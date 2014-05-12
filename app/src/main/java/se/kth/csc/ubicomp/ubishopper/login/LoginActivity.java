package se.kth.csc.ubicomp.ubishopper.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import se.kth.csc.ubicomp.ubishopper.R;
import se.kth.csc.ubicomp.ubishopper.userinterests.UserInterestActivity;

/**
 * Activity that
 */
public class LoginActivity extends ActionBarActivity
        implements LoginFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * The User request to log in with the current
     *
     * @param view The view that invoked this event
     */
    public void onLogin(View view) {
        startActivity(new Intent(this, UserInterestActivity.class));
    }

    /**
     * The User request to register a new account.
     *
     * @param view The view that invoked this event
     */
    public void onRegister(View view) {
        showNotYetImplementedAlertDialog();
    }

    /**
     * The User notifies application that their password is forgotten.
     *
     * @param view The view that invoked this event
     */
    public void onForgotPassword(View view) {
        showNotYetImplementedAlertDialog();
    }

    /**
     * The User requests to login with Facebook.
     *
     * @param view The view that invoked this event
     */
    public void onLoginWithFacebook(View view) {
        showNotYetImplementedAlertDialog();
    }

    /**
     * The User requests to login with Twitter.
     *
     * @param view The view that invoked this event
     */
    public void onLoginWithTwitter(View view) {
        showNotYetImplementedAlertDialog();
    }

    /**
     * The User requests to login with Google Plus.
     *
     * @param view The view that invoked this event
     */
    public void onLoginWithGooglePlus(View view) {
        showNotYetImplementedAlertDialog();
    }

    @Override
    public void onFaceRecognized(Camera.Face face) {
        Toast.makeText(this, "Face recognized!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, UserInterestActivity.class));
    }

    private void showNotYetImplementedAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true).setTitle("Feature Not Implemented!").
                setMessage("We are currently still working on this feature.  Sorry for the" +
                        " inconvenience.").create().show();
    }
}
