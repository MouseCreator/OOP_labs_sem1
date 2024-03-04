package org.example.dto;

public class DesktopDTO {
    private int gameState;
    private String details;

    public DesktopDTO(int gameState) {
        this.gameState = gameState;
        details = "";
    }
    public DesktopDTO(int gameState, String s) {
        this.gameState = gameState;
        details = s;
    }

    public DesktopDTO() {
        this.gameState = 0;
        details = "";
    }

    public int getGameState() {
        return gameState;
    }
    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
