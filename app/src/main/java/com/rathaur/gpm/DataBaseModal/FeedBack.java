package com.rathaur.gpm.DataBaseModal;

public class FeedBack {
    String name;
    String enrollment;
    String feedback;
    String mood;

    public FeedBack(String name, String enrollment, String feedback, String mood) {
        this.name = name;
        this.enrollment = enrollment;
        this.feedback = feedback;
        this.mood = mood;
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public FeedBack() {
    }
}
