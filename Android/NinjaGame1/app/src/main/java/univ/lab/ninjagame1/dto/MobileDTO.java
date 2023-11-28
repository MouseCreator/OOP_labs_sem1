package univ.lab.ninjagame1.dto;

public class MobileDTO {
    private int messageType;
    private String vectorData;
    private String details;
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
}
