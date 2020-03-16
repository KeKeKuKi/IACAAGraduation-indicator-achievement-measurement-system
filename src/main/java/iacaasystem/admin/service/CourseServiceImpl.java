package iacaasystem.admin.service;

import iacaasystem.admin.dao.CourseDao;
import iacaasystem.entity.*;
import iacaasystem.utils.Arith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


@Service("courseservice")
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDao courseDao;
    static Calendar thisYearcal = Calendar.getInstance();

    @Override
    public List<Course> getAllCourse() {
        return courseDao.selectAllCourse();
    }

    @Override
    public boolean addDistributionCourse(int teacherid, int courseid) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String nowdate = dateFormat.format( new Date() );
        int change = courseDao.addDistributionCourse(teacherid,courseid,nowdate);
        if(change == 1) return true;
        else return false;
    }

    @Override
    public List<DistributionCourse> getAllDistributionCourse() {
        return courseDao.selectAllDistributionCourse();
    }

    @Override
    public Course getCourseById(int id) {
        return courseDao.selectCourseById(id);
    }

    @Override
    public List<CourseTargetMix> getAllCourseTargetMixByCourseId(int id) {
        return courseDao.selectCourseTargetMixByCourseId(id);
    }


    @Override
    public List<CourseTask> getThisYearTasksByCourseId(int courseId) {
        List<CourseTask> courseTasks= courseDao.selectCourseTaskByCourseId(courseId);
        return getThisYearCourseTaskFilter(courseTasks);
    }


    /**
     *  @author: ZhaoZezhong
     *  @advertisement: welcome to https://zhaozezhong.fun
     *  @Date: 2020/2/28 14:06
     *  @Description: 方法用于根据courseTask更新数据库并检查数据是否异常，有异常则进行数据回滚
     */
    @Override
    public synchronized boolean updateCourseTask(CourseTask courseTask) {
        CourseTask courseTaskSaveNow = courseDao.selectCourseTaskByTaskId(courseTask.getTaskId());
        int count =  courseDao.UpdateCourseTask(courseTask.getTaskId(),courseTask.getTaskDiscribe(),courseTask.getTargetMix());
        if(count==1){
            double totalMix = getTotalMixByCourseIdAndTargetId(courseTaskSaveNow.getTCourse().getCourseId(), courseTaskSaveNow.getTtarget().getTargetId());
            if(totalMix<=1 && totalMix>0){
                return true;
            }else {
                //数据异常，进行数据回滚
                courseDao.UpdateCourseTask(courseTask.getTaskId(),courseTaskSaveNow.getTaskDiscribe(),courseTaskSaveNow.getTargetMix());
                return false;
            }
        }else return false;
    }

    @Override
    public synchronized boolean deleteCourseTaskByTaskId(int id) {
        return courseDao.DeleteCourseTaskByTaskId(id)==1;
    }

    @Override
    public synchronized boolean addCourseTask(String dis, int courseId, int targetId, double mix) {
        double totalMix = getTotalMixByCourseIdAndTargetId(courseId,targetId);
        if(mix<=0) return false;
        if(totalMix+mix>1||totalMix+mix<=0) return false;

        return courseDao.addCourseTask(dis,courseId,targetId,mix,new Date())==1;
    }

    @Override
    public double getTotalMixByCourseIdAndTargetId(int courseId, int targetId) {
        List<CourseTask> courseTasks = courseDao.selectCourseTaskByCourseIdAndTargetId(courseId,targetId);
        List<CourseTask> thisYearCouraeTasks = getThisYearCourseTaskFilter(courseTasks);
        double totalMix =0;
        for(int i = 0,size = thisYearCouraeTasks.size();i<size;i++){
            totalMix = Arith.add(totalMix,thisYearCouraeTasks.get(i).getTargetMix());
            //为了确保计算精度，使用math.BigDecimal封装的计算类用于精确计算
        }
        return totalMix;
    }

    @Override
    public List<ExaminationLink> getThisYearExaminationLinksByCourseId(int courseId) {
        List<ExaminationLink> examinationLinks = new ArrayList<>();
        List<CourseTask> courseTasks = courseDao.selectCourseTaskByCourseId(courseId);
        List<CourseTask> thisYearcourseTasks = getThisYearCourseTaskFilter(courseTasks);

        if(thisYearcourseTasks.size()>0){
            for(int i=0,size = thisYearcourseTasks.size();i<size;i++){
                List<ExaminationLink> els = courseDao.selectExaminationLinksByCourseTaskId(thisYearcourseTasks.get(i).getTaskId());
                if(els != null){
                    for (int j = 0,elsize = els.size();j<elsize;j++){
                        examinationLinks.add(els.get(j));
                    }
                }
            }
        }
        return examinationLinks;
    }

    public double getTotalElLinksMixByCourseTaskId(int taskid){
        List<ExaminationLink> examinationLinks = courseDao.selectExaminationLinksByCourseTaskId(taskid);
        double totalMix = 0;
        if (examinationLinks!=null){
            for (int i=0,size = examinationLinks.size();i<size;i++){
                totalMix = Arith.add(totalMix,examinationLinks.get(i).getElMix());
            }
        }
        return totalMix;
    }

    @Override
    public boolean updateExaminationLink(ExaminationLink examinationLink) {
        ExaminationLink examinationLinksave = courseDao.selectExaminationLinkById(examinationLink.getElId());
        int count = courseDao.updateExaminationLink(examinationLink.getElId(),examinationLink.getElName(),examinationLink.getElTargetScore(),examinationLink.getElMix());
        if(count==1){
            double totalelmix = getTotalElLinksMixByCourseTaskId(examinationLink.getCourseTask().getTaskId());
            if(totalelmix>1||totalelmix<=0){
                courseDao.updateExaminationLink(examinationLink.getElId(),examinationLinksave.getElName(),examinationLinksave.getElTargetScore(),examinationLinksave.getElMix());
                return false;
            }
            return true;
        }else {
            courseDao.updateExaminationLink(examinationLink.getElId(),examinationLinksave.getElName(),examinationLinksave.getElTargetScore(),examinationLinksave.getElMix());
            return false;
        }
    }

    @Override
    public boolean deleteExaminationLinkByElId(int id) {
        if(courseDao.deleteExaminationLinkByElId(id)==1) return true;
        return false;
    }

    @Override
    public synchronized boolean addExaminationLink(ExaminationLink examinationLink) {
        double totalelmix = getTotalElLinksMixByCourseTaskId(examinationLink.getCourseTask().getTaskId());
        if(examinationLink.getElMix()<=0){
            return false;
        }
        if(totalelmix+examinationLink.getElMix()>1||totalelmix+examinationLink.getElMix()<=0){
            return false;
        }
        return courseDao.addExaminationLink(examinationLink.getCourseTask().getTaskId(),examinationLink.getElName(),
                examinationLink.getElTargetScore(),0,examinationLink.getElMix())==1;

    }

    double getTotalCourseTaskMix(List<CourseTask> courseTasks){
        double courseTaskMix = 0;
        for (int i=0,size = courseTasks.size();i<size;i++){
            courseTaskMix = Arith.add(courseTaskMix,courseTasks.get(i).getTargetMix());
        }
        return  courseTaskMix;
    }

    List<CourseTask> getThisYearCourseTaskFilter(List<CourseTask> courseTasks){
        List<CourseTask> courseTasks1 = new ArrayList<>();
        if(courseTasks.size()>0){
            thisYearcal.setTime(new Date());
            Calendar cl = Calendar.getInstance();
            for (int i=0,size = courseTasks.size();i<size;i++){
                cl.setTime(courseTasks.get(i).getYear());
                if(thisYearcal.get(Calendar.YEAR)==cl.get(Calendar.YEAR)){
                    courseTasks1.add(courseTasks.get(i));
                }
            }
        }
        return courseTasks1;
    }

    @Override
    public boolean ifCourseEditFinshed(int courseid) {
        List<CourseTargetMix> courseTargetMixes = courseDao.selectCourseTargetMixByCourseId(courseid);
        List<CourseTask> ct;
        List<CourseTask> courseTasks;
        for (int j=0,tsize = courseTargetMixes.size();j<tsize;j++){
            ct = courseDao.selectCourseTaskByCourseIdAndTargetId(courseid,courseTargetMixes.get(j).getTarget().getTargetId());
            courseTasks = getThisYearCourseTaskFilter(ct);
            if(courseTasks!=null&&getTotalCourseTaskMix(courseTasks)>=1.0){
                for (int i=0,size = courseTasks.size();i<size;i++){
                    double totalElmix = getTotalElLinksMixByCourseTaskId(courseTasks.get(i).getTaskId());
                    if(totalElmix>=1.0) continue;
                    else return false;
                }
            }else return false;

        }
        return true;
    }

    @Override
    public synchronized boolean updateElinkAvgScore(ExaminationLink examinationLink) {
        double mix = examinationLink.getElAverageScore()/examinationLink.getElTargetScore();
        if (mix>1||mix<0) return false;
        return courseDao.updateExaminationLinkAvgScore(examinationLink.getElId(),examinationLink.getElAverageScore())==1;

    }

    @Override
    public ExaminationLink getExaminationLinkByElId(int id) {
        return courseDao.selectExaminationLinkById(id);
    }





    /**
     *  @author: ZhaoZezhong
     *  @advertisement: welcome to http://zhaozezhong.fun
     *  @Date: 2020/3/4 11:54
     *  @Description:用于计算某年的单个指标点达成度统计
     */
    @Override
    public double getTargetScoreByTargetAndYear(Target target,Calendar cal) {
        Calendar ca2 = Calendar.getInstance();

        List<CourseTargetMix> courseTargetMixes = courseDao.selectCourseTargetMixByTargetId(target.getTargetId());
        double totalTargetScore = 0;
        int courseTargetMixesSize = courseTargetMixes.size();
        if(0<courseTargetMixesSize){
            List<CourseTask> courseTasks;
            List<ExaminationLink> examinationLinks;
            for (int i=0;i<courseTargetMixesSize;i++){
                courseTasks= courseDao.selectCourseTaskByCourseIdAndTargetId(courseTargetMixes.get(i).getCourse().getCourseId(),target.getTargetId());
                double totalCourseTaskScore = 0;
                int courseTaskSize = courseTasks.size();
                if(0<courseTaskSize){
                    for (int j=0;j<courseTaskSize;j++){
                        ca2.setTime(courseTasks.get(j).getYear());
                        if(ca2.get(Calendar.YEAR)==cal.get(Calendar.YEAR)){
                            examinationLinks = courseDao.selectExaminationLinksByCourseTaskId(courseTasks.get(j).getTaskId());
                            double totalElinkScore =0;
                            int examinationLinksSize = examinationLinks.size();
                            if(0<examinationLinksSize){
                                for (int k=0;k<examinationLinksSize;k++){
                                    totalElinkScore = Arith.add(totalElinkScore,examinationLinks.get(k).getElMix()*(examinationLinks.get(k).getElAverageScore()/examinationLinks.get(k).getElTargetScore()));
                                }
                            }
                            totalCourseTaskScore = Arith.add(totalCourseTaskScore,totalElinkScore*courseTasks.get(j).getTargetMix());
                        }
                    }
                }
                totalTargetScore = Arith.add(totalTargetScore,totalCourseTaskScore*courseTargetMixes.get(i).getCtmix());
            }
        }
        return totalTargetScore;
    }

    @Override
    public double getGraduationReqScoreByGraduationReqAndYear(GraduationRequirement graduationRequirement,Calendar cal) {
        List<Target> targets = courseDao.selectTargetByReqId(graduationRequirement.getReqId());
        double reScore = 0;
        int targetsSize = targets.size();
        if(targetsSize>0){
            for (int i=0;i<targetsSize;i++){
                reScore = Arith.add(reScore,getTargetScoreByTargetAndYear(targets.get(i),cal));
            }
            return reScore/(double)targetsSize;
        }
        return 0;

    }

    @Override
    public double[] getAllGraduationReqScoreByYear(int year) {
        List<GraduationRequirement> graduationRequirements = courseDao.selectGraduationRequirements();
        int totalReqCount = graduationRequirements.size();
        double allReqScores[] = new double[totalReqCount];

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,1,1);
        for (int i=0;i<totalReqCount;i++){
            allReqScores[i] = (double) Math.round(getGraduationReqScoreByGraduationReqAndYear(graduationRequirements.get(i),calendar) * 100) / 100;
        }
        return allReqScores;
    }

    @Override
    public List<GraduationRequirement> getAllGraduationRequirements() {
        return courseDao.selectGraduationRequirements();
    }

    @Override
    public synchronized boolean setAllThisYearReqScore() {
        try{
            List <GraduationRequirement> graduationRequirements = courseDao.selectGraduationRequirements();
            int graduationRequirementsSize = graduationRequirements.size();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            for (int i=0;i<graduationRequirementsSize;i++){
                courseDao.addReqScore(new Date(),graduationRequirements.get(i).getReqId(),getGraduationReqScoreByGraduationReqAndYear(graduationRequirements.get(i),calendar));
            }
            return true;
        }catch (Exception e){
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.error("##在添加当年所有毕业要求成绩时发生异常。##"+e.toString());
            return false;
        }


    }


}
