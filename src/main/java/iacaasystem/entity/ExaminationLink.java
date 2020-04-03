package iacaasystem.entity;

import java.util.Date;

public class ExaminationLink {
    private int elId;
    private CourseTask courseTask;
    private String elName;
    private double elTargetScore;
    private double elAverageScore;
    private double elMix;

    public ExaminationLink() {
    }

    public ExaminationLink(int elId, CourseTask courseTask, String elName, double elTargetScore, double elAverageScore, double elMix) {
        this.elId = elId;
        this.courseTask = courseTask;
        this.elName = elName;
        this.elTargetScore = elTargetScore;
        this.elAverageScore = elAverageScore;
        this.elMix = elMix;
    }

    public int getElId() {
        return elId;
    }

    public void setElId(int elId) {
        this.elId = elId;
    }

    public CourseTask getCourseTask() {
        return courseTask;
    }

    public void setCourseTask(CourseTask courseTask) {
        this.courseTask = courseTask;
    }

    public String getElName() {
        return elName;
    }

    public void setElName(String elName) {
        this.elName = elName;
    }

    public double getElTargetScore() {
        return elTargetScore;
    }

    public void setElTargetScore(double elTargetScore) {
        this.elTargetScore = elTargetScore;
    }

    public double getElAverageScore() {
        return elAverageScore;
    }

    public void setElAverageScore(double elAverageScore) {
        this.elAverageScore = elAverageScore;
    }


    public double getElMix() {
        return elMix;
    }

    public void setElMix(double elMix) {
        this.elMix = elMix;
    }

    @Override
    public String toString() {
        return "ExaminationLink{" +
                "elId=" + elId +
                ", courseTask=" + courseTask +
                ", elName='" + elName + '\'' +
                ", elTargetScore=" + elTargetScore +
                ", elAverageScore=" + elAverageScore +
                ", elMix=" + elMix +
                '}';
    }
}
