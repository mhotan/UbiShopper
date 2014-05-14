package se.kth.csc.ubicomp.ubishopper.userinterests;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.GridView;
import se.kth.csc.ubicomp.ubishopper.R;
import se.kth.csc.ubicomp.ubishopper.model.MockModel;


/**
 * Fragment that shows all the interests of the User.
 *
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserInterestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserInterestFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class UserInterestFragment extends Fragment {

    private OnFragmentInteractionListener listener;

    UserinterestsAdapter adapter;

    /**
     * The interest grid view.
     */
    private GridView interestGrid;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserInterestFragment.
     */
    public static UserInterestFragment newInstance() {
        UserInterestFragment fragment = new UserInterestFragment();
        Bundle args = new Bundle();
        // TODO Set Arguments
        fragment.setArguments(args);
        return fragment;
    }

    public UserInterestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO Extract Arguments.
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_interest, container, false);

        // Set the Adapter for
        interestGrid = (GridView) view.findViewById(R.id.interest_grid_view);

        // Set the grid for the grid view.
        // This informs how the grid view will populate each cell.
        adapter = new UserinterestsAdapter(
                this.getActivity(), MockModel.getInstance().getInterestTypes());
        interestGrid.setAdapter(adapter);

        // Allow multiple items to be checked.
        interestGrid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        interestGrid.setMultiChoiceModeListener(new InterestMultiChoiceListener());
        return view;
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
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

//        void onCheckedStateChange(InterestType type, boolean checked);
    }

    /**
     * Listener for grid view selected items.
     */
    private class InterestMultiChoiceListener implements GridView.MultiChoiceModeListener {

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            int numChecked = interestGrid.getCheckedItemCount();
            mode.setSubtitle(numChecked + " interests.");
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Select your Interests");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }

}


