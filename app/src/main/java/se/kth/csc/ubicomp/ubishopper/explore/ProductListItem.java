package se.kth.csc.ubicomp.ubishopper.explore;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import se.kth.csc.ubicomp.ubishopper.R;
import se.kth.csc.ubicomp.ubishopper.model.Product;

import java.text.NumberFormat;

/**
 * Created by mhotan on 5/8/14.
 */
public class ProductListItem extends RelativeLayout {

    public ProductListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setProduct(Product product) {
        if (product == null) throw new NullPointerException("Null Product while populating view");
        setTitle(product.getName());
        setPrice(product.getPrice());
        setDescription(product.getDescription());
        ImageView view = getProductImageView();
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        product.loadImage(view);
    }

    private void setDescription(String description) {
        getDescriptionView().setText(description);
    }

    private void setTitle(String title) {
        getTitleView().setText(title);
    }

    private void setPrice(float price) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        getPriceView().setText(currencyFormat.format(price));
    }

    private ImageView getProductImageView() {
        return (ImageView) findViewById(R.id.product_list_item_image);
    }

    private TextView getDescriptionView() {
        return (TextView) findViewById(R.id.product_list_item_description);
    }

    private TextView getPriceView() {
        return (TextView) findViewById(R.id.product_list_item_price);
    }

    private TextView getTitleView() {
        return (TextView) findViewById(R.id.product_list_item_title);
    }

}
