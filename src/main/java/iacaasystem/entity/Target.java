package iacaasystem.entity;

public class Target {
    int targetId;
    String targetDiscribe;
    GraduationRequirement graduationRequirement;

    public Target() {
    }

    public Target(int targetId, String targetDiscribe, GraduationRequirement graduationRequirement) {
        this.targetId = targetId;
        this.targetDiscribe = targetDiscribe;
        this.graduationRequirement = graduationRequirement;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getTargetDiscribe() {
        return targetDiscribe;
    }

    public void setTargetDiscribe(String targetDiscribe) {
        this.targetDiscribe = targetDiscribe;
    }

    public GraduationRequirement getGraduationRequirement() {
        return graduationRequirement;
    }

    public void setGraduationRequirement(GraduationRequirement graduationRequirement) {
        this.graduationRequirement = graduationRequirement;
    }

    @Override
    public String toString() {
        return "Target{" +
                "targetId=" + targetId +
                ", targetDiscribe='" + targetDiscribe + '\'' +
                ", graduationRequirement=" + graduationRequirement +
                '}';
    }
}
