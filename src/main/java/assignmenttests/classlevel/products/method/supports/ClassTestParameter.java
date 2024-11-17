package assignmenttests.classlevel.products.method.supports;

import java.util.Objects;

public record ClassTestParameter(Class<?> datatype, Object value) {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassTestParameter that)) return false;
        return Objects.equals(value, that.value)
            && Objects.equals(datatype, that.datatype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datatype, value);
    }

    @Override
    public String toString() {
        return "ClassTestParameter{" +
            "datatype='" + datatype + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}
