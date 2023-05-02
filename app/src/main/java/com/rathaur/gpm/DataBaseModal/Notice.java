package com.rathaur.gpm.DataBaseModal;

public class Notice {
    String date;
    String subject;
    String topic;

    public Notice(String date, String subject, String topic) {
        this.date = date;
        this.subject = subject;
        this.topic = topic;
    }

    public Notice() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}