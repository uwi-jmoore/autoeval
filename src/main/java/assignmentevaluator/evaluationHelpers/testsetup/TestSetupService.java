package assignmentevaluator.evaluationHelpers.testsetup;

import java.util.Map;

public abstract class TestSetupService {
    protected Map<String, Object> map;
    public Map<String,Object> getMap(){
        return map;
    }
}
