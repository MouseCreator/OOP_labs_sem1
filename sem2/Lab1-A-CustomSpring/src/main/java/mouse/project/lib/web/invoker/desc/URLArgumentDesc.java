package mouse.project.lib.web.invoker.desc;

public interface URLArgumentDesc extends ArgumentDesc {
    String getPattern();
    Class<?> expectedType();
    String getTarget();
}
