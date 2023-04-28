package com.rathaur.gpm.DataBaseModal;

public class Attendance {
    String studentId;
    String subject;
    boolean isPresent;
    String date;

    public Attendance(String studentId, String subject, boolean isPresent, String time) {
        this.studentId = studentId;
        this.subject = subject;
        this.isPresent = isPresent;
        this.date = time;
    }

    public Attendance() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
