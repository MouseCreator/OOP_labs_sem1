package org.example.game.modes;

import org.example.dto.MobileDTO;
import org.example.game.drawable.DrawManager;
import org.example.model.Updatable;

public interface GameMode extends Updatable, DrawManager {
    void onStart();
    void onExit();
    void onPause();
    void processMessage(MobileDTO mobileDTO);
}
