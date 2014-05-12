package se.kth.csc.ubicomp.ubishopper.explore;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moodstocks.android.MoodstocksError;
import com.moodstocks.android.Result;
import com.moodstocks.android.Scanner;

import java.util.Locale;

import se.kth.csc.ubicomp.ubishopper.R;
import se.kth.csc.ubicomp.ubishopper.wikitude.SampleCamHandlePoiDetailActivity;

/**
 * Activity that manages the fragments for scanning location QR Codes and
 *
 * @author Michael Hotan
 */
public class SearchAndScanActivity extends ActionBarActivity implements ActionBar.TabListener,
        ScanFragment.OnFragmentInteractionListener, SupportFragment.OnFragmentInteractionListener,
        Scanner.SyncListener {

    private static final String TAG = SearchAndScanActivity.class.getSimpleName();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    public static final String ARG_SEARCH_QUERY = "search_query";

    private boolean compatible = false;
    private Scanner scanner;

    /**
     * Current search query for searching and scanning for an application.
     */
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_and_scan);

        // Initialize MoodStocks
        compatible = Scanner.isCompatible();
        if (compatible) {
            try {
                scanner = Scanner.get();
                String path = Scanner.pathFromFilesDir(this, MoodStocksConstants.DB_PATH);
                scanner.open(path, MoodStocksConstants.API_KEY, MoodStocksConstants.API_SECRET);
                scanner.setSyncListener(this);
                scanner.sync();
            } catch (MoodstocksError e) {
                Log.e(TAG, "Unable to initialize MoodStocks due to " + e);
                compatible = false;
            }
        }

        // Set up the action bar.
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // If the activity was created for the first time
        // Check if there isn't an initial argument for the search query.
        if (getIntent() != null && getIntent().hasExtra(ARG_SEARCH_QUERY))
            searchQuery = getIntent().getExtras().getString(ARG_SEARCH_QUERY, "");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), compatible);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Close the scanner once we are done with this activity.
        if (compatible) {
            try {
                scanner.close();
                scanner.destroy();
                scanner = null;
            } catch (MoodstocksError e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_scan, menu);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_SEARCH_QUERY, searchQuery);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        searchQuery = savedInstanceState.getString(ARG_SEARCH_QUERY, "");
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        // Nothing to do.
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        // Nothing to do.
    }

    /**
     * Shows a dialog that describes the functionality of the Scan Activity.
     *
     * @param infoButton The info button that initiated the event.
     */
    public void onScanInfoButtonClicked(View infoButton) {
        // TODO Handle the presentation of the information for the Scan Button
        new AlertDialog.Builder(this).
                setTitle("Scan Around").
                setMessage("Scan nearby QRCodes and Images so we can assist you with your current location").
                setCancelable(true).create().show();
    }

    /**
     * The user request to
     *
     * @param exploreButton The button that invoked the callback
     */
    public void onExploreSurroundingWorld(View exploreButton) {
        startActivity(new Intent(this, SampleCamHandlePoiDetailActivity.class));
    }

//    Moodstocks Sync listener

    @Override
    public void onScannedResult(Result result) {
        Toast.makeText(this, "Result found " + result.getValue(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSyncStart() {
        Log.d("Moodstocks SDK", "Sync will start.");
    }

    @Override
    public void onSyncComplete() {
        try {
            Log.d("Moodstocks SDK", "Sync succeeded ("+ scanner.count() + " images)");
        } catch (MoodstocksError e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSyncFailed(MoodstocksError e) {
        Log.d("Moodstocks SDK", "Sync error #" + e.getErrorCode() + ": " + e.getMessage());
    }

    @Override
    public void onSyncProgress(int total, int current) {
        int percent = (int) ((float) current / (float) total * 100);
        Log.d("Moodstocks SDK", "Sync progressing: " + percent + "%");
    }

    /**
     * Scan Fragment postion in the tab.
     */
    private static final int SCANNER_FRAGMENT_POSITION = 1;

    /**
     *
     */
    private static final int SEARCH_FRAGMENT_POSITION = 0;

    /**
     * This section page adapter handle the presentation of
     *
     * A {@link FragmentPagerAdapter} That returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        /**
         *
         */
        private final boolean moodstocksCompatible;

        /**
         * A reference to the Scanner Fragment.
         */
        private ScanFragment scanFragment;

        /**
         * Creates Adapter for handling fragments present in the Screen.
         *
         * @param fm Fragment Manager.
         * @param moodstocksCompatible Whether or not we can scan using a camera
         */
        public SectionsPagerAdapter(FragmentManager fm, boolean moodstocksCompatible) {
            super(fm);
            this.moodstocksCompatible = moodstocksCompatible;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                // Request the scanner fragment.
                case SCANNER_FRAGMENT_POSITION:
                    scanFragment = ScanFragment.newInstance();
                    return scanFragment;
                // Request the search fragment.
                case SEARCH_FRAGMENT_POSITION:
                default:
                    return SupportFragment.newInstance(searchQuery);
            }
        }

        @Override
        public int getCount() {
            if (!moodstocksCompatible) return 1;
            // Show to if is compatible
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case SEARCH_FRAGMENT_POSITION:
                    return getString(R.string.title_section_search).toUpperCase(l);
                case SCANNER_FRAGMENT_POSITION:
                    return getString(R.string.title_section_scan).toUpperCase(l);
            }
            return null;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);

            // If the ScanFragment does not exist, there no need to try and pause and resume
            // the camera processing.
            if (scanFragment == null) return;

            // Pause and resume the camera appropiately.
            switch (position) {
                case SCANNER_FRAGMENT_POSITION:
                    scanFragment.resumeScanning();
                    break;
                default:
                    scanFragment.pauseScanning();
            }
        }

    }

}
