package iacaasystem.entity;

public class Student {
    private int stuId;
    private String stuNum;
    private String stuName;
    private int stuSex;
    private String stuBirthday;
    private int stuAge;
    private String stuNativePlace;
    private int clazz;

    public Student(int stuId, String stuNum, String stuName, int stuSex, String stuBirthday, int stuAge, String stuNativePlace, int clazz) {
        this.stuId = stuId;
        this.stuNum = stuNum;
        this.stuName = stuName;
        this.stuSex = stuSex;
        this.stuBirthday = stuBirthday;
        this.stuAge = stuAge;
        this.stuNativePlace = stuNativePlace;
        this.clazz = clazz;
    }

    public Student() {
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public int getStuSex() {
        return stuSex;
    }

    public void setStuSex(int stuSex) {
        this.stuSex = stuSex;
    }

    public String getStuBirthday() {
        return stuBirthday;
    }

    public void setStuBirthday(String stuBirthday) {
        this.stuBirthday = stuBirthday;
    }

    public int getStuAge() {
        return stuAge;
    }

    public void setStuAge(int stuAge) {
        this.stuAge = stuAge;
    }

    public String getStuNativePlace() {
        return stuNativePlace;
    }

    public void setStuNativePlace(String stuNativePlace) {
        this.stuNativePlace = stuNativePlace;
    }

    public int getClazz() {
        return clazz;
    }

    public void setClazz(int clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", stuNum='" + stuNum + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuSex=" + stuSex +
                ", stuBirthday='" + stuBirthday + '\'' +
                ", stuAge=" + stuAge +
                ", stuNativePlace='" + stuNativePlace + '\'' +
                ", clazz=" + clazz +
                '}';
    }
}
