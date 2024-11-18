package assignmenttests.classlevel.products.method.supports;

import java.util.List;

/**
 * A class that represents a method to be run in a test, including the method's
 * name, the class name that contains the method, and a list of parameters
 * that the method requires.
 */
public class ClassTestRunMethod {
    protected String methodName;
    protected String className;
    protected List<ClassTestParameter> methodParams;

    /**
     * Sets the constructor details, including the class name and method name.
     * This method is used to initialize both the methodName and className.
     * 
     * @param className The name of the class containing the method.
     */
    public void setConstructor(String className){
        this.methodName = className;
        this.className = className;
    }

    /**
     * Sets the name of the method to be tested.
     * 
     * @param methodName The name of the method to run.
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Sets the list of parameters for the method being tested.
     * 
     * @param methodParams The list of parameters that the method requires.
     */
    public void setMethodParams(List<ClassTestParameter> methodParams) {
        this.methodParams = methodParams;
    }

    /**
     * Sets the name of the class that contains the method to be tested.
     * 
     * @param className The name of the class containing the method.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Returns the name of the method to be tested.
     * 
     * @return The method name.
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Returns the list of parameters for the method to be tested.
     * 
     * @return The list of method parameters.
     */
    public List<ClassTestParameter> getMethodParams() {
        return methodParams;
    }

    /**
     * Returns the name of the class containing the method to be tested.
     * 
     * @return The class name.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Returns a string representation of the ClassTestRunMethod object,
     * showing the method name, class name, and method parameters.
     * 
     * @return A string representation of the ClassTestRunMethod.
     */
    @Override
    public String toString() {
        return "ClassTestRunMethod{" +
            "methodName='" + methodName + '\'' +
            ", className='" + className + '\'' +
            ", methodParams=" + methodParams +
            '}';
    }
}
