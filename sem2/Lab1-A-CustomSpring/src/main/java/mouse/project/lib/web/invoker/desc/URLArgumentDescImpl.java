package mouse.project.lib.web.invoker.desc;

import mouse.project.lib.web.invoker.processor.URLArgumentProcessor;
import mouse.project.lib.web.request.RequestURL;

public class URLArgumentDescImpl implements URLArgumentDesc {

    private final URLArgumentProcessor processor;
    private final String pattern;

    public URLArgumentDescImpl(URLArgumentProcessor processor, String pattern) {
        this.processor = processor;
        this.pattern = pattern;
    }

    @Override
    public Object apply(RequestURL requestURL) {
        return processor.process(this, requestURL);
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}
