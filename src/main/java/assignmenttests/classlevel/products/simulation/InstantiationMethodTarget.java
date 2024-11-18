package assignmenttests.classlevel.products.simulation;

/**
 * Represents a target method that is being tracked for instantiation within a class.
 * This record stores the method name and its return type, which can be used to detect 
 * or validate method calls during testing or analysis.
 * 
 * <p>The {@code InstantiationMethodTarget} is a simple data structure that holds two properties:</p>
 * <ul>
 *     <li>{@code methodName} - The name of the method being tracked.</li>
 *     <li>{@code methodType} - The return type of the method.</li>
 * </ul>
 */
public record InstantiationMethodTarget(String methodName, Class<?> methodType) {

    /**
     * Returns a string representation of the {@code InstantiationMethodTarget} record.
     * This method provides a formatted output with the method name and type for easier inspection.
     * 
     * @return A string representation of the method name and its type.
     */
    @Override
    public String toString() {
        return "InstantiationMethodTarget{" +
            "methodName='" + methodName + '\'' +
            ", methodType=" + methodType +
            '}';
    }
}
