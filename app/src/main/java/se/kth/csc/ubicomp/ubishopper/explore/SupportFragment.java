package se.kth.csc.ubicomp.ubishopper.explore;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.kth.csc.ubicomp.ubishopper.R;
import se.kth.csc.ubicomp.ubishopper.model.Location;
import se.kth.csc.ubicomp.ubishopper.model.LocationFactory;
import se.kth.csc.ubicomp.ubishopper.model.MockModel;
import se.kth.csc.ubicomp.ubishopper.model.Product;

/**
 * Support screen that shows Product information for nearby areas.
 *
 * Created by mhotan on 5/4/14.
 */
public class SupportFragment extends Fragment {

    /**
     * Interaction listener for this fragment.
     */
    private OnFragmentInteractionListener listener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param searchQuery Initial Search query to use if one is defined.
     * @return A new instance of fragment ScanFragment.
     */
    public static SupportFragment newInstance(String searchQuery) {
        SupportFragment fragment = new SupportFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public SupportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        // Location List.
        AutoCompleteTextView locationField = (AutoCompleteTextView) view.findViewById(R.id.location_search_field);
        List<Location> locationList = new ArrayList<Location>(LocationFactory.getLocations());
        Location[] locations = new Location[locationList.size()];
        locationList.toArray(locations);
        ArrayAdapter<Location> locationsAdapter = new ArrayAdapter<Location>(getActivity(),
                android.R.layout.simple_list_item_1, locations);
        locationField.setAdapter(locationsAdapter);

        // Product List.
        Product[] products = Arrays.copyOf(MockModel.getInstance().getProducts(),
                MockModel.getInstance().getProducts().length);
        ProductAdapter adapter = new ProductAdapter(getActivity(), products);
        ListView productListView = (ListView) view.findViewById(R.id.product_list);
        productListView.setAdapter(adapter);
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



    }

}
