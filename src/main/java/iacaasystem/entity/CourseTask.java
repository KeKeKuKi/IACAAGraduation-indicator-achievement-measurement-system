package iacaasystem.entity;

import java.util.Date;

public class CourseTask {
    int taskId;
    String taskDiscribe;
    Course TCourse;
    Target Ttarget;
    double targetMix;
    Date year;
    int ifComplete;

    public CourseTask() {
    }

    public CourseTask(int taskId, String taskDiscribe, Course TCourse, Target ttarget, double targetMix, Date year, int ifComplete) {
        this.taskId = taskId;
        this.taskDiscribe = taskDiscribe;
        this.TCourse = TCourse;
        Ttarget = ttarget;
        this.targetMix = targetMix;
        this.year = year;
        this.ifComplete = ifComplete;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskDiscribe() {
        return taskDiscribe;
    }

    public void setTaskDiscribe(String taskDiscribe) {
        this.taskDiscribe = taskDiscribe;
    }

    public Course getTCourse() {
        return TCourse;
    }

    public void setTCourse(Course TCourse) {
        this.TCourse = TCourse;
    }

    public Target getTtarget() {
        return Ttarget;
    }

    public void setTtarget(Target ttarget) {
        Ttarget = ttarget;
    }

    public double getTargetMix() {
        return targetMix;
    }

    public void setTargetMix(double targetMix) {
        this.targetMix = targetMix;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public int getIfComplete() {
        return ifComplete;
    }

    public void setIfComplete(int ifComplete) {
        this.ifComplete = ifComplete;
    }

    @Override
    public String toString() {
        return "CourseTask{" +
                "taskId=" + taskId +
                ", taskDiscribe='" + taskDiscribe + '\'' +
                ", TCourse=" + TCourse +
                ", Ttarget=" + Ttarget +
                ", targetMix=" + targetMix +
                ", year=" + year +
                ", ifComplete=" + ifComplete +
                '}';
    }
}
