package mouse.project.lib.web.invoker.processor;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.exception.ControllerException;
import mouse.project.lib.web.invoker.ParamTranslation;
import mouse.project.lib.web.invoker.desc.URLArgumentDesc;
import mouse.project.lib.web.request.RequestURL;

@Service
public class URLArgumentProcessorImpl implements URLArgumentProcessor {

    private final ParamTranslation translation;
    @Auto
    public URLArgumentProcessorImpl(ParamTranslation translation) {
        this.translation = translation;
    }

    @Override
    public Object process(URLArgumentDesc argumentDesc, RequestURL requestURL) {
        String fullUrl = requestURL.getURL();
        String target = argumentDesc.getTarget();
        String pattern = argumentDesc.getPattern();
        return extract(fullUrl, pattern, target, argumentDesc.expectedType());
    }

    private Object extract(String fullUrl, String pattern, String target, Class<?> type) {
        String s = extractFromPath(fullUrl, pattern, target);
        return translation.translate(s, type);
    }

    private String extractFromPath(String fullUrl, String urlPattern, String target) {
        String[] fullUrlParts = fullUrl.split("/");
        String[] urlPatternParts = urlPattern.split("/");

        if (fullUrlParts.length != urlPatternParts.length) {
            throw new ControllerException("URL pattern does not match the full URL");
        }

        for (int i = 0; i < urlPatternParts.length; i++) {
            if (urlPatternParts[i].equals("[" + target + "]")) {
                return fullUrlParts[i];
            }
        }

        throw new ControllerException("Target not found in the URL pattern");
    }
}
