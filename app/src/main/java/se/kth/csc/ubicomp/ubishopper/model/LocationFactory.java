package se.kth.csc.ubicomp.ubishopper.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mhotan on 5/8/14.
 */
public class LocationFactory {

    private static final Map<Integer, Location> memoryMap = new HashMap<Integer, Location>();

    /**
     * Adds location.
     *
     * @param location Location to add to this Factory.
     */
    static void addLocation(Location location) {
        memoryMap.put(location.getId(), location);
    }

    /**
     * Return a location.
     *
     * @param id ID number of the location.
     * @return Location if it exists, null if it does not exists.
     */
    public static Location getLocation(int id) {
        return memoryMap.get(id);
    }

    /**
     * All the locations.
     *
     * @return All the current locations.
     */
    public static Collection<Location> getLocations() { return memoryMap.values(); }

}
