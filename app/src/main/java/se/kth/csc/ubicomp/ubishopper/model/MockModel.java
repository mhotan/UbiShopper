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
}
