package iacaasystem.entity;

import java.util.Date;

public class QuestionnaireRecord {
    private int id;
    private String ipAdress;
    private GraduationRequirement req;
    private int grade;
    private Date year;

    public QuestionnaireRecord() {
    }

    public QuestionnaireRecord(int id, String ipAdress, GraduationRequirement req, int grade, Date year) {
        this.id = id;
        this.ipAdress = ipAdress;
        this.req = req;
        this.grade = grade;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public GraduationRequirement getReq() {
        return req;
    }

    public void setReq(GraduationRequirement req) {
        this.req = req;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "QuestionnaireRecord{" +
                "id=" + id +
                ", ipAdress='" + ipAdress + '\'' +
                ", req=" + req +
                ", grade=" + grade +
                ", year=" + year +
                '}';
    }
}
