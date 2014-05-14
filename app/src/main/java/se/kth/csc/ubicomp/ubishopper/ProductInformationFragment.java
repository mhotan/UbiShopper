package se.kth.csc.ubicomp.ubishopper;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import se.kth.csc.ubicomp.ubishopper.userinterests.ScrollViewFragment;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductInformationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductInformationFragment} factory method to
 * create an instance of this fragment.
 *
 */
public class ProductInformationFragment extends Fragment implements TabHost.OnTabChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "FragmentTabs";
    public static final String TAB_1 = "History";
    public static final String TAB_2 = "Location";
    public static final String TAB_3 = "Fragments";


    // TODO: Rename and change types of parameters
    private View mRoot;
    private TabHost mTabHost;
    private int mCurrentTab;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProductInformationFragment.
     */

    public ProductInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_product_information, container, false);
        mRoot = inflater.inflate(R.layout.fragment_product_information, null);
        mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
        setupTabs();
        return mRoot;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(mCurrentTab);
        // manually start loading stuff in the first tab
        updateTab(TAB_1, R.id.History);
    }

    private void setupTabs() {
        mTabHost.setup(); // you must call this before adding your tabs!
        mTabHost.addTab(newTab(TAB_1, R.string.tab_1, R.id.History));
        mTabHost.addTab(newTab(TAB_2, R.string.tab_2, R.id.Location));
        mTabHost.addTab(newTab(TAB_3, R.string.tab_3, R.id.Popularity));
    }

    private TabSpec newTab(String tag, int labelId, int tabContentId) {
        Log.d(TAG, "buildTab(): tag=" + tag);

        View indicator = LayoutInflater.from(getActivity()).inflate(
                R.layout.tab,
                (ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
        ((TextView) indicator.findViewById(R.id.text)).setText(labelId);

        TabSpec tabSpec = mTabHost.newTabSpec(tag);
        tabSpec.setIndicator(indicator);
        tabSpec.setContent(tabContentId);
        return tabSpec;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        Log.d(TAG, "onTabChanged(): tabId=" + tabId);
        if (TAB_1.equals(tabId)) {
            updateTab(tabId, R.id.History);
            mCurrentTab = 0;
            return;
        }
        if (TAB_2.equals(tabId)) {
            updateTab(tabId, R.id.Location);
            mCurrentTab = 1;
            return;
        }
        if (TAB_3.equals(tabId)) {
            updateTab(tabId, R.id.Popularity);
            mCurrentTab = 2;
            return;
        }
    }

    private void updateTab(String tabId, int placeholder) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.findFragmentByTag(tabId) == null) {
            fragmentManager.beginTransaction()
                    .replace(placeholder, ScrollViewFragment.newInstance(), tabId)
                    .commit();
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
