package org.example.dto;

public class MobileDTO {
    private int messageType;
    private String vectorData;
    private String details;
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

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
}
