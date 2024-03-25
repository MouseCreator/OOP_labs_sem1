package mouse.project.lib.web.scan;
import java.lang.reflect.Method;

public interface WebAnnotationProcessor {
    boolean canProcess(Method method);
    Registration process(String url, Object controller, Method method);
}
