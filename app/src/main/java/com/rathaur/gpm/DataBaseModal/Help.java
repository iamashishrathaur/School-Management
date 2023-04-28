package com.rathaur.gpm.DataBaseModal;

public class Help {
    String name;
    String enrollment;
    String problem;

    public Help(String name, String enrollment, String problem) {
        this.name = name;
        this.enrollment = enrollment;
        this.problem = problem;
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

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Help() {
    }
}
