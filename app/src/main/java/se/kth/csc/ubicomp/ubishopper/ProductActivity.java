package se.kth.csc.ubicomp.ubishopper;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;


public class ProductActivity extends FragmentActivity implements
        ProductInformationFragment.OnFragmentInteractionListener, RecomendationFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
