package com.rathaur.gpm.DataBaseModal;

public class Notice {
    String profession;
    String date;
    String subject;
    String topic;

    public Notice(String profession, String date, String subject, String topic) {
        this.profession = profession;
        this.date = date;
        this.subject = subject;
        this.topic = topic;
    }

    public Notice() {
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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
