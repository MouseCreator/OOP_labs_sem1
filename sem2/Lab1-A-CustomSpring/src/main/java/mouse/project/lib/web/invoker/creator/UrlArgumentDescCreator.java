package mouse.project.lib.web.invoker.creator;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.annotation.FromURL;
import mouse.project.lib.web.context.EndpointContext;
import mouse.project.lib.web.invoker.desc.ArgumentDesc;
import mouse.project.lib.web.invoker.ArgumentSource;
import mouse.project.lib.web.invoker.desc.URLArgumentDescImpl;
import mouse.project.lib.web.invoker.processor.URLArgumentProcessor;

import java.lang.reflect.Parameter;
@Service
public class UrlArgumentDescCreator implements DescCreator {
    private final URLArgumentProcessor parameterProcessor;
    @Auto
    public UrlArgumentDescCreator(URLArgumentProcessor parameterProcessor) {
        this.parameterProcessor = parameterProcessor;
    }
    @Override
    public ArgumentDesc create(EndpointContext context, Parameter parameter) {
        FromURL annotation = parameter.getAnnotation(FromURL.class);
        assert annotation != null;
        String target = annotation.value();
        Class<?> type = parameter.getType();
        String pattern = context.getUrl().toPattern();
        return new URLArgumentDescImpl(parameterProcessor, type, pattern, target);
    }

    @Override
    public boolean accepts(ArgumentSource source) {
        return source == ArgumentSource.URL;
    }
}
