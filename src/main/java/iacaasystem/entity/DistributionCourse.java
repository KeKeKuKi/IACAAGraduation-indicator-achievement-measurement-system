package iacaasystem.entity;

public class DistributionCourse {
    int distributionId;
    Course districourse;
    Teacher distriteacher;
    String distrTime;

    public DistributionCourse() {
    }

    public DistributionCourse(int distributionId, Course districourse, Teacher distriteacher, String distrTime) {
        this.distributionId = distributionId;
        this.districourse = districourse;
        this.distriteacher = distriteacher;
        this.distrTime = distrTime;
    }

    public int getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(int distributionId) {
        this.distributionId = distributionId;
    }

    public Course getDistricourse() {
        return districourse;
    }

    public void setDistricourse(Course districourse) {
        this.districourse = districourse;
    }

    public Teacher getDistriteacher() {
        return distriteacher;
    }

    public void setDistriteacher(Teacher distriteacher) {
        this.distriteacher = distriteacher;
    }

    public String getDistrTime() {
        return distrTime;
    }

    public void setDistrTime(String distrTime) {
        this.distrTime = distrTime;
    }

    @Override
    public String toString() {
        return "DistributionCourse{" +
                "distributionId=" + distributionId +
                ", districourse=" + districourse.toString() +
                ", distriteacher=" + distriteacher.toString() +
                ", distrTime=" + distrTime +
                '}';
    }
}
