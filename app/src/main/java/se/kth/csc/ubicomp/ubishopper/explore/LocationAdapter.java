package se.kth.csc.ubicomp.ubishopper.explore;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import se.kth.csc.ubicomp.ubishopper.model.Location;

/**
 *
 *
 * Created by mhotan on 5/8/14.
 */
public class LocationAdapter extends ArrayAdapter<Location> implements Filterable {

    /**
     * Create location adapter.
     *
     * @param context Context of the container
     * @param locations Locations to populate the adapter
     */
    public LocationAdapter(Context context, Location[] locations) {
        super(context, android.R.layout.simple_list_item_1, locations);
    }

    

}
