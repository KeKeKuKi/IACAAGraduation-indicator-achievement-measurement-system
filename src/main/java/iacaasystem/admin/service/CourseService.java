package iacaasystem.admin.service;

import iacaasystem.entity.*;

import java.util.Calendar;
import java.util.List;


public interface CourseService {

    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/3/1
     *  @Description:返回所有课程列表
     *  @parameters:
     */
    List<Course> getAllCourse();



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/3/1
     *  @Description:添加课程编辑任务
     *  @parameters: teacherid:教师账户Id,courseid:课程Id
     */
    boolean addDistributionCourse(int teacherid, int courseid);



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/3/1
     *  @Description:返回所有分配任务列表
     *  @parameters:
     */
    List<DistributionCourse> getAllDistributionCourse();



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/3/1
     *  @Description:根据课程Id返回课程
     *  @parameters: id:课程Id
     */
    Course getCourseById(int id);



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/3/1
     *  @Description:根据课程Id返回该课程所有支撑的指标点以及权重
     *  @parameters: id：课程Id
     */
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
