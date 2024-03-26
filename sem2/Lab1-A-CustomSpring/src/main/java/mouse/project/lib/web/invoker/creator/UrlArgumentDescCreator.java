package mouse.project.lib.web.invoker.creator;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.annotation.FromURL;
import mouse.project.lib.web.endpoint.Endpoints;
import mouse.project.lib.web.invoker.desc.ArgumentDesc;
import mouse.project.lib.web.invoker.ArgumentSource;
import mouse.project.lib.web.invoker.desc.URLArgumentDescImpl;
import mouse.project.lib.web.invoker.processor.URLArgumentProcessor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
@Service
public class UrlArgumentDescCreator implements DescCreator {
    private final URLArgumentProcessor parameterProcessor;

    private final Endpoints endpoints;
    @Auto
    public UrlArgumentDescCreator(URLArgumentProcessor parameterProcessor, Endpoints endpoints) {
        this.parameterProcessor = parameterProcessor;
        this.endpoints = endpoints;
    }
    @Override
    public ArgumentDesc create(Method method, Parameter parameter) {
        FromURL annotation = parameter.getAnnotation(FromURL.class);
        assert annotation != null;
        String target = annotation.value();
        Class<?> type = parameter.getType();
        String pattern = endpoints.getURL(method).toPattern();
        return new URLArgumentDescImpl(parameterProcessor, type, pattern, target);
    }

    @Override
    public boolean accepts(ArgumentSource source) {
        return source == ArgumentSource.URL;
    }
}
