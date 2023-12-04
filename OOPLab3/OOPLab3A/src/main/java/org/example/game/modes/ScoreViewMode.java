package org.example.game.modes;

import org.example.engine.ConstUtils;
import org.example.game.event.Event;
import org.example.game.helper.GameUtils;
import org.example.game.player.PlayerManager;
import org.example.game.ui.UIText;

public class ScoreViewMode implements GameMode{

    private UIText scoreText;
    private final PlayerManager playerManager;

    public ScoreViewMode(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public void onStart() {
        String text = "Result: " + playerManager.getScore();
        scoreText = new UIText("SCORE_RESULT", text, ConstUtils.center, true);
        GameUtils.get().userInterface().addElement(scoreText);
        GameUtils.get().userInterface().hide("SCORE");
    }

    @Override
    public void onExit() {
        playerManager.onReset();
        GameUtils.get().userInterface().removeElement(scoreText);
        GameUtils.get().userInterface().show("SCORE");
    }

    @Override
    public void handleEvent(Event event) {

    }

    @Override
    public void update() {

    }
}
