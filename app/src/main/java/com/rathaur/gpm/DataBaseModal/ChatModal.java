package com.rathaur.gpm.DataBaseModal;

public class ChatModal {

    String message, receiver,sender,type,timestamp;
    boolean dilihat;
    String image;




    public ChatModal() {
    }

    public ChatModal(String message, String receiver, String sender, String type, String timestamp, boolean dilihat) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
        this.type = type;
        this.timestamp = timestamp;
        this.dilihat = dilihat;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDilihat() {
        return dilihat;
    }

    public void setDilihat(boolean dilihat) {
        this.dilihat = dilihat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
