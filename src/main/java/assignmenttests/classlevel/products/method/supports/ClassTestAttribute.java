package assignmenttests.classlevel.products.method.supports;

import java.util.Objects;

public abstract class ClassTestAttribute{
    protected String name;
    protected Class<?> datatype;

    public ClassTestAttribute(String name, Class<?> datatype){
        this.name = Objects.requireNonNull(name);//name of class attribute
        this.datatype = Objects.requireNonNull(datatype);//data type of attribute
        //this.expectedAttributeValue = expectedAttributeValue; //if null, java datatype default value expected for attribute
        //this.testSetValue = testSetValue; //if null, test does not require setup of class attribute values
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassTestAttribute that)) return false;
        return Objects.equals(name, that.name)
            && Objects.equals(datatype, that.datatype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, datatype);
    }

    @Override
    public String toString() {
        return "ClassTestAttribute{" +
            "name='" + name + '\'' +
            ", datatype='" + datatype + '\'' +
            '}';
    }

    public String getName() {
        return name;
    }

    public Class<?> getDatatype() {
        return datatype;
    }
}
