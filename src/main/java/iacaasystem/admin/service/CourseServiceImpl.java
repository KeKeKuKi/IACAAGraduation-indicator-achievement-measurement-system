package iacaasystem.admin.service;

import iacaasystem.admin.dao.CourseDao;
import iacaasystem.entity.*;
import iacaasystem.utils.Arith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service("courseservice")
public class CourseServiceImpl implements CourseService {
    @Autowired
    AdminService adminService;

    @Autowired
    CourseDao courseDao;

    private static Calendar thisYearcal = Calendar.getInstance();

    @Override
    public List<Course> getAllCourse() {
        return courseDao.selectAllCourse();
    }

    @Override
    public boolean addDistributionCourse(int teacherid, int courseid) {
        int change = courseDao.addDistributionCourse(teacherid,courseid);
        return change==1;
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

    @Override
    public List<Target> getAllTargets() {
        return courseDao.selectAllTargets();
    }

    @Override
    public List<Target> getAllTargetsByReqId(int id) {
        return courseDao.selectTargetByReqId(id);
    }

    @Override
    public synchronized boolean updateCourseTask(CourseTask courseTask) {
        CourseTask courseTaskSaveNow = courseDao.selectCourseTaskByTaskId(courseTask.getTaskId());
        int count =  courseDao.UpdateCourseTask(courseTask.getTaskId(),courseTask.getTaskDiscribe(),courseTask.getTargetMix());
        if(count==1){
            double totalMix = getTotalMixByCourseIdAndTargetId(courseTaskSaveNow.getTCourse().getCourseId(),
                    courseTaskSaveNow.getTtarget().getTargetId());
            if(totalMix==1){
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
        thisYearcal.set(adminService.getSystemDateYear(),Calendar.FEBRUARY,1);
        return courseDao.addCourseTask(dis,courseId,targetId,mix,thisYearcal.getTime())==1;
    }

    @Override
    public double getTotalMixByCourseIdAndTargetId(int courseId, int targetId) {
        List<CourseTask> courseTasks = courseDao.selectCourseTaskByCourseIdAndTargetId(courseId,targetId);
        List<CourseTask> thisYearCouraeTasks = getThisYearCourseTaskFilter(courseTasks);
        double totalMix =0;
        for(CourseTask i : thisYearCouraeTasks){
            totalMix = Arith.add(totalMix,i.getTargetMix());
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
            for(CourseTask i : thisYearcourseTasks){
                List<ExaminationLink> els = courseDao.selectExaminationLinksByCourseTaskId(i.getTaskId());
                if(els != null){
                    examinationLinks.addAll(els);
                }
            }
        }
        return examinationLinks;
    }

    @Override
    public boolean updateExaminationLink(ExaminationLink examinationLink) {
        ExaminationLink examinationLinksave = courseDao.selectExaminationLinkById(examinationLink.getElId());
        int count = courseDao.updateExaminationLink(examinationLink.getElId(),examinationLink.getElName(),
                examinationLink.getElTargetScore(),examinationLink.getElMix());
        if(count==1){
            double totalelmix = getTotalElLinksMixByCourseTaskId(examinationLink.getCourseTask().getTaskId());
            if(totalelmix>1||totalelmix<=0){
                courseDao.updateExaminationLink(examinationLink.getElId(),examinationLinksave.getElName(),
                        examinationLinksave.getElTargetScore(),examinationLinksave.getElMix());
                return false;
            }
            return true;
        }else {
            courseDao.updateExaminationLink(examinationLink.getElId(),examinationLinksave.getElName(),
                    examinationLinksave.getElTargetScore(),examinationLinksave.getElMix());
            return false;
        }
    }

    @Override
    public boolean deleteExaminationLinkByElId(int id) {
        return courseDao.deleteExaminationLinkByElId(id)==1;
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

    @Override
    public boolean ifCourseEditFinshed(int courseid) {
        List<CourseTargetMix> courseTargetMixes = courseDao.selectCourseTargetMixByCourseId(courseid);
        List<CourseTask> ct;
        List<CourseTask> courseTasks;
        for (CourseTargetMix c : courseTargetMixes){
            ct = courseDao.selectCourseTaskByCourseIdAndTargetId(courseid,c.getTarget().getTargetId());
            courseTasks = getThisYearCourseTaskFilter(ct);
            if(courseTasks!=null&&getTotalCourseTaskMix(courseTasks)>=1.0){
                for (CourseTask i : courseTasks){
                    double totalElmix = getTotalElLinksMixByCourseTaskId(i.getTaskId());
                    if(totalElmix<1.0) return false;
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

    @Override
    public double getTargetScoreByTargetAndYear(Target target,Calendar cal) {
        List<CourseTargetMix> courseTargetMixes = courseDao.selectCourseTargetMixByTargetId(target.getTargetId());
        double totalTargetScore = 0;
        for (CourseTargetMix i:courseTargetMixes){
            totalTargetScore = Arith.add(totalTargetScore,getAvgCourseTaskScoreByCoursIdAndTargetIdAndYear(
                    i.getTarget().getTargetId(), i.getCourse().getCourseId(),cal.get(Calendar.YEAR))*i.getCtmix());
        }
        return totalTargetScore;
    }

    @Override
    public double getGraduationReqScoreByGraduationReqIdAndYear(int reqid,Calendar cal) {
        List<Target> targets = courseDao.selectTargetByReqId(reqid);
        double reScore = 0;
        int targetsSize = targets.size();
        if(targetsSize>0){
            for (Target i:targets){
                reScore = Arith.add(reScore,getTargetScoreByTargetAndYear(i,cal));
            }
            return reScore/(double)targetsSize;
        }
        return 0;
    }

    @Override
    public double[] getAllGraduationReqScoreByYear(int year) {
        List<GraduationRequirement> graduationRequirements = courseDao.selectGraduationRequirements();
        int totalReqCount = graduationRequirements.size();
        double [] allReqScores = new double[totalReqCount];

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,Calendar.FEBRUARY,1);
        for (int i=0;i<totalReqCount;i++){
            Double score = courseDao.selectReqScoreByYearAndRegId(year,graduationRequirements.get(i).getReqId());
            if(score!=null){
                allReqScores[i] = (double) Math.round(score * 100) / 100;
            }else {
                allReqScores[i] = 0;
            }

        }
        return allReqScores;
    }

    @Override
    public double getAvgCourseTaskScoreByCoursIdAndTargetIdAndYear(int targetId,int courseId, int year) {
        List<CourseTask> courseTasks = courseDao.selectCourseTaskByCourseIdAndTargetId(courseId,targetId);
        List<CourseTask> courseTasks2 = new ArrayList<>();
        if(courseTasks.size()>0){
            for(CourseTask i:courseTasks){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(i.getYear());
                if(calendar.get(Calendar.YEAR)!=year) continue;
                courseTasks2.add(i);
            }
        }
        double score =0;
        if(courseTasks2.size()>0){
            for(CourseTask i:courseTasks2){
                score = Arith.add(score,getAvgExlinkScoreByCourseTaskId(i.getTaskId())*i.getTargetMix());
            }
            return score;
        }else return 0;
    }

    @Override
    public double[] getAllTargetScoreByReqIdAndYear(int reqid,int year) {
        Calendar years = Calendar.getInstance();
        years.set(year,Calendar.FEBRUARY,1);
        List<Target> targets = courseDao.selectTargetByReqId(reqid);
        double [] alltargetScores = new double[targets.size()];

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,Calendar.FEBRUARY,1);
        for (int i=0,size = targets.size();i<size;i++){
            alltargetScores[i] = (double) Math.round(getTargetScoreByTargetAndYear(targets.get(i),years) * 100) / 100;
        }
        return alltargetScores;
    }

    @Override
    public List<GraduationRequirement> getAllGraduationRequirements() {
        return courseDao.selectGraduationRequirements();
    }

    @Override
    public boolean setAllThisYearReqScore() {
        try{
            List <GraduationRequirement> graduationRequirements = courseDao.selectGraduationRequirements();
            Calendar calendar = Calendar.getInstance();
            calendar.set(adminService.getSystemDateYear(),Calendar.FEBRUARY,1);
            for (GraduationRequirement i: graduationRequirements){
                if(courseDao.selectReqScoreByYearAndRegId(calendar.get(Calendar.YEAR),i.getReqId())==null){
                    courseDao.addReqScore(calendar.get(Calendar.YEAR),i.getReqId(),
                            getGraduationReqScoreByGraduationReqIdAndYear(i.getReqId(),calendar));
                }else{
                    courseDao.updateReqScore(getGraduationReqScoreByGraduationReqIdAndYear(
                            i.getReqId(),calendar),calendar.get(Calendar.YEAR),i.getReqId());
                }
            }
            return true;
        }catch (Exception e){
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.error("##在添加当年所有毕业要求成绩时发生异常。##"+e.toString());
            return false;
        }


    }

    @Override
    public List<CourseTargetMix> getAllCourseTargetMixByTargetId(int id) {
        return courseDao.selectCourseTargetMixByTargetId(id);
    }

    private double getTotalElLinksMixByCourseTaskId(int taskid){
        List<ExaminationLink> examinationLinks = courseDao.selectExaminationLinksByCourseTaskId(taskid);
        double totalMix = 0;
        if (examinationLinks!=null){
            for (ExaminationLink i: examinationLinks){
                totalMix = Arith.add(totalMix,i.getElMix());
            }
        }
        return totalMix;
    }

    private double getTotalCourseTaskMix(List<CourseTask> courseTasks){
        double courseTaskMix = 0;
        for (CourseTask i : courseTasks){
            courseTaskMix = Arith.add(courseTaskMix,i.getTargetMix());
        }
        return  courseTaskMix;
    }

    private List<CourseTask> getThisYearCourseTaskFilter(List<CourseTask> courseTasks){
        List<CourseTask> courseTasks1 = new ArrayList<>();
        if(courseTasks.size()>0){
            Calendar cl = Calendar.getInstance();
            for (CourseTask i : courseTasks){
                cl.setTime(i.getYear());
                if(adminService.getSystemDateYear() == cl.get(Calendar.YEAR)){
                    courseTasks1.add(i);
                }
            }
        }
        return courseTasks1;
    }

    private double getAvgExlinkScoreByCourseTaskId(int courseTaskId){
        List<ExaminationLink> examinationLinks = courseDao.selectExaminationLinksByCourseTaskId(courseTaskId);
        if(examinationLinks.size()>0){
            double score = 0;
            for(ExaminationLink i:examinationLinks){
                score=Arith.add(score,i.getElMix()*(i.getElAverageScore()/i.getElTargetScore()));
            }
            return score;
        }else return 0;
    }
}
