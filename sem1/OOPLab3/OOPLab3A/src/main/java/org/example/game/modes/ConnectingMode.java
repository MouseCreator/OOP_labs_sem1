package org.example.game.modes;

import org.example.utils.ConstUtils;
import org.example.game.event.Event;
import org.example.game.helper.GameUtils;
import org.example.game.ui.UIText;

public class ConnectingMode implements GameMode{
    private UIText connectingText;

    public ConnectingMode() {
    }

    @Override
    public void onStart() {
        String text = "Connecting...";
        connectingText = new UIText("SCORE_RESULT", text, ConstUtils.center, true);
        GameUtils.get().userInterface().addElement(connectingText);
        GameUtils.get().userInterface().hide("SCORE");
        GameUtils.get().userInterface().hide("HEALTH");
    }

    @Override
    public void onExit() {
        GameUtils.get().userInterface().removeElement(connectingText);
        GameUtils.get().userInterface().show("SCORE");
        GameUtils.get().userInterface().show("HEALTH");
    }

    @Override
    public void handleEvent(Event event) {

    }

    @Override
    public void update() {

    }
}
