package mouse.project.lib.injector.card.helper;

import mouse.project.lib.annotation.Collect;
import mouse.project.lib.annotation.UseNamed;
import mouse.project.lib.exception.MissingAnnotationException;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.definition.ParameterDefinition;
import mouse.project.lib.injector.card.definition.ParameterDefinitionImpl;
import mouse.project.lib.injector.card.invoke.Parameters;
import mouse.project.lib.injector.card.invoke.ParametersImpl;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class DefinitionsInspector {

    public FieldInfo inspectField(Field field) {
        Class<?> requiredType = field.getType();
        UseNamed useNamed = field.getAnnotation(UseNamed.class);
        String named = useNamed == null ? null : useNamed.named();
        if (isCollection(field)) {
            return inspectCollectionField(named, field);
        }
        return new FieldInfo(new Implementation<>(requiredType, named), null);
    }

    public ParameterDefinition inspectParameter(Parameter parameter, int order) {
        UseNamed annotation = parameter.getAnnotation(UseNamed.class);
        String named = annotation == null ? null : annotation.named();
        Class<?> requiredType = parameter.getType();
        if (isCollection(parameter)) {
            return inspectCollectionParameter(named, parameter, order);
        } else {
            Implementation<?> implementation = new Implementation<>(requiredType, named);
            return new ParameterDefinitionImpl(implementation, order, null);
        }
    }
    private FieldInfo inspectCollectionField(String named, Field field) {
        Class<?> collection = field.getType();
        Implementation<?> implementation = toCollectedImplementation(named, field);
        return new FieldInfo(implementation, collection);
    }

    private Implementation<?> toCollectedImplementation(String named, AnnotatedElement el) {
        Collect collect = el.getAnnotation(Collect.class);
        if (collect == null) {
            throw new MissingAnnotationException("Collection field/parameter must be annotated with @Collect " +
                    "and specify collection genetic type");
        }
        Class<?> type = collect.collectionClass();
        return new Implementation<>(type, named);
    }


    private ParameterDefinition inspectCollectionParameter(String named, Parameter parameter, int order) {
        Class<?> collection = parameter.getType();
        Implementation<?> implementation = toCollectedImplementation(named, parameter);
        return new ParameterDefinitionImpl(implementation, order, collection);
    }

    private boolean isCollection(Field field) {
        return Collection.class.isAssignableFrom(field.getType());
    }
    private boolean isCollection(Parameter parameter) {
        return Collection.class.isAssignableFrom(parameter.getType());
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
