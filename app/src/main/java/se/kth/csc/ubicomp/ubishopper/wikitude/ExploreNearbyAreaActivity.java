package se.kth.csc.ubicomp.ubishopper.wikitude;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import com.wikitude.architect.ArchitectView.ArchitectUrlListener;
import org.json.JSONArray;
import se.kth.csc.ubicomp.ubishopper.R;
import se.kth.csc.ubicomp.ubishopper.model.MockModel;
import se.kth.csc.ubicomp.ubishopper.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the presentations of nearby activity.
 */
public class ExploreNearbyAreaActivity extends SampleCamActivity {

    private static final String MARKER_SELECTED_TAG = "markerselected";

    private static final int WAIT_FOR_LOCATION_STEP_MS = 2000;

    private POILoader loader;

    /** Called when the activity is first created. */
    @Override
    public void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        // Location Listener.
        this.locationListener = new LocationListener() {
            @Override
            public void onStatusChanged( String provider, int status, Bundle extras ) {}
            @Override
            public void onProviderEnabled( String provider ) {}
            @Override
            public void onProviderDisabled( String provider ) {}

            @Override
            public void onLocationChanged( final Location location ) {
                if (location!=null) {
                    ExploreNearbyAreaActivity.this.lastKnownLocaton = location;
                    if ( ExploreNearbyAreaActivity.this.architectView != null ) {
                        if ( location.hasAltitude() ) {
                            ExploreNearbyAreaActivity.this.architectView.setLocation( location.getLatitude(),
                                    location.getLongitude(), location.getAltitude(), location.getAccuracy() );
                        } else {
                            ExploreNearbyAreaActivity.this.architectView.setLocation( location.getLatitude(),
                                    location.getLongitude(), location.getAccuracy() );
                        }
                    }
                }
            }
        };

        this.architectView.registerSensorAccuracyChangeListener( this.sensorAccuracyListener );
        this.locationProvider = new LocationProvider( this, this.locationListener );

        loader = new POILoader(MockModel.getInstance().getNearbyProducts());
    }

    @Override
    protected void onPostCreate( final Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );
        this.loadData();
    }

    protected void loadData() {
        if (!this.loader.isLoading()) {
            final Thread t = new Thread(loader);
            t.start();
        }
    }

    public ArchitectUrlListener getUrlListener() {
        return new ArchitectUrlListener() {

            @Override
            // fetch e.g. document.location = "architectsdk://markerselected?id=1";
            public boolean urlWasInvoked(String uriString) {
                Uri invokedUri = Uri.parse(uriString);
                if (MARKER_SELECTED_TAG.equalsIgnoreCase(invokedUri.getHost())) {
                    final Intent poiDetailIntent = new Intent(ExploreNearbyAreaActivity.this, SamplePoiDetailActivity.class);
                    poiDetailIntent.putExtra(SamplePoiDetailActivity.EXTRAS_KEY_POI_ID, String.valueOf(invokedUri.getQueryParameter("id")) );
                    poiDetailIntent.putExtra(SamplePoiDetailActivity.EXTRAS_KEY_POI_TITILE, String.valueOf(invokedUri.getQueryParameter("title")) );
                    poiDetailIntent.putExtra(SamplePoiDetailActivity.EXTRAS_KEY_POI_DESCR, String.valueOf(invokedUri.getQueryParameter("description")) );
                    ExploreNearbyAreaActivity.this.startActivity(poiDetailIntent);
                    return true;
                }
                return false;
            }
        };
    }

    /**
     * Call JacaScript in architectView
     * @param methodName
     * @param arguments
     */
    private void callJavaScript(final String methodName, final String[] arguments) {
        final StringBuilder argumentsString = new StringBuilder("");
        for (int i= 0; i<arguments.length; i++) {
            argumentsString.append(arguments[i]);
            if (i<arguments.length-1) {
                argumentsString.append(", ");
            }
        }

        if (this.architectView != null) {
            final String js = ( methodName + "( " + argumentsString.toString() + " );" );
            this.architectView.callJavascript(js);
        } else {

        }
    }

    private class POILoader implements Runnable {

        private final List<Product> products;

        private boolean isLoading;

        /**
         * @param data JSON Array.
         */
        POILoader(List<Product> data) {
            this.products = new ArrayList<Product>(data);
            this.isLoading = false;
        }

        public synchronized boolean isLoading() {
            return isLoading;
        }

        private synchronized void setLoading(boolean isLoading) {
            this.isLoading = isLoading;
        }

        @Override
        public void run() {
            setLoading(true); // Mark as the thread is loading.

            while (ExploreNearbyAreaActivity.this.lastKnownLocaton == null
                    && !ExploreNearbyAreaActivity.this.isFinishing()) {

                ExploreNearbyAreaActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ExploreNearbyAreaActivity.this, R.string.location_fetching, Toast.LENGTH_SHORT).show();
                    }
                });
                try {
                    Thread.sleep(WAIT_FOR_LOCATION_STEP_MS);
                } catch (InterruptedException e) {
                    break;
                }
            }

            if (ExploreNearbyAreaActivity.this.lastKnownLocaton!=null && !ExploreNearbyAreaActivity.this.isFinishing()) {
                JSONArray poiData = toJSONArray(products);
                ExploreNearbyAreaActivity.this.callJavaScript("World.loadPoisFromJsonData",
                        new String[] { poiData.toString() });
            }

            setLoading(false); // Mark as the thread is done loading.
        }
    }

    private static JSONArray toJSONArray(List<Product> products) {
        JSONArray array = new JSONArray();
        for (Product product: products) {
            array.put(product.toPOIInformation());
        }
        return array;
    }

}
