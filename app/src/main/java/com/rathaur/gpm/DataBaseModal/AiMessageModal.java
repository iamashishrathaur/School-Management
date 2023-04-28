package com.rathaur.gpm.DataBaseModal;

public class AiMessageModal {
    public static final String SENT_BY_ME="me";
    public static final String SENT_BY_BOT="bot";
    String message;
    String sentBy;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public AiMessageModal(String message, String sentBy) {
        this.message = message;
        this.sentBy = sentBy;
    }
}
