package org.example.game.player;

import org.example.game.helper.GameUtils;
import org.example.game.ui.HealthBar;
import org.example.game.ui.ScoreCounter;

public class PlayerManager {
    private Player player;
    private HealthBar healthBar;
    private ScoreCounter scoreCounter;
    public static PlayerManager create() {
        PlayerManager playerManager = new PlayerManager();
        playerManager.player = new Player();
        playerManager.healthBar = new HealthBar(GameUtils.get().getSpriteBuffer().getHearts());
        GameUtils.get().userInterface().addElement(playerManager.healthBar);
        playerManager.scoreCounter = new ScoreCounter();
        GameUtils.get().userInterface().addElement(playerManager.scoreCounter);
        return playerManager;
    }

    public void onDamage() {
        player.damage();
        updateView();
    }
    private void updateView() {
        healthBar.updateView(player.getHealth());
    }


    public void onReset() {
        player.reset();
        updateView();
    }

    public void onScore(int count) {
        player.addScore(count);
        scoreCounter.update(player.getScore());
    }

    public boolean isPlayerDead() {
        return player.isDead();
    }

    public int getScore() {
        return player.getScore();
    }
}
