package org.example.game.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UserInterface {
    private final List<UIObject> uiObjects = new ArrayList<>();
    public void eachElement(Consumer<UIObject> c) {
        uiObjects.forEach(c);
    }
    public List<UIObject> get() {
        return uiObjects;
    }

    public void addElement(UIObject uiObject) {
        uiObjects.add(uiObject);
    }
    public void removeElement(UIObject uiObject) {
        uiObjects.remove(uiObject);
    }
}
