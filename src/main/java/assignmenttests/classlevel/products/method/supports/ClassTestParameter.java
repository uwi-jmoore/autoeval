package assignmenttests.classlevel.products.method.supports;

import java.util.Objects;

/**
 * A record class that represents a test parameter consisting of a data type
 * and a value. This class is used to encapsulate a parameter's type and value,
 * which can be used during method or class-level tests.
 */
public record ClassTestParameter(Class<?> datatype, Object value) {

    /**
     * Compares the specified object with this ClassTestParameter for equality.
     * Returns true if the object is the same or if it is an instance of
     * ClassTestParameter and has the same datatype and value.
     * 
     * @param o The object to be compared for equality with this instance.
     * @return true if the specified object is equal to this instance.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassTestParameter that)) return false;
        return Objects.equals(value, that.value)
            && Objects.equals(datatype, that.datatype);
    }

    /**
     * Returns a hash code value for this ClassTestParameter, which is based
     * on its datatype and value. This method ensures that equal objects have
     * the same hash code.
     * 
     * @return The hash code for this ClassTestParameter.
     */
    @Override
    public int hashCode() {
        return Objects.hash(datatype, value);
    }

    /**
     * Returns a string representation of this ClassTestParameter, showing
     * the data type and value. This is useful for logging, debugging, or
     * providing a readable format of the parameter.
     * 
     * @return A string representation of the ClassTestParameter.
     */
    @Override
    public String toString() {
        return "ClassTestParameter{" +
            "datatype='" + datatype + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}
