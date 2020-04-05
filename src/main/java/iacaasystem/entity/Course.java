package iacaasystem.entity;

public class Course {
    private int courseId;
    private String courseName;
    private String courseImageUrl;
    private Teacher edit_teacher;

    public Course() {
    }

    public Course(int courseId, String courseName, String courseImageUrl, Teacher edit_teacher) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseImageUrl = courseImageUrl;
        this.edit_teacher = edit_teacher;
    }

    public Course(int courseId, String courseName, String courseImageUrl) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseImageUrl = courseImageUrl;
    }

    public Teacher getEdit_teacher() {
        return edit_teacher;
    }

    public void setEdit_teacher(Teacher edit_teacher) {
        this.edit_teacher = edit_teacher;
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
                ", edit_teacher=" + edit_teacher +
                '}';
    }
}
