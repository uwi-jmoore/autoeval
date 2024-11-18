package assignmentevaluator.evaluationHelpers.testsetup.classlevel;

import java.io.File;
import java.util.HashMap;

import assignmentevaluator.evaluationHelpers.testsetup.TestSetupService;
import assignmenttests.classlevel.products.simulation.InstantiationMethodTarget;

/**
 * Configures the setup for object instantiation tests.
 * This class is responsible for setting up the object to be instantiated, 
 * the method to instantiate the object, and the file associated with the test.
 */
public class ObjectInstantiationTestSetup extends TestSetupService {

    /**
     * Constructs a new `ObjectInstantiationTestSetup` instance.
     * Initializes the mappings for the object, target method, and file as null.
     */
    public ObjectInstantiationTestSetup(){
        map = new HashMap<>();
        map.put("object", null);
        map.put("target", null);
        map.put("file", null);
    }

    /**
     * Sets the object to be instantiated in the test.
     * 
     * @param inObj the object that is to be instantiated.
     */
    public void setObject(Object inObj){
        map.put("object", inObj);
    }

    /**
     * Sets the target method used for object instantiation in the test.
     *
     * @param inMethod the method responsible for instantiating the object.
     */
    public void setMethodTarget(InstantiationMethodTarget inMethod){
        map.put("target", inMethod);
    }

    /**
     * Sets the file associated with the instantiation test.
     *
     * @param directory the file or directory related to the instantiation test.
     */
    public void setFile(File directory){
        map.put("file", directory);
    }
}
