package assignmenttests.classlevel.products.method.supports;

import java.util.List;

public class ClassTestRunMethod {
    protected String methodName;
    protected String className;
    protected List<ClassTestParameter> methodParams;

    public void setConstructor(String className){
        this.methodName = className;
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setMethodParams(List<ClassTestParameter> methodParams) {
        this.methodParams = methodParams;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<ClassTestParameter> getMethodParams() {
        return methodParams;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return "ClassTestRunMethod{" +
            "methodName='" + methodName + '\'' +
            ", className='" + className + '\'' +
            ", methodParams=" + methodParams +
            '}';
    }
}
