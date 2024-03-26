package mouse.project.lib.web.invoker;

public interface ParameterDesc {
    Class<?> expectedType();
    ParamTranslation translations();
    String name();
}
