package iacaasystem.entity;

public class GraduationRequirement {
    int reqId;
    String reqTitle;
    String reqDiscribe;

    public GraduationRequirement(int reqId, String reqTitle, String reqDiscribe) {
        this.reqId = reqId;
        this.reqTitle = reqTitle;
        this.reqDiscribe = reqDiscribe;
    }

    public GraduationRequirement() {
    }

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public String getReqTitle() {
        return reqTitle;
    }

    public void setReqTitle(String reqTitle) {
        this.reqTitle = reqTitle;
    }

    public String getReqDiscribe() {
        return reqDiscribe;
    }

    public void setReqDiscribe(String reqDiscribe) {
        this.reqDiscribe = reqDiscribe;
    }



    @Override
    public String toString() {
        return "GraduationRequirement{" +
                "reqId=" + reqId +
                ", reqTitle='" + reqTitle + '\'' +
                ", reqDiscribe='" + reqDiscribe + '\'' +
                '}';
    }
}
