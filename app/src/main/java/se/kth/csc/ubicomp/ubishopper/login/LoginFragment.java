package se.kth.csc.ubicomp.ubishopper.login;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import se.kth.csc.ubicomp.ubishopper.R;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class LoginFragment extends Fragment
        implements SurfaceHolder.Callback, Camera.FaceDetectionListener{

    private static final String TAG = LoginFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String EMAIL_ADDRESS = "email_address";
    private static final String PASSWORD = "password";

    // TODO: Rename and change types of parameters
    private String email;
    private String password;

    private Camera camera;

    private OnFragmentInteractionListener listener;

    /**
     * The surface view where the camera preview is drawn.
     */
    private SurfaceView loginCameraPreview;

    /**
     * Social Login Buttons
     */
    private ImageButton facebookButton, twitterButton, googlePlusButton;

    /**
     * Email and Password input fields
     */
    private EditText emailInputField, passwordInputField;

    /**
     * Regular log in buttons.
     */
    private Button loginButton, registerButton, forgotPasswordButton;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param email Parameter 1.
     * @param password Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String email, String password) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(EMAIL_ADDRESS, email);
        args.putString(PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(EMAIL_ADDRESS);
            password = getArguments().getString(PASSWORD);
        } else {
            email = "";
            password = "";
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginCameraPreview = (SurfaceView) view.findViewById(R.id.login_camera_preview);
        facebookButton = (ImageButton) view.findViewById(R.id.facebook_button);
        twitterButton = (ImageButton) view.findViewById(R.id.twitter_button);
        googlePlusButton = (ImageButton) view.findViewById(R.id.googleplus_button);
        emailInputField = (EditText) view.findViewById(R.id.email_input_field);
        passwordInputField = (EditText) view.findViewById(R.id.password_input_field);
        loginButton = (Button) view.findViewById(R.id.login_button);
        registerButton = (Button) view.findViewById(R.id.register_button);
        forgotPasswordButton = (Button) view.findViewById(R.id.forgot_password_button);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginCameraPreview.getHolder().addCallback(this);
        loginCameraPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        // Open the default i.e. the first front facing camera.
        if (Camera.getNumberOfCameras() >= 2)
            this.camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        else
            this.camera = Camera.open();
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        /**
         * Notifies application that a face was recognized.
         *
         * @param face Face that was recognized
         */
        void onFaceRecognized(Camera.Face face);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.camera.stopPreview();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.camera.release();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            this.camera.setPreviewDisplay(this.loginCameraPreview.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters params = this.camera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width,selected.height);

        // Set the parameters
        this.camera.setParameters(params);
        this.camera.setDisplayOrientation(90);
        this.camera.setFaceDetectionListener(this);
        this.camera.startPreview();
        this.camera.startFaceDetection();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("PREVIEW", "surfaceDestroyed");
    }

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        if (faces.length == 0) {
            Log.w(TAG, "False no faces detected");
            return;
        }
        if (faces.length > 1) {
            Toast.makeText(getActivity(), "Too many faces to authenticate",
                    Toast.LENGTH_SHORT);
            return;
        }
        listener.onFaceRecognized(faces[0]);
    }
}
