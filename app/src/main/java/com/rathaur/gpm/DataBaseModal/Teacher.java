package com.rathaur.gpm.DataBaseModal;

public class Teacher {
    String tname;
    String tmobile;
    String temail;
    String tgender;
    String tprofession;
    String tenrollment;
    String tpassword;
    String tcomplaints;
    String tdate;

    public Teacher(String tname, String tmobile, String tenrollment, String tcomplaints, String tdate) {
        this.tname = tname;
        this.tmobile = tmobile;
        this.tenrollment = tenrollment;
        this.tcomplaints = tcomplaints;
        this.tdate = tdate;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public String getTcomplaints() {
        return tcomplaints;
    }

    public void setTcomplaints(String tcomplaints) {
        this.tcomplaints = tcomplaints;
    }

    public Teacher(String timage) {
        this.timage = timage;
    }

    public String getTimage() {
        return timage;
    }

    public void setTimage(String timage) {
        this.timage = timage;
    }

    public Teacher() {
    }

    String timage;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTmobile() {
        return tmobile;
    }

    public void setTmobile(String tmobile) {
        this.tmobile = tmobile;
    }

    public String getTemail() {
        return temail;
    }

    public void setTemail(String temail) {
        this.temail = temail;
    }

    public String getTgender() {
        return tgender;
    }

    public void setTgender(String tgender) {
        this.tgender = tgender;
    }

    public String getTprofession() {
        return tprofession;
    }

    public void setTprofession(String tprofession) {
        this.tprofession = tprofession;
    }

    public String getTenrollment() {
        return tenrollment;
    }

    public void setTenrollment(String tenrollment) {
        this.tenrollment = tenrollment;
    }

    public String getTpassword() {
        return tpassword;
    }

    public void setTpassword(String tpassword) {
        this.tpassword = tpassword;
    }

    public Teacher(String tname, String tmobile, String temail, String tgender, String tprofession, String tenrollment, String tpassword) {
        this.tname = tname;
        this.tmobile = tmobile;
        this.temail = temail;
        this.tgender = tgender;
        this.tprofession = tprofession;
        this.tenrollment = tenrollment;
        this.tpassword = tpassword;

    }
}
