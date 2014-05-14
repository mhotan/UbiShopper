package se.kth.csc.ubicomp.ubishopper.explore;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import se.kth.csc.ubicomp.ubishopper.R;
import se.kth.csc.ubicomp.ubishopper.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Support screen that shows Product information for nearby areas.
 *
 * Created by mhotan on 5/4/14.
 */
public class SupportFragment extends Fragment implements MockModel.ModelListener,
        ProductAdapter.ProductSelectedListener {

    /**
     * Interaction listener for this fragment.
     */
    private OnFragmentInteractionListener listener;

    private LinearLayout locationPane;

    private ProductAdapter productAdapter;

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

        locationPane = (LinearLayout) view.findViewById(R.id.check_surrounding_area);
        locationPane.setVisibility(MockModel.getInstance().getHighlightedLocation() == null ? View.INVISIBLE : View.VISIBLE);

        // Location List.
        final AutoCompleteTextView locationField = (AutoCompleteTextView) view.findViewById(R.id.location_search_field);
        List<Location> locationList = new ArrayList<Location>(LocationFactory.getInstance().getLocations());
        ArrayAdapter<String> locationsAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, getLocationNames(locationList));
        locationField.setAdapter(locationsAdapter);
        locationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Location location = LocationFactory.getInstance().getLocation(locationField.getText().toString().trim());
//                if (location == null) return;
                MockModel.getInstance().setHighlightedLocation(location);
            }
        });

        // Product Search bar
        final AutoCompleteTextView productEditText = (AutoCompleteTextView) view.findViewById(R.id.product_search_bar);
        String[] productNames = getProductNames(MockModel.getInstance().getProducts());
        ArrayAdapter<String> productSearchAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, productNames);
        productEditText.setAdapter(productSearchAdapter);
        productEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Product product = ProductFactory.getInstance().getProduct(productEditText.getText().toString().trim());
//                if (product == null) return;
                MockModel.getInstance().setHighlightedProduct(product);
            }
        });


        // Product List.
        Product[] products = Arrays.copyOf(MockModel.getInstance().getProducts(),
                MockModel.getInstance().getProducts().length);
        productAdapter = new ProductAdapter(getActivity(), products, this);
        ListView productListView = (ListView) view.findViewById(R.id.product_list);
        productListView.setAdapter(productAdapter);

        // Register the listener
        MockModel.getInstance().addListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        MockModel.getInstance().removeListener(this);
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

    @Override
    public void onHighlightedLocationChanged(Location location) {
        if (location == null) {
            locationPane.setVisibility(View.INVISIBLE);
            return;
        }
        locationPane.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHighlightedProductChanged(Product product) {
        if (product == null) {
            productAdapter.clear();
            productAdapter.add(product);
        } else {
            productAdapter.clear();
            productAdapter.addAll(MockModel.getInstance().getProducts());
        }
    }

    @Override
    public void onProductSelected(Product product) {
        this.listener.onProductSelected(product);
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
    interface OnFragmentInteractionListener extends ProductAdapter.ProductSelectedListener {

    }

    private static String[] getProductNames(Product[] products) {
        String[] names = new String[products.length];
        for (int i = 0; i < products.length; i++) {
            names[i] = products[i].getName();
        }
        return names;
    }

    private static String[] getLocationNames(Collection<Location> locations) {
        String[] names = new String[locations.size()];
        int i = 0;
        for (Location location: locations) {
            names[i] = location.getName();
            i++;
        }
        return names;
    }

}
