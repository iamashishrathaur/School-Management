package com.rathaur.gpm.DataBaseModal;

public class School {

      String about;
      String open;
      String close;
      String name1;
      String mobile1;
      String email1;
      String name2;
      String mobile2;
      String email2;
      String uri;


    public School() {

    }

    public School(String about, String open, String close, String name1, String mobile1, String email1, String name2, String mobile2, String email2, String uri) {
        this.about = about;
        this.open = open;
        this.close = close;
        this.name1 = name1;
        this.mobile1 = mobile1;
        this.email1 = email1;
        this.name2 = name2;
        this.mobile2 = mobile2;
        this.email2 = email2;
        this.uri = uri;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
