package se.kth.csc.ubicomp.ubishopper.explore;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.moodstocks.android.AutoScannerSession;
import com.moodstocks.android.ManualScannerSession;
import com.moodstocks.android.MoodstocksError;
import com.moodstocks.android.Result;
import com.moodstocks.android.Scanner;

import se.kth.csc.ubicomp.ubishopper.R;


/**
 * A fragment that handles the scanning for BarCodes, Location QR Codes, and even images.
 *
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanFragment extends Fragment implements AutoScannerSession.Listener {

    private static final String TAG = ScanFragment.class.getSimpleName();

    private static final int TYPES = Result.Type.IMAGE | Result.Type.QRCODE | Result.Type.EAN13;

    /**
     * MoodStocks Scan Session.
     */
    private AutoScannerSession session = null;

    /**
     * Listening activity.
     */
    private OnFragmentInteractionListener listener;

    /**
     * The screen where the scanning will take place.
     */
    private SurfaceView scanPreview;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ScanFragment.
     */
    public static ScanFragment newInstance() {
        ScanFragment fragment = new ScanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Required empty public constructor
     */
    public ScanFragment() {
        // Do not delete.  Class needed for embedded XML
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        scanPreview = (SurfaceView) view.findViewById(R.id.scan_preview);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            session = new AutoScannerSession(this.getActivity(), Scanner.get(), this, scanPreview);
            session.setResultTypes(TYPES);
        } catch (MoodstocksError e) {
            Log.e(TAG, "Unable to start Scanner Session due to " + e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        session.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        session.stop();
    }

    /**
     * Resume the scanning.
     */
    public void resumeScanning() {
        if (session == null) return;
        session.resume();
    }

    /**
     * Pause the scanning.
     */
    public void pauseScanning() {
        if (session == null) return;
        session.pause();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getClass().getName()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

//    Scanner

    @Override
    public void onCameraOpenFailed(Exception e) {
        // Unable to get access to the camera
        Log.w(TAG, "Unable to get access to the camera due to " + e);
    }

    @Override
    public void onResult(Result result) {
        listener.onScannedResult(result);
    }

    @Override
    public void onWarning(String s) {
        // An error occured in the
        Log.w(TAG, "Error occured in the background: " + s);
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
         * Notifies listener that a scan was successful
         *
         * @param result Result of the most recent successful scan.
         */
        public void onScannedResult(Result result);
    }

}
