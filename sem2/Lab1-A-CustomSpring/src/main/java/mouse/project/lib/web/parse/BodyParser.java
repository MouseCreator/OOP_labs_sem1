package mouse.project.lib.web.parse;

import java.util.Collection;

public interface BodyParser {
    <T> T parse(String body, Class<T> expectedType);
    <T> Collection<T> parseAll(String body, Class<T> expectedType);
    String unparse(Object object);
}
