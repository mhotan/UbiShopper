package se.kth.csc.ubicomp.ubishopper.userinterests;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import se.kth.csc.ubicomp.ubishopper.R;
import se.kth.csc.ubicomp.ubishopper.model.InterestType;


/**
 * Adapter for User interests.
 *
 * Created by zenkig on 4/24/14.
 */
public class UserinterestsAdapter extends ArrayAdapter<InterestType> {

    private Context context;


    /**
     * Creates adapter for interest types.
     *
     * @param context The context for the adapter
     * @param interestTypes The interest types.
     */
    public UserinterestsAdapter(Context context, InterestType[] interestTypes) {
        super(context, R.layout.interest_grid_item, interestTypes);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Populate the view for the first time.
        // Inflate the view for the first time.
        InterestGridItem gridView = (InterestGridItem) inflater.inflate(R.layout.interest_grid_item, parent, false);
        // Extract the components of the grid item.
        TextView interestLabel = (TextView) gridView.findViewById(R.id.interest_grid_item_label);
        ImageView interestImage = (ImageView) gridView.findViewById(R.id.interest_image);

        // Populate the fields for the item
        interestLabel.setText(getItem(position).getName());
        getItem(position).loadImage(interestImage);

        return gridView;
    }


}
