package se.kth.csc.ubicomp.ubishopper.explore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import se.kth.csc.ubicomp.ubishopper.R;
import se.kth.csc.ubicomp.ubishopper.model.Product;
import se.kth.csc.ubicomp.ubishopper.userinterests.InterestGridItem;

/**
 * Created by mhotan on 5/8/14.
 */
public class ProductAdapter extends ArrayAdapter<Product> implements Filterable {

    /**
     * Returns a basic adapter for populating a Product.
     *
     * @param context Context that request
     * @param products Products to present.
     */
    public ProductAdapter(Context context, Product[] products) {
        super(context, R.layout.product_list_item, products);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductListItem view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (ProductListItem) inflater.inflate(R.layout.product_list_item, parent, false);
        } else {
            view = (ProductListItem) convertView;
        }
        // Set the values
        Product product = getItem(position);
        view.setProduct(product);
        return view;
    }
}
