package se.kth.csc.ubicomp.ubishopper;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import se.kth.csc.ubicomp.ubishopper.userinterests.ScrollViewFragment;


public class ProductActivity extends FragmentActivity implements
        ProductInformationFragment.OnFragmentInteractionListener, ScrollViewFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
