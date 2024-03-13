package mouse.project.lib.injector.card.helper;

import mouse.project.lib.annotation.UseNamed;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.invoke.ParameterDefinition;
import mouse.project.lib.injector.card.invoke.ParameterDefinitionImpl;
import mouse.project.lib.injector.card.invoke.Parameters;
import mouse.project.lib.injector.card.invoke.ParametersImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;
import java.util.stream.Collectors;

public class DefinitionsInspector {

    public Implementation<?> inspectField(Field field) {
        Class<?> requiredType = field.getType();
        UseNamed useNamed = field.getAnnotation(UseNamed.class);
        String named = useNamed == null ? null : useNamed.named();
        return new Implementation<>(requiredType, named);
    }

    public ParameterDefinition inspectParameter(Parameter parameter, int order) {
        UseNamed annotation = parameter.getAnnotation(UseNamed.class);
        String named = annotation == null ? null : annotation.named();
        Class<?> requiredType = parameter.getType();
        Implementation<?> implementation = new Implementation<>(requiredType, named);
        return new ParameterDefinitionImpl(implementation, order);
    }

    public Parameters inspectParameters(Parameter[] parameters) {
        ParametersImpl result = new ParametersImpl();
        int order = 0;
        for (Parameter parameter : parameters) {
            ParameterDefinition pd = inspectParameter(parameter, order);
            result.add(pd);
            order++;
        }
        return result;
    }

    public Parameters inspectConstructor(Constructor<?> constructor) {
        return inspectParameters(constructor.getParameters());
    }

    public Parameters inspectMethod(Method method) {
        return inspectParameters(method.getParameters());
    }

    public Set<Implementation<?>> toRequirements(Parameters parameters) {
        return parameters.getParameterDefinitions()
                .stream()
                .map(ParameterDefinition::type).collect(Collectors.toSet());
    }
}
