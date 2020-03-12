package iacaasystem.entity;

public class Teacher {
    int teacherId;
    String passWord;
    String teacherNumber;
    String teacherName;
    int teacherSex;
    int teacherAge;
    String teacherPhonenumber;
    int onlineState;
    int editState;

    public Teacher(int teacherId, String passWord, String teacherNumber, String teacherName, int teacherSex, int teacherAge, String teacherPhonenumber, int onlineState, int editState) {
        this.teacherId = teacherId;
        this.passWord = passWord;
        this.teacherNumber = teacherNumber;
        this.teacherName = teacherName;
        this.teacherSex = teacherSex;
        this.teacherAge = teacherAge;
        this.teacherPhonenumber = teacherPhonenumber;
        this.onlineState = onlineState;
        this.editState = editState;
    }

    public Teacher() {
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTeacherSex() {
        return teacherSex;
    }

    public void setTeacherSex(int teacherSex) {
        this.teacherSex = teacherSex;
    }

    public int getTeacherAge() {
        return teacherAge;
    }

    public void setTeacherAge(int teacherAge) {
        this.teacherAge = teacherAge;
    }

    public String getTeacherPhonenumber() {
        return teacherPhonenumber;
    }

    public void setTeacherPhonenumber(String teacherPhonenumber) {
        this.teacherPhonenumber = teacherPhonenumber;
    }

    public int getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(int onlineState) {
        this.onlineState = onlineState;
    }

    public int getEditState() {
        return editState;
    }

    public void setEditState(int editState) {
        this.editState = editState;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", passWord='" + passWord + '\'' +
                ", teacherNumber='" + teacherNumber + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", teacherSex=" + teacherSex +
                ", teacherAge=" + teacherAge +
                ", teacherPhonenumber='" + teacherPhonenumber + '\'' +
                ", onlineState=" + onlineState +
                ", editState=" + editState +
                '}';
    }
}
