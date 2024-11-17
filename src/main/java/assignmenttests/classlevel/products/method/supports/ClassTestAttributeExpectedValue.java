package assignmenttests.classlevel.products.method.supports;

public class ClassTestAttributeExpectedValue extends ClassTestAttribute{
    protected Object expectedAttributeValue;

    public ClassTestAttributeExpectedValue(String name, Class<?> datatype, Object expectedAttributeValue) {
        super(name, datatype);
        this.expectedAttributeValue = expectedAttributeValue;
    }

    @Override
    public String toString() {
        return "ClassTestAttributeExpectedValue{" +
            "expectedAttributeValue=" + expectedAttributeValue +
            ", name='" + name + '\'' +
            ", datatype=" + datatype +
            '}';
    }

    public Object getExpectedAttributeValue() {
        return expectedAttributeValue;
    }
}
