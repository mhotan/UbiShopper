package se.kth.csc.ubicomp.ubishopper.explore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import se.kth.csc.ubicomp.ubishopper.R;
import se.kth.csc.ubicomp.ubishopper.model.Product;

/**
 * Created by mhotan on 5/8/14.
 */
public class ProductAdapter extends ArrayAdapter<Product> implements Filterable {

    private final ProductSelectedListener listener;

    /**
     * Returns a basic adapter for populating a Product.
     *
     * @param context Context that request
     * @param products Products to present.
     */
    public ProductAdapter(Context context, Product[] products, ProductSelectedListener listener) {
        super(context, R.layout.product_list_item, products);
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ProductListItem view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (ProductListItem) inflater.inflate(R.layout.product_list_item, parent, false);
        } else {
            view = (ProductListItem) convertView;
        }
        // Set the values
        final Product product = getItem(position);
        view.setProduct(product);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onProductSelected(product);
            }
        });

        return view;
    }

    interface ProductSelectedListener {

        void onProductSelected(Product product);

    }


}
