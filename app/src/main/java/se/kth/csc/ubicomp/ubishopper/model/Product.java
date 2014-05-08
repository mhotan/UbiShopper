package se.kth.csc.ubicomp.ubishopper.model;

import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by zenkig on 5/4/14.
 */


public class Product {

    /**
     * Name of the type of products.
     */
    private final String productname;

    /**
     * The resource ID if the image is stored locally.
     */
    private final int imageResourceId;

    /**
     * The Image Uri for the web
     */
    private final Uri imageUri;

    private final float price;

    private final String description;

    private double rating;


    /**
     * Creates the Interest Type
     *
     * @param productname Name of the Product
     * @param imageResourceId The resource Id of the image.
     */

    public Product(String productname, int imageResourceId, float price, String description,double rating) {
        if (productname == null || productname.trim().isEmpty())
            throw new NullPointerException(getClass().getSimpleName() +
                    "() Illegal name " + productname);
        this.productname = productname;
        this.imageResourceId = imageResourceId;
        this.imageUri = null;
        this.price = price;
        this.description = description;
        this.rating = rating;
    }

    /**
     * Creates an interest type with an image based on a Uri.
     *
     * @param productname Name of the Interest type
     * @param imageUri The Uri of the image.
     */

    public Product(String productname, Uri imageUri, float price, String description, double rating) {
        if (productname == null || productname.trim().isEmpty())
            throw new NullPointerException(getClass().getSimpleName() +
                    "() Illegal name " + productname);
        if (imageUri == null)
            throw new NullPointerException(getClass().getSimpleName() +
                    "() Illegal Image Uri " + imageUri);
        this.productname = productname;
        this.imageUri = imageUri;
        this.imageResourceId = 0;
        this.price = price;
        this.description = description;
        this.rating = rating;
    }

    /**
     * @return The price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @return The ratings for the product
     */

    public double getRating() {
        return rating;
    }

    /**
     * @set The ratings for the product
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * @return The description of this product.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Loads the Image of this interest type in the image view.
     *
     * @param imageView ImageView to display the image.
     */

    public void loadImage(ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (imageUri != null) {
            imageView.setImageURI(imageUri);
        } else {
            imageView.setImageResource(imageResourceId);
        }
    }

    /**
     * @return the name of this product.
     */
    public String getName() {
        return productname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (imageResourceId != product.imageResourceId) return false;
        if (imageUri != null ? !imageUri.equals(product.imageUri) : product.imageUri != null)
            return false;
        if (productname != null ? !productname.equals(product.productname) : product.productname != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productname != null ? productname.hashCode() : 0;
        result = 31 * result + imageResourceId;
        result = 31 * result + (imageUri != null ? imageUri.hashCode() : 0);
        return result;
    }
}
