package com.rathaur.gpm.DataBaseModal;

public class AllotSubjectModal {
    String name,enrollment,subject,image;

    public AllotSubjectModal(String name, String enrollment, String subject, String image) {
        this.name = name;
        this.enrollment = enrollment;
        this.subject = subject;
        this.image = image;
    }

    public AllotSubjectModal(String name, String enrollment, String subject) {
        this.name = name;
        this.enrollment = enrollment;
        this.subject = subject;
    }

    public AllotSubjectModal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
