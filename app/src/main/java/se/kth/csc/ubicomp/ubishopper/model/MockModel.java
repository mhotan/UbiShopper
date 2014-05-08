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

        // Populate the Product Model full of data.
        List<Product> productList = new ArrayList<Product>();
        productList.add(new Product("Adidas", R.raw.clothes,56,"newest hood with James Lebron",4.20));
        productList.add(new Product("FashionShoes", R.raw.fashionshoes,81,"Micheal Kors new season women",3.8));
        productList.add(new Product("Iphone", R.raw.iphone,588,"iphone5s,black, 4.0inches with unlocked and brand new with touchID",4.0));
        productList.add(new Product("Guitar", R.raw.guitar,120,"Classical guitar for new learners",3.6));
        productList.add(new Product("Banana", R.raw.fruits,2,"per kg, fresh from Malaysia",4.31));
        productList.add(new Product("Tomato", R.raw.vegetable,3,"per kg, local farm produced, GMO free!",4.6));
        productList.add(new Product("Drink", R.raw.drink,6,"Redbull keep you energetic all day long",3.4));
        products = new Product[productList.size()];
        typeList.toArray(products);

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
     * @return Product Names.
     */
    public Product[] getProducts() {
        return products;
    }

}
