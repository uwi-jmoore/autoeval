package assignmenttests.classlevel.products.method.supports;

import java.util.Objects;

/**
 * A record representing the return value of a method during testing.
 * This includes whether the expected return value matches, the expected
 * data type, and the actual return value from the method execution.
 */
public record MethodReturn(boolean returnExpected, Class<?> datatype, Object returnValue) {

    /**
     * Compares this MethodReturn to another object to check if they are equal.
     * 
     * @param o The object to compare with.
     * @return true if the two objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodReturn that)) return false;
        return returnExpected == that.returnExpected
            && Objects.equals(datatype, that.datatype)
            && Objects.equals(returnValue, that.returnValue);
    }

    /**
     * Returns a hash code for this MethodReturn based on its fields.
     * 
     * @return A hash code for this MethodReturn.
     */
    @Override
    public int hashCode() {
        return Objects.hash(returnExpected, datatype, returnValue);
    }

    /**
     * Returns a string representation of the MethodReturn, showing whether the
     * return value is expected, the data type of the return value, and the
     * actual return value.
     * 
     * @return A string representation of the MethodReturn.
     */
    @Override
    public String toString() {
        return "MethodReturn{" +
            "returnExpected=" + returnExpected +
            ", datatype='" + datatype.getName() + '\'' +
            ", returnValue='" + returnValue + '\'' +
            '}';
    }
}
