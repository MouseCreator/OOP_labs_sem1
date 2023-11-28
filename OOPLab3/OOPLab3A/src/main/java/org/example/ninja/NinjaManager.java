package org.example.ninja;

import org.example.model.Ninja;
import org.example.model.Symbol;

public class NinjaManager {
    public void update(Ninja ninja, Symbol symbol) {
        if (ninja.isCentralized() && symbol.isHidden()) {
            symbol.show();
            symbol.changeToRandom();
        }
    }
}
