package com.rathaur.gpm.DataBase;

public class Applications {
    String name;
    String mobile;
    String enrollment;
    String subject;
    String reason;
    String time;

    public Applications(String name, String mobile, String enrollment, String subject, String reason, String time) {
        this.name = name;
        this.mobile = mobile;
        this.enrollment = enrollment;
        this.subject = subject;
        this.reason = reason;
        this.time = time;
    }

    public Applications() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
