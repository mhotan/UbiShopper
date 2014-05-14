package se.kth.csc.ubicomp.ubishopper.login;

import android.app.ActionBar;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
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
public class LoginFragment extends Fragment implements Camera.FaceDetectionListener{

    private static final String TAG = LoginFragment.class.getSimpleName();

    // The email and password
    private static final String EMAIL_ADDRESS = "email_address";
    private static final String PASSWORD = "password";

    // TODO: Rename and change types of parameters
    private String email;
    private String password;

    private Preview mPreview;
    Camera mCamera;
    int mNumberOfCameras;
    int mCurrentCamera;  // Camera ID currently chosen
    int mCameraCurrentlyLocked;  // Camera ID that's actually acquired

    // The first rear facing camera
    int mDefaultCameraId;

    private OnFragmentInteractionListener listener;

    /**
     * The surface view where the camera preview is drawn.
     */
    private FrameLayout previewContainer;

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
     * @param email Previously used email.
     * @param password Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
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

        // Create a container that will hold a SurfaceView for camera previews
        mPreview = new Preview(this.getActivity(), this);

        // Find the total number of cameras available
        mNumberOfCameras = Camera.getNumberOfCameras();

        // Find the ID of the rear-facing ("default") camera
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < mNumberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                mCurrentCamera = mDefaultCameraId = i;
            }
        }
        setHasOptionsMenu(mNumberOfCameras > 1);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        previewContainer = (FrameLayout) view.findViewById(R.id.login_camera_preview_container);
        facebookButton = (ImageButton) view.findViewById(R.id.facebook_button);
        twitterButton = (ImageButton) view.findViewById(R.id.twitter_button);
        googlePlusButton = (ImageButton) view.findViewById(R.id.googleplus_button);
        emailInputField = (EditText) view.findViewById(R.id.email_input_field);
        passwordInputField = (EditText) view.findViewById(R.id.password_input_field);
        loginButton = (Button) view.findViewById(R.id.login_button);
        registerButton = (Button) view.findViewById(R.id.register_button);
        forgotPasswordButton = (Button) view.findViewById(R.id.forgot_password_button);
        previewContainer.addView(mPreview);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Add an up arrow to the "home" button, indicating that the button will go "up"
        // one activity in the app's Activity heirarchy.
        // Calls to getActionBar() aren't guaranteed to return the ActionBar when called
        // from within the Fragment's onCreate method, because the Window's decor hasn't been
        // initialized yet.  Either call for the ActionBar reference in Activity.onCreate()
        // (after the setContentView(...) call), or in the Fragment's onActivityCreated method.
        Activity activity = this.getActivity();
        ActionBar actionBar = activity.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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

        // Use mCurrentCamera to select the camera desired to safely restore
        // the fragment after the camera has been changed
        mCamera = Camera.open(mCurrentCamera);
        mCameraCurrentlyLocked = mCurrentCamera;
        mPreview.setCamera(mCamera);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Because the Camera object is a shared resource, it's very
        // important to release it when the activity is paused.
        if (mCamera != null) {
            mPreview.setCamera(null);

            // Release the camera
            mCamera.release();
            mCamera = null;
        }
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
