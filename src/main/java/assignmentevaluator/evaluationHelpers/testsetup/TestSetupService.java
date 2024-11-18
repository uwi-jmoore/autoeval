package assignmentevaluator.evaluationHelpers.testsetup;

import java.util.Map;

/**
 * Abstract class that provides a template for setting up test configurations.
 * This class contains a map that stores configuration details required for the setup of different tests.
 * Concrete subclasses should extend this class and populate the map with specific test setup information.
 */
public abstract class TestSetupService {

    /** A map that stores test setup details as key-value pairs. */
    protected Map<String, Object> map;

    /**
     * Gets the map containing the test setup details.
     * 
     * @return the map of test setup configurations.
     */
    public Map<String, Object> getMap(){
        return map;
    }
}
