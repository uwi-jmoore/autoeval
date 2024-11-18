package assignmenttests.classlevel.products.simulation;

/**
 * A record representing an expected method call in a simulation test. 
 * 
 * <p>
 * The {@code methodName} field specifies the name of the method being tested, 
 * while the {@code expectedCalls} field specifies how many times that method is expected to be called 
 * during the execution of the code under test.
 * </p>
 */
public record MethodCalls(String methodName, int expectedCalls) {
}
