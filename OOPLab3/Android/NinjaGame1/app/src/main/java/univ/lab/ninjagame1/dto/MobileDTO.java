package univ.lab.ninjagame1.dto;

public class MobileDTO {
    private int messageType;
    private String vectorData;
    private String details;
    private int gameMode;
    public int getMessageType() {
        return messageType;
    }
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
    public String getVectorData() {
        return vectorData;
    }
    public void setVectorData(String vectorData) {
        this.vectorData = vectorData;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public int getGameMode() {
        return gameMode;
    }
    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }
}
