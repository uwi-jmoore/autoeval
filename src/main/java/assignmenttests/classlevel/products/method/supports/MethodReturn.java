package assignmenttests.classlevel.products.method.supports;

import java.util.Objects;

public record MethodReturn(boolean returnExpected, Class<?> datatype, Object returnValue) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodReturn that)) return false;
        return returnExpected == that.returnExpected
            && Objects.equals(datatype, that.datatype)
            && Objects.equals(returnValue, that.returnValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(returnExpected, datatype, returnValue);
    }

    @Override
    public String toString() {
        return "MethodReturn{" +
            "returnExpected=" + returnExpected +
            ", datatype='" + datatype.getName() + '\'' +
            ", returnValue='" + returnValue + '\'' +
            '}';
    }
}
