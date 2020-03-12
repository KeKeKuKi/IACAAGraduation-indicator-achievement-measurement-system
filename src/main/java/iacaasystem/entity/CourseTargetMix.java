package iacaasystem.entity;

public class CourseTargetMix {
    int ctmixId;
    Course course;
    Target target;
    double ctmix;

    public CourseTargetMix(int ctmixId, Course course, Target target, double ctmix) {
        this.ctmixId = ctmixId;
        this.course = course;
        this.target = target;
        this.ctmix = ctmix;
    }

    public CourseTargetMix() {
    }

    public int getCtmixId() {
        return ctmixId;
    }

    public void setCtmixId(int ctmixId) {
        this.ctmixId = ctmixId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public double getCtmix() {
        return ctmix;
    }

    public void setCtmix(double ctmix) {
        this.ctmix = ctmix;
    }

    @Override
    public String toString() {
        return "CourseTargetMix{" +
                "ctmixId=" + ctmixId +
                ", course=" + course +
                ", target=" + target +
                ", ctmix=" + ctmix +
                '}';
    }
}
