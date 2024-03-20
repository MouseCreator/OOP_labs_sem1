package mouse.project.lib.injector.card.scan;

import mouse.project.lib.injector.card.definition.DefinedCard;

public class PackageScannerImpl {
    private final DefinitionsManager definitionsManager;
    private final CardScanner scanner;
    public PackageScannerImpl(DefinitionsManager definitionsManager, CardScanner scanner) {
        this.definitionsManager = definitionsManager;
        this.scanner = scanner;
    }

    public void scanPackage(String packageName) {

    }
    private void scanClass(Class<?> clazz) {
        DefinedCard<?> card = scanner.scan(clazz);
        definitionsManager.onAdd(card);
    }
}
