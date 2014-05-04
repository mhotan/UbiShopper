package se.kth.csc.ubicomp.ubishopper.userinterests;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import se.kth.csc.ubicomp.ubishopper.explore.SearchAndScanActivity;

import static se.kth.csc.ubicomp.ubishopper.R.layout.activity_user_interest;

/**
 * Activity that contains and provides context for all the Fragments that manage
 * handling the user interests.
 */
public class UserInterestActivity extends FragmentActivity implements
        UserInterestFragment.OnFragmentInteractionListener {

    @Override
    // originally set as protected class, now it's public
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_user_interest);
    }

    public void onContinue(View view) {
        startActivity(new Intent(this, SearchAndScanActivity.class));
    }
}
