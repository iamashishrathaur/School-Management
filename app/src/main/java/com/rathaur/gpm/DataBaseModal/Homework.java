package com.rathaur.gpm.DataBaseModal;

public class Homework {
    String subject;
    String heading;
    String content;
    String time;

    public Homework() {
    }

    public Homework(String subject, String heading, String content, String time) {
        this.subject = subject;
        this.heading = heading;
        this.content = content;
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
