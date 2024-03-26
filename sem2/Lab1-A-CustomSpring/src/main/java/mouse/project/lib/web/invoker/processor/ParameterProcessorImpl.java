package mouse.project.lib.web.invoker.processor;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.exception.BadRequestException;
import mouse.project.lib.web.invoker.ParamTranslation;
import mouse.project.lib.web.invoker.desc.ParameterDesc;
import mouse.project.lib.web.request.RequestParameter;
import mouse.project.lib.web.request.RequestURL;

import java.util.Optional;

@Service
public class ParameterProcessorImpl implements ParameterProcessor {

    private final ParamTranslation paramTranslation;
    @Auto
    public ParameterProcessorImpl(ParamTranslation paramTranslation) {
        this.paramTranslation = paramTranslation;
    }

    @Override
    public Object process(ParameterDesc parameterDesc, RequestURL requestURL) {
        Optional<RequestParameter> param =
                requestURL.getParameters().stream().filter(p -> p.getName().equals(parameterDesc.name())).findFirst();
        if (param.isEmpty()) {
            Optional<String> defaultValue = parameterDesc.defaultValue();
            if (defaultValue.isEmpty()) {
                throw new BadRequestException("No default value for missing parameter: " + parameterDesc);
            }
            return toExpected(parameterDesc.expectedType(), defaultValue.get());
        }
        return toExpected(parameterDesc.expectedType(), param.get().getValue());
    }

    private Object toExpected(Class<?> type, String value) {
        return paramTranslation.translate(value, type);
    }
}
