package se.kth.csc.ubicomp.ubishopper.model;

import se.kth.csc.ubicomp.ubishopper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A Mock Model just for the sake of prototype purposes.
 *
 * Created by mhotan on 5/1/14.
 */

public class MockModel {

    private static MockModel instance;

    private final InterestType[] interestTypes;

    private final Product[] products;

    private List<ModelListener> listeners;

    private final List<InterestType> userIntereset;

    // Highlighted values
    private Location highlightedLocation;
    private Product highlightedProduct;

    private MockModel() {
        // Cannot Instantiate outside this class
        interestTypes = InterestType.INTEREST_TYPES;

        List<Product> productList = new ArrayList<Product>();
        List<Product> locOneProducts = new ArrayList<Product>();
        List<Product> locTwoProducts = new ArrayList<Product>();

        ProductFactory factory = ProductFactory.getInstance();

        // Sports Products.
        locOneProducts.add(
                factory.createProduct("Nike Legend Dri-Fit Poly",
                "Synthetic shirt Dri-Fit Poly helps you stay dry and comfortable ",
                23.99f, R.raw.nike_shirt).
                        addInterestType(InterestType.INTEREST_TYPE_SPORTS));
        locOneProducts.add(factory.createProduct("Addidas Hood",
                "newest hood with James Lebron Series",
                43.99f, R.raw.clothes).
                addInterestType(InterestType.INTEREST_TYPE_SPORTS).
                addInterestType(InterestType.INTEREST_TYPE_FASHION));

        // Fashion Products.
        locTwoProducts.add( factory.createProduct("FashionShoes",
                "Micheal Kors new season women",79.99f,
                R.raw.fashionshoes).
                addInterestType(InterestType.INTEREST_TYPE_FASHION));

        // Electronics Products.
        locTwoProducts.add( factory.createProduct("iPhone5S",
                "iPhone 5S, black, 4.0 inches with unlocked and brand new with touchID",
                499.99f, R.raw.iphone).
                addInterestType(InterestType.INTEREST_TYPE_ELECTRONICS));
        locTwoProducts.add( factory.createProduct("GoPro HERO3+",
                "Black Edition",
                399.99f, R.raw.go_pro).
                addInterestType(InterestType.INTEREST_TYPE_ELECTRONICS));
        locTwoProducts.add( factory.createProduct("Monster Headphones",
                "Monster Diesel On-Ear Headphones",
                79.95f, R.raw.monster_head_phones).
                addInterestType(InterestType.INTEREST_TYPE_ELECTRONICS).
                addInterestType(InterestType.INTEREST_TYPE_FASHION));


        locTwoProducts.add( factory.createProduct("Guitar",
                "Classical guitar for new learners",169.99f,
                R.raw.guitar));
        locTwoProducts.add( factory.createProduct("Crown Banana",
                "per kg, fresh from Malaysia tropical",4.99f,
                R.raw.fruits));
        locOneProducts.add( factory.createProduct("Organic Tomato",
                "per kg, local farm produced, GMO free!",3.99f,
                R.raw.vegetable));
        locOneProducts.add( factory.createProduct("Red Bull",
                "Redbull keep you energetic all day long",5.99f,
                R.raw.drink));
        locOneProducts.add( factory.createProduct("Moleskine Music Note",
                "Legendary Notebook for all your daily inspirations",17.99f,
                R.raw.moleskine));

        // Populate the array.
        productList.addAll(locOneProducts);
        productList.addAll(locTwoProducts);

        // Update the highlightedLocation with highlightedProduct list
        LocationFactory locFactory = LocationFactory.getInstance();
        locFactory.addLocation(MockLocations.BATHROOM);
        locFactory.addLocation(MockLocations.CHECKOUT_COUNTER);
        locFactory.addLocation(MockLocations.ELECTRONICS_EXPO);
        locFactory.getLocation(1).addProducts(locOneProducts);
        locFactory.getLocation(2).addProducts(locTwoProducts);

        products = new Product[productList.size()];
        productList.toArray(products);

        listeners = new ArrayList<ModelListener>();
        userIntereset = new ArrayList<InterestType>();
    }

    public List<InterestType> getUserIntereset() {
        return userIntereset;
    }

    public Location getHighlightedLocation() {
        return highlightedLocation;
    }



    public List<Product> getNearbyProducts() {

        // TODO Based off of the current highlightedLocation
        List<Product> products = new ArrayList<Product>();
        if (highlightedLocation != null) {
            // Search the nearby use the highlightedLocation id to reference the products nearby
            products.addAll(highlightedLocation.getProducts());
        }
        return products;
    }

    /**
     * @return Instance of this model.
     */
    public static MockModel getInstance() {
        if (instance == null)
            instance = new MockModel();
        return instance;
    }

    /**
     * @return Interest Types.
     */
    public InterestType[] getInterestTypes() {
        return interestTypes;
    }

    /**
     * @return All the products of this/
     */
    public Product[] getProducts() {
        return products;
    }


    public Product getHighlightedProduct() {
        return highlightedProduct;
    }

    public void setHighlightedLocation(Location highlightedLocation) {
        if (this.highlightedLocation == null && highlightedLocation == null) return;
        if (this.highlightedLocation != null && this.highlightedLocation.equals(highlightedLocation)) return;
        this.highlightedLocation = highlightedLocation;
        for (ModelListener listener: listeners) {
            listener.onHighlightedLocationChanged(this.highlightedLocation);
        }
    }

    public void setHighlightedProduct(Product highlightedProduct) {
        if (highlightedProduct == null && this.highlightedProduct == null) return;
        if (this.highlightedProduct != null && this.highlightedProduct.equals(highlightedProduct)) return;
        this.highlightedProduct = highlightedProduct;
        for (ModelListener listener: listeners) {
            listener.onHighlightedProductChanged(this.highlightedProduct);
        }
    }

    public void addListener(ModelListener listener) {
        if (listener == null) return;
        this.listeners.add(listener);
    }

    public boolean removeListener(ModelListener listener) {
        return this.listeners.remove(listener);
    }

    public interface ModelListener {

        /**
         * @param location Location that is now set.
         */
        public void onHighlightedLocationChanged(Location location);

        public void onHighlightedProductChanged(Product product);

    }
}
