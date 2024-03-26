package mouse.project.lib.web.invoker;

import lombok.ToString;
import mouse.project.lib.web.exception.BadRequestException;
import mouse.project.lib.web.exception.ControllerException;
import mouse.project.lib.web.parse.BodyParser;
import mouse.project.lib.web.parse.JacksonBodyParser;
import mouse.project.lib.web.request.RequestBody;
import mouse.project.lib.web.request.RequestParameter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ControllerInvokerImpl implements ControllerInvoker {
    private final List<ParameterDesc> parameterDescriptions;
    private final Object controller;
    private final Method method;
    public ControllerInvokerImpl(Object controller, Method method, List<ParameterDesc> parameterDescriptions) {
        this.parameterDescriptions = parameterDescriptions;
        this.controller = controller;
        this.method = method;
    }

    @Override
    public Object invoke(Collection<RequestParameter> parameters, RequestBody body) {
        SatisfiedParams satisfiedParams = new SatisfiedParams(parameterDescriptions);
        for (RequestParameter parameter : parameters) {
            String name = parameter.getName();
            satisfiedParams.satisfy(name, parameter.getValue());
        }
        getBodyInfo(body);
        Object[] res = satisfiedParams.finish();
        method.setAccessible(true);
        try {
            return method.invoke(controller, res);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ControllerException(e);
        }
    }

    private void getBodyInfo(RequestBody body) {
        String str = body.get();
        BodyParser parser = new JacksonBodyParser();
    }

    private static class SatisfiedParams {
        private final Map<String, MethodParam> map;
        public SatisfiedParams(List<ParameterDesc> descList) {
            int order = 0;
            map = new HashMap<>();
            for (ParameterDesc d : descList) {
                map.put(d.name(), new MethodParam(order, d));
            }
        }

        public void satisfy(String name, String value) {
            MethodParam methodParam = map.get(name);
            if (methodParam == null) {
                throw new BadRequestException("Unexpected parameter: " + name);
            }
            initParamWith(methodParam, value);
        }

        private void initParamWith(MethodParam methodParam, String value) {
            methodParam.value = methodParam.desc.translations().translate(value);
        }

        public Object[] finish() {
            List<MethodParam> requireDefaultInitialization = map.values().stream().filter(t -> !t.hasValue()).toList();
            List<MethodParam> notInitialized = new ArrayList<>();
            for (MethodParam param : requireDefaultInitialization) {
                Optional<String> defaultOptional = param.desc.defaultValue();
                if (defaultOptional.isEmpty()) {
                    notInitialized.add(param);
                    continue;
                }
                String s = defaultOptional.get();
                initParamWith(param, s);
            }
            if (!notInitialized.isEmpty()) {
                throw new BadRequestException("Parameters are not satisfied: " + notInitialized);
            }
            return map.values().stream().sorted(Comparator.comparingInt(t -> t.order)).toArray();
        }
    }
    @ToString
    private static class MethodParam {
        int order;
        ParameterDesc desc;
        Object value;
        boolean hasValue() {
            return value != null;
        }
        public MethodParam(int order, ParameterDesc desc) {
            this.order = order;
            this.value = null;
            this.desc = desc;
        }

    }
}
