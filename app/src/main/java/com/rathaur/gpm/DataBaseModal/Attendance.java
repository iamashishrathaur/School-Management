package com.rathaur.gpm.DataBaseModal;

public class Attendance {
    String enrollment;
    String subject;
    boolean isPresent;
    String date;

    public Attendance(String enrollment, String subject, boolean isPresent, String date) {
        this.enrollment = enrollment;
        this.subject = subject;
        this.isPresent = isPresent;
        this.date = date;
    }

    public Attendance() {
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

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
