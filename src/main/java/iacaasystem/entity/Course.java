package iacaasystem.entity;

public class Course {
    int courseId;
    String courseName;
    String courseImageUrl;

    public Course() {
    }

    public Course(int courseId, String courseName, String courseImageUrl) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseImageUrl = courseImageUrl;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseImageUrl() {
        return courseImageUrl;
    }

    public void setCourseImageUrl(String courseImageUrl) {
        this.courseImageUrl = courseImageUrl;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseImageUrl='" + courseImageUrl + '\'' +
                '}';
    }
}
