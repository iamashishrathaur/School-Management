package com.rathaur.gpm.DataBaseModal;

public class PDF {
    String pdfName;
    String time;
    String date;
    String enrollment;
    String uri;

    public PDF(String pdfName, String time, String date, String enrollment, String uri) {
        this.pdfName = pdfName;
        this.time = time;
        this.date = date;
        this.enrollment = enrollment;
        this.uri = uri;
    }

    public PDF() {
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
