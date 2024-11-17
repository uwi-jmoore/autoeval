package assignmenttests.classlevel;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassLevelHelpers {
    public static List<String> findMissingKeys(Map<String, Object> map, List<String> requiredKeys){
        List<String> missingKeys = new ArrayList<>();
        for (String k: requiredKeys){
            if(!map.containsKey(k)){
                missingKeys.add(k);
            }
        }
        return missingKeys;
    }


}
