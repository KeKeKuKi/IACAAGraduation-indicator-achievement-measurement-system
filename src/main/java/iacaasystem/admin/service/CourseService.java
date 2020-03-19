package iacaasystem.admin.service;

import iacaasystem.entity.*;

import java.util.Calendar;
import java.util.List;


public interface CourseService {
    List<Course> getAllCourse();
    boolean addDistributionCourse(int teacherid, int courseid);
    List<DistributionCourse> getAllDistributionCourse();
    Course getCourseById(int id);
    List<CourseTargetMix> getAllCourseTargetMixByCourseId(int id);
    List<CourseTask> getThisYearTasksByCourseId(int courseId);
    List<Target> getAllTargets();
    List<Target> getAllTargetsByReqId(int id);
    boolean updateCourseTask(CourseTask courseTask);
    boolean deleteCourseTaskByTaskId(int id);
    boolean addCourseTask(String dis,int courseId,int targetId,double mix);
    double getTotalMixByCourseIdAndTargetId(int courseId,int targetId);
    List<ExaminationLink> getThisYearExaminationLinksByCourseId(int courseId);
    boolean updateExaminationLink(ExaminationLink examinationLink);
    boolean deleteExaminationLinkByElId(int id);
    boolean addExaminationLink(ExaminationLink examinationLink);
    boolean ifCourseEditFinshed(int courseid);
    boolean updateElinkAvgScore(ExaminationLink examinationLink);
    ExaminationLink getExaminationLinkByElId(int id);
    double getTargetScoreByTargetAndYear(Target target, Calendar cal);
    double getGraduationReqScoreByGraduationReqIdAndYear(int graduationRequirementId, Calendar cal);
    double [] getAllTargetScoreByReqIdAndYear(int reqid,int year);
    double [] getAllGraduationReqScoreByYear(int year);
    double getAvgCourseTaskScoreByCoursIdAndTargetIdAndYear(int targetId,int courseId,int year);
    List<GraduationRequirement> getAllGraduationRequirements();
    boolean setAllThisYearReqScore();
    List<CourseTargetMix> getAllCourseTargetMixByTargetId(int id);

}
