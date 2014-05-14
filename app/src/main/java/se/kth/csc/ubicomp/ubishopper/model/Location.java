package se.kth.csc.ubicomp.ubishopper.model;

import java.util.Collection;
import java.util.HashSet;

/**
 * Basic class for a Mock Location
 *
 * Created by mhotan on 5/8/14.
 */
public class Location {

    private final int id;

    private String name;

    private final Collection<Product> products;

    Location(int id, String name) {
        this.id = id;
        this.name = name;
        this.products = new HashSet<Product>();
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void clearProducts() {
        this.products.clear();
    }

    public boolean removeProduct(Product product) {
        return this.products.remove(product);
    }

    public boolean removeProducts(Collection<Product> products) {
        return this.products.removeAll(products);
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addProducts(Collection<Product> products) {
        this.products.addAll(products);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        if (id != location.id) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                '}';
    }
}
