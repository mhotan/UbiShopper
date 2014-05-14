package se.kth.csc.ubicomp.ubishopper.model;

import android.net.Uri;
import android.widget.ImageView;
import se.kth.csc.ubicomp.ubishopper.R;

/**
 * Basic data container for User Interest Type.  IE an interest type
 * can be Sports, Fashion, Electronics.
 *
 * Created by mhotan on 5/1/14.
 */
public class InterestType {

    public static final InterestType INTEREST_TYPE_SPORTS = new InterestType("Sports", R.raw.sport);
    public static final InterestType INTEREST_TYPE_OUTDOORS = new InterestType("Outdoors", R.raw.outdoors);
    public static final InterestType INTEREST_TYPE_ELECTRONICS = new InterestType("Electronics", R.raw.eletronics);
    public static final InterestType INTEREST_TYPE_FASHION = new InterestType("Fashion", R.raw.fashion);
    public static final InterestType INTEREST_TYPE_JEWELERY = new InterestType("Jewelry", R.raw.jewelry);
    public static final InterestType INTEREST_TYPE_HOME_FURNISHINGS = new InterestType("Home Furnishing", R.raw.home_furnishing);
    public static final InterestType INTEREST_TYPE_KITCHEN = new InterestType("Kitchen", R.raw.kitchen);
    public static final InterestType[] INTEREST_TYPES = {
            INTEREST_TYPE_SPORTS,
            INTEREST_TYPE_OUTDOORS,
            INTEREST_TYPE_ELECTRONICS,
            INTEREST_TYPE_FASHION,
            INTEREST_TYPE_JEWELERY,
            INTEREST_TYPE_HOME_FURNISHINGS,
            INTEREST_TYPE_KITCHEN,
    };


    /**
     * Name of the type of interest.
     */
    private final String name;

    /**
     * The resource ID if the image is stored locally.
     */
    private final int imageResourceId;

    /**
     * The Image Uri for the web
     */
    private final Uri imageUri;

    /**
     * Creates the Interest Type
     *
     * @param name Name of the Interest type
     * @param imageResourceId The resource Id of the image.
     */
    public InterestType(String name, int imageResourceId) {
        if (name == null || name.trim().isEmpty())
            throw new NullPointerException(getClass().getSimpleName() +
                    "() Illegal name " + name);
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.imageUri = null;
    }

    /**
     * Creates an interest type with an image based on a Uri.
     *
     * @param name Name of the Interest type
     * @param imageUri The Uri of the image.
     */

    public InterestType(String name, Uri imageUri) {
        if (name == null || name.trim().isEmpty())
            throw new NullPointerException(getClass().getSimpleName() +
                    "() Illegal name " + name);
        if (imageUri == null)
            throw new NullPointerException(getClass().getSimpleName() +
                    "() Illegal Image Uri " + imageUri);
        this.name = name;
        this.imageUri = imageUri;
        this.imageResourceId = 0;
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
     * @return the name of this interest type.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestType that = (InterestType) o;
        if (!name.equals(that.name)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
