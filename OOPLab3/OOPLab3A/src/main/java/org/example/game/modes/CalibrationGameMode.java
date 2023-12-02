package org.example.game.modes;

import org.example.dto.MobileDTO;

import java.awt.*;

public class CalibrationGameMode implements GameMode {
    @Override
    public void onStart() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void processMessage(MobileDTO mobileDTO) {
        if (mobileDTO.getMessageType() == 4) {
            //switchToMode(GameState.SHOOTING);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {

    }

    @Override
    public void update() {

    }
}
