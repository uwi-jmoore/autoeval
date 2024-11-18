package assignmenttests.classlevel.products.method.supports;

import java.util.Objects;

/**
 * This abstract class represents an attribute of a class that is being tested.
 * It encapsulates the name and data typeof a class attribute. It is used to define
 * the characteristics of the class attributes for testing purposes.
 */
public abstract class ClassTestAttribute {

    protected String name;
    protected Class<?> datatype;

    /**
     * Constructs a new ClassTestAttribute with the specified name and data type.
     *
     * @param name The name of the class attribute.
     * @param datatype The data type of the class attribute.
     * @throws NullPointerException if either the name or datatype is null.
     */
    public ClassTestAttribute(String name, Class<?> datatype) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.datatype = Objects.requireNonNull(datatype, "Data type cannot be null");
    }

    /**
     * Compares this object with another object to check for equality.
     * Two ClassTestAttribute objects are considered equal if they have the same name and data type.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassTestAttribute that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(datatype, that.datatype);
    }

    /**
     * Returns a hash code value for the ClassTestAttribute object. The hash code is
     * computed based on the name and data type of the attribute.
     *
     * @return The hash code value of the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, datatype);
    }

    /**
     * Returns a string representation of the ClassTestAttribute object,
     * including its name and data type.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "ClassTestAttribute{" +
            "name='" + name + '\'' +
            ", datatype='" + datatype + '\'' +
            '}';
    }

    /**
     * Gets the name of the class attribute.
     *
     * @return The name of the class attribute.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the data type of the class attribute.
     *
     * @return The data type of the class attribute.
     */
    public Class<?> getDatatype() {
        return datatype;
    }
}
