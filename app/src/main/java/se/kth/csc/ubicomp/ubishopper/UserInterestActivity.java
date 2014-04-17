package se.kth.csc.ubicomp.ubishopper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class UserInterestActivity extends FragmentActivity implements UserInterestFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interest);
    }

    public void onContinue(View view) {
        startActivity(new Intent(this, ScanActivity.class));
    }
}
