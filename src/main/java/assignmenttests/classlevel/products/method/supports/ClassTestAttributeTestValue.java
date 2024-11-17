package assignmenttests.classlevel.products.method.supports;

public class ClassTestAttributeTestValue extends ClassTestAttribute{
    protected Object testSetAttributeValue;

    public ClassTestAttributeTestValue(String name, Class<?> datatype, Object testSetValue) {
        super(name, datatype);
        this.testSetAttributeValue = testSetValue;

    }

    @Override
    public String toString() {
        return "ClassTestAttributeTestValue{" +
            "testSetAttributeValue=" + testSetAttributeValue +
            ", name='" + name + '\'' +
            ", datatype=" + datatype +
            '}';
    }

    public Object getTestSetAttributeValue() {
        return testSetAttributeValue;
    }

}
