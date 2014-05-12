package se.kth.csc.ubicomp.ubishopper.model;

import java.util.ArrayList;
import java.util.List;

import se.kth.csc.ubicomp.ubishopper.R;

/**
 * A Mock Model just for the sake of prototype purposes.
 *
 * Created by mhotan on 5/1/14.
 */

public class MockModel {

    private static MockModel instance;

    private final InterestType[] interestTypes;

    private final Product[] products;

    private MockModel() {
        // Cannot Instantiate outside this class

        // Populate the Mock Model full of data.
        List<InterestType> typeList = new ArrayList<InterestType>();
        typeList.add(new InterestType("Sports", R.raw.sport));
        typeList.add(new InterestType("Outdoors", R.raw.outdoors));
        typeList.add(new InterestType("Electronics", R.raw.eletronics));
        typeList.add(new InterestType("Fashion", R.raw.fashion));
        typeList.add(new InterestType("Jewelry", R.raw.jewelry));
        typeList.add(new InterestType("Home Furnishing", R.raw.home_furnishing));
        typeList.add(new InterestType("Kitchen", R.raw.kitchen));
        interestTypes = new InterestType[typeList.size()];
        typeList.toArray(interestTypes);

        List<Product> productList = new ArrayList<Product>();
        productList.add(ProductFactory.createProduct("Nike Legend Dri-Fit Poly",
                "Synthetic shirt Dri-Fit Poly helps you stay dry and comfortable ",
                23.99f, R.raw.nike_shirt));
        // TODO Add more well defined Products
        productList.add( ProductFactory.createProduct("Addidas Hood",
                "newest hood with James Lebron Series",
                43.99f, R.raw.clothes));
        productList.add( ProductFactory.createProduct("FashionShoes",
                "Micheal Kors new season women",79.99f,
                R.raw.fashionshoes));
        productList.add( ProductFactory.createProduct("ihphone5s",
                "iphone5s,black, 4.0inches with unlocked and brand new with touchID",
                499.99f,R.raw.iphone));
        productList.add( ProductFactory.createProduct("Guitar",
                "Classical guitar for new learners",169.99f,
                R.raw.guitar));
        productList.add( ProductFactory.createProduct("Crown Banana",
                "per kg, fresh from Malaysia tropical",4.99f,
                R.raw.fruits));
        productList.add( ProductFactory.createProduct("Organic Tomato",
                "per kg, local farm produced, GMO free!",3.99f,
                R.raw.vegetable));
        productList.add( ProductFactory.createProduct("Red Bull",
                "Redbull keep you energetic all day long",5.99f,
                R.raw.drink));
        productList.add( ProductFactory.createProduct("Moleskine Music Note",
                "Legendary Notebook for all your daily inspirations",17.99f,
                R.raw.moleskine));


        // Populate the array.
        products = new Product[productList.size()];
        productList.toArray(products);
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
}
