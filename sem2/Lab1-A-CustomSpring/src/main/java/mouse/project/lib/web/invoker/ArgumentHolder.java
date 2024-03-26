package mouse.project.lib.web.invoker;

public interface ArgumentHolder {
    boolean isBodySource();
    boolean isParamSource();
    BodyDesc toBodyDesc();
    ParameterDesc toParamDesc();
}
