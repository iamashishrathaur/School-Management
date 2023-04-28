package com.rathaur.gpm.DataBaseModal;

public class Video {
    String uenrollment;
    String vtitle;
    String vtime;
    String vurl;

    public Video(String uenrollment, String vtitle, String vtime, String vurl) {
        this.uenrollment = uenrollment;
        this.vtitle = vtitle;
        this.vtime = vtime;
        this.vurl = vurl;
    }

    public Video() {
    }

    public String getUenrollment() {
        return uenrollment;
    }

    public void setUenrollment(String uenrollment) {
        this.uenrollment = uenrollment;
    }

    public String getVtitle() {
        return vtitle;
    }

    public void setVtitle(String vtitle) {
        this.vtitle = vtitle;
    }

    public String getVtime() {
        return vtime;
    }

    public void setVtime(String vtime) {
        this.vtime = vtime;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }
}
