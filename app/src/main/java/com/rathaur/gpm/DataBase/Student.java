package com.rathaur.gpm.DataBase;

import java.util.ArrayList;

public class Student {
    String sname;
    String smobile;
    String semail;
    String sgender;
    String sprofession;
    String senrollment;
    String spassword;
    String syear;
    String simage;

    public String getScomplaints() {
        return scomplaints;
    }

    public void setScomplaints(String scomplaints) {
        this.scomplaints = scomplaints;
    }

    String scomplaints;

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    String sdate;

    public Student() {
    }
    public Student(String sname, String smobile, String senrollment, String syear, String scomplaints, String sdate)
    {
        this.sname = sname;
        this.smobile = smobile;
        this.senrollment = senrollment;
        this.syear = syear;
        this.scomplaints=scomplaints;
        this.sdate=sdate;
    }
    public Student(String simage) {
        this.simage = simage;
    }

    public String getSimage() {
        return simage;
    }

    public void setSimage(String simage) {
        this.simage = simage;
    }

    public Student(String sname, String smobile, String semail, String sgender, String sprofession, String senrollment, String spassword, String syear) {
        this.sname = sname;
        this.smobile = smobile;
        this.semail = semail;
        this.sgender = sgender;
        this.sprofession = sprofession;
        this.senrollment = senrollment;
        this.spassword = spassword;
        this.syear = syear;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSmobile() {
        return smobile;
    }

    public void setSmobile(String smobile) {
        this.smobile = smobile;
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public String getSgender() {
        return sgender;
    }

    public void setSgender(String sgender) {
        this.sgender = sgender;
    }

    public String getSprofession() {
        return sprofession;
    }

    public void setSprofession(String sprofession) {
        this.sprofession = sprofession;
    }

    public String getSenrollment() {
        return senrollment;
    }

    public void setSenrollment(String senrollment) {
        this.senrollment = senrollment;
    }

    public String getSpassword() {
        return spassword;
    }

    public void setSpassword(String spassword) {
        this.spassword = spassword;
    }

    public String getSyear() {
        return syear;
    }

    public void setSyear(String syear) {
        this.syear = syear;
    }
}
