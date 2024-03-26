package mouse.project.lib.web.invoker.desc;

import mouse.project.lib.web.invoker.processor.URLArgumentProcessor;
import mouse.project.lib.web.request.RequestURL;

public class URLArgumentDescImpl implements URLArgumentDesc {

    private final URLArgumentProcessor processor;
    private final String pattern;
    private final String target;
    private final Class<?> type;
    public URLArgumentDescImpl(URLArgumentProcessor processor, Class<?> type, String pattern, String target) {
        this.processor = processor;
        this.pattern = pattern;
        this.type = type;
        this.target = target;
    }

    @Override
    public Object apply(RequestURL requestURL) {
        return processor.process(this, requestURL);
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public Class<?> expectedType() {
        return type;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
