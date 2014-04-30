package se.kth.csc.ubicomp.ubishopper.userinterests;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import se.kth.csc.ubicomp.ubishopper.R;


/**
 * Created by zenkig on 4/24/14.
 */

public class UserinterestsAdapter extends BaseAdapter {

    private Context context;
    private final String[] recommanditemValues;

    public UserinterestsAdapter(Context context, String[] recommanditemValues) {
        this.context = context;
        this.recommanditemValues = recommanditemValues;

    }

    @Override
    public int getCount() {
        return recommanditemValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.preference_griditem, null);

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_labels);
            textView.setText(recommanditemValues[position]);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_images);

            String recomItems = recommanditemValues[position];
            // Need TODO: change the , Or create a dataset of the items base.
            if (recomItems.equals("recommendItems1...")) {
                imageView.setImageResource(R.drawable.item1_logo);
            } else if (recomItems.equals("recommendItems2...")) {
                imageView.setImageResource(R.drawable.item2_logo);
            } else if (recomItems.equals("recommendItems3...")) {
                imageView.setImageResource(R.drawable.item3_logo);
            } else {
                imageView.setImageResource(R.drawable.item4_logo);
            }

        } else {
            gridView = (View) convertView;
        }


        return gridView;
    }


}
