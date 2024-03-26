package mouse.project.lib.web.invoker;
@FunctionalInterface
public interface ParamTranslation {
    Object translate(String from);
}
