package se.kth.csc.ubicomp.ubishopper.model;

import android.net.Uri;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Class that represents a product that can be found in the store.
 *
 * Created by mhotan on 5/8/14.
 */
public class Product {

    private final UUID id;

    private final String name;

    private final String description;

    private final float price;

    private Uri imageUri;

    private int imageResourceId;

    private boolean onSale;

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
        this.onSale = false;
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

    public String getPriceString() {
        return NumberFormat.getCurrencyInstance().format(getPrice());
    }

    /**
     * Returns this product as a JSON Object. Latitude and Longitude are not included in the
     * returned JSON object.  They can put applied late.
     *
     * @return The
     */
    public JSONObject toPOIInformation() {
        Map<String, String> poiInformation = new HashMap<String, String>();
        poiInformation.put(POIConstants.ATTR_ID, getId().toString());
        poiInformation.put(POIConstants.ATTR_NAME, getName());
        poiInformation.put(POIConstants.ATTR_DESCRIPTION, onSale ?
                "SALE " : "" + getPriceString());
        poiInformation.put(POIConstants.ATTR_ALTITUDE, String.valueOf(POIConstants.UNKNOWN_ALTITUDE));
        return new JSONObject(poiInformation);
    }


}
