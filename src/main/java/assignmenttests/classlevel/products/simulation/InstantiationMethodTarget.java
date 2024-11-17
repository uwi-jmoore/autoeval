package assignmenttests.classlevel.products.simulation;

public record InstantiationMethodTarget(String methodName, Class<?> methodType) {
    @Override
    public String toString() {
        return "InstantiationMethodTarget{" +
            "methodName='" + methodName + '\'' +
            ", methodType=" + methodType +
            '}';
    }
}
