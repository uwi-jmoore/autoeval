package assignmenttests.classlevel.products.attribute.supports;

public class AttributeDataType {
    private Class<?> dataType;
    private Object generic;

    public AttributeDataType(Class<?> inputDataType, Object inputGeneric){
        this.dataType = inputDataType;
        this.generic = inputGeneric;
    }
    public void setDataType(Class<?> dataType) {
        this.dataType = dataType;
    }

    public Class<?> getDataType() {
        return dataType;
    }

    public void setGeneric(Object generic) {
        this.generic = generic;
    }

    public Object getGeneric() {
        return generic;
    }
}

