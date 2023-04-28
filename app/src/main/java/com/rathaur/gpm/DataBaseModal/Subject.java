package com.rathaur.gpm.DataBaseModal;

public class Subject {
    String  sem;
    String Subject;
    String type;
    String Teacher;

    public Subject(String sem, String subject, String type, String teacher) {
        this.sem = sem;
        Subject = subject;
        this.type = type;
        Teacher = teacher;
    }

    public Subject() {
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }
}
