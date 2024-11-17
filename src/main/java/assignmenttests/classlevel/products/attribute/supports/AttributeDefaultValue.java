package assignmenttests.classlevel.products.attribute.supports;

public class AttributeDefaultValue {
    private Object defaultValue;
    private Class<?> defaultValueDataType;

    public void setAttrDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDefaultValueDataType(Class<?> defaultValueDataType) {
        this.defaultValueDataType = defaultValueDataType;
    }

    public Object getAttrDefaultValue() {
        return defaultValue;
    }

    public Class<?> getAttrDefaultValueDataType() {
        return defaultValueDataType;
    }

    @Override
    public String toString() {
        return "AttributeDefaultValue{" +
            "defaultValue=" + defaultValue +
            ", defaultValueDataType=" + defaultValueDataType +
            '}';
    }
}
