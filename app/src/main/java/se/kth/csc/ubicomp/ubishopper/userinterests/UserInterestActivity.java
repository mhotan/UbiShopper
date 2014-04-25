package se.kth.csc.ubicomp.ubishopper.userinterests;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.GridView;
import android.widget.TextView;

import se.kth.csc.ubicomp.ubishopper.R;
import se.kth.csc.ubicomp.ubishopper.ScanActivity;

import static se.kth.csc.ubicomp.ubishopper.R.layout.activity_user_interest;


public class UserInterestActivity extends FragmentActivity implements UserInterestFragment.OnFragmentInteractionListener {

    GridView gridView;


    static final String[] PREFERRED_ITEM = new String[] {
            "item1_logo", "item2_logo","item3_logo", "defaultItemSomething_logo" };

    @Override
    // originally set as protected class, now it's public
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_user_interest);

        gridView = (GridView) findViewById(R.id.gridViewFragment);

        // got problem here setting the UserinterestsAdapter to the view
        gridView.setAdapter(new UserinterestsAdapter(this,PREFERRED_ITEM));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById(R.id.grid_labels))
                                .getText(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void onContinue(View view) {
        startActivity(new Intent(this, ScanActivity.class));
    }
}
