package mouse.project.lib.injector.card.scan;

import mouse.project.lib.annotation.Factory;
import mouse.project.lib.injector.card.definition.CardDefinition;
import mouse.project.lib.injector.card.definition.FactoryCardImpl;
import mouse.project.lib.injector.card.definition.MethodDefinition;
import mouse.project.lib.injector.card.helper.DefinitionHelper;
import mouse.project.lib.injector.card.helper.DefinitionHelperImpl;

import java.lang.reflect.Method;

public class FactoryCardScanner{

    private CardScanner scanner;

    public <T> FactoryClassDescriptor<T> scan(Class<T> tClass) {
        CardDefinition<T> selfCard = scanner.scan(tClass);
        scanFactoryMethods(tClass);
    }

    private <T> void scanFactoryMethods(Class<T> tClass, CardDefinition<T> selfCard) {
        Method[] methods = tClass.getMethods();
        DefinitionHelper definitionHelper = new DefinitionHelperImpl();
        for(Method method : methods) {
            if (!method.isAnnotationPresent(Factory.class)) {
               continue;
            }
            MethodDefinition factoryMethod = definitionHelper.getFactoryMethod(method, selfCard.getType());
            CardDefinition<?> cardDefinition = new FactoryCardImpl<>(factoryMethod);
        }
    }
}
