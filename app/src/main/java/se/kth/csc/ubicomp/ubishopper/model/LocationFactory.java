package se.kth.csc.ubicomp.ubishopper.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mhotan on 5/8/14.
 */
public class LocationFactory {

    private final Map<Integer, Location> memoryMap;

    private static LocationFactory instance;

    private LocationFactory() {
        memoryMap = new HashMap<Integer, Location>();
    }

    public static LocationFactory getInstance() {
        if (instance == null)
            instance = new LocationFactory();
        return instance;
    }

    /**
     * Adds location.
     *
     * @param location Location to add to this Factory.
     */
    void addLocation(Location location) {
        memoryMap.put(location.getId(), location);
    }

    /**
     * Return a location.
     *
     * @param id ID number of the location.
     * @return Location if it exists, null if it does not exists.
     */
    public Location getLocation(int id) {
        return memoryMap.get(id);
    }

    public Location getLocation(String name) {
        if (name == null) return null;
        for (Location location: memoryMap.values()) {
            if (location.getName().trim().equalsIgnoreCase(name))
                return location;
        }
        return null;
    }

    /**
     * All the locations.
     *
     * @return All the current locations.
     */
    public Collection<Location> getLocations() { return memoryMap.values(); }

}
