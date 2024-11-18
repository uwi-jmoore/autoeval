package assignmenttests.classlevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class providing helper methods for class-level tests.
 * 
 * <p>
 * The {@code ClassLevelHelpers} class includes utility methods for performing common
 * operations in class-level tests, such as checking for missing required keys in a map.
 * </p>
 */
public class ClassLevelHelpers {

    /**
     * Finds and returns a list of keys that are missing from the given map.
     * 
     * <p>
     * This method checks whether all required keys are present in the provided map.
     * If any required key is missing, it is added to the returned list.
     * </p>
     * 
     * @param map The map to check for required keys.
     * @param requiredKeys The list of keys that are required.
     * @return A list of missing keys. If no keys are missing, an empty list is returned.
     */
    public static List<String> findMissingKeys(Map<String, Object> map, List<String> requiredKeys) {
        List<String> missingKeys = new ArrayList<>();
        for (String k : requiredKeys) {
            if (!map.containsKey(k)) {
                missingKeys.add(k);
            }
        }
        return missingKeys;
    }
}
