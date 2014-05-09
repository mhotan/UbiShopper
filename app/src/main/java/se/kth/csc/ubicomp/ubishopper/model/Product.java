package se.kth.csc.ubicomp.ubishopper.model;

import android.net.Uri;
import android.widget.ImageView;

import java.util.UUID;

/**
 * Created by mhotan on 5/8/14.
 */
public class Product {

    private final UUID id;

    private final String name;

    private final String description;

    private final float price;

    private Uri imageUri;

    private int imageResourceId;

    private static final int NO_IMAGE = -1;

    /**
     * Creates a new Product
     *
     * @param id ID of the product
     * @param name Name of the product
     * @param description Description of the product
     * @param price Price of the product.
     */
    Product(UUID id, String name, String description, float price) {
        if (id == null) throw new NullPointerException("Null id is not allowed");
        if (name == null) throw new NullPointerException("Null name is not allowed");
        if (description == null)
            description = "";
        if (price < 0.0)
            throw new IllegalArgumentException("Price cannot be negative " + price);
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUri = null;
        this.imageResourceId = NO_IMAGE;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    /**
     * Loads the Image of this interest type in the image view.
     *
     * @param imageView ImageView to display the image.
     */
    public void loadImage(android.widget.ImageView imageView) {
        imageView.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
        if (imageUri != null) {
            imageView.setImageURI(imageUri);
        } else if (this.imageResourceId != NO_IMAGE) {
            imageView.setImageResource(imageResourceId);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }
}
