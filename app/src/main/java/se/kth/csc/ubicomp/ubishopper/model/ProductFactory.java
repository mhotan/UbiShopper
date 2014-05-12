package se.kth.csc.ubicomp.ubishopper.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mhotan on 5/8/14.
 */
public class ProductFactory {

    /**
     * Memory Map.
     */
    private static final Map<UUID, Product> memoryMap = new HashMap<UUID, Product>();

    /**
     * Create a product.
     *
     * @param name
     * @param description
     * @param price
     * @return
     */
    public static Product createProduct(String name, String description, float price) {
        if (name == null) throw new NullPointerException("Null name is not allowed");
        if (description == null)
            description = "";
        if (price < 0.0)
            throw new IllegalArgumentException("Price cannot be negative " + price);
        return new Product(UUID.randomUUID(), name, description, price);
    }

    /**
     *
     *
     * @param name Name of the product
     * @param description Description of the product
     * @param price Price of the product
     * @param imageResource
     * @return
     */
    public static Product createProduct(String name, String description,
                                        float price, int imageResource) {
        Product product = createProduct(name, description, price);
        product.setImageResourceId(imageResource);
        return product;
    }

}
