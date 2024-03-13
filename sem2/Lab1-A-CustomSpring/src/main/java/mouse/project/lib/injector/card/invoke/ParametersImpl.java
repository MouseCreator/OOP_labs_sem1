package mouse.project.lib.injector.card.invoke;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ParametersImpl implements Parameters {

    private final List<ParameterDefinition> parameterDefinitions;

    public ParametersImpl() {
        parameterDefinitions = new ArrayList<>();
    }

    public void add(ParameterDefinition parameterDefinition) {
        parameterDefinitions.add(parameterDefinition);
    }

    @Override
    public List<ParameterDefinition> getParameterDefinitions() {
        return parameterDefinitions
                .stream()
                .sorted(Comparator.comparingInt(ParameterDefinition::order))
                .toList();
    }

    @Override
    public int size() {
        return parameterDefinitions.size();
    }

    @Override
    @NonNull
    public Iterator<ParameterDefinition> iterator() {
        return getParameterDefinitions().iterator();
    }
}
