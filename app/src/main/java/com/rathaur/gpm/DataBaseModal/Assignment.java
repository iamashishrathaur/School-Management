package com.rathaur.gpm.DataBaseModal;

public class Assignment {
    String tname,subject,sclass,topic,date,ldate;

    public Assignment() {
    }

    public Assignment(String tname, String subject, String sclass, String topic, String date, String ldate) {
        this.tname = tname;
        this.subject = subject;
        this.sclass = sclass;
        this.topic = topic;
        this.date = date;
        this.ldate = ldate;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSclass() {
        return sclass;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLdate() {
        return ldate;
    }

    public void setLdate(String ldate) {
        this.ldate = ldate;
    }
}
