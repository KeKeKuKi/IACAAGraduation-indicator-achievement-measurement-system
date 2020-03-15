package iacaasystem.admin.dao;

import iacaasystem.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface CourseDao {
    @Select("select course_id as courseId,course_name as courseName,course_image as courseImageUrl " +
            "from course")
    List<Course> selectAllCourse();

    @Select("select course_id as courseId,course_name as courseName,course_image as courseImageUrl " +
            "from course where course_id=#{id}")
    Course selectCourseById(int id);

    @Select("select el_id as elId,c_task,se_name as elName,target_score as elTargetScore,average_score as elAverageScore,el_mix as elMix " +
            "from examination_link where el_id=#{id}")
    @Results({
            @Result(property = "courseTask",column = "c_task",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectCourseTaskByTaskId"))
    })
    ExaminationLink selectExaminationLinkById(int id);

    @Select("select req_id as reqId,req_name as reqTitle,req_discrible as reqDiscribe " +
            "from graduation_requirements where req_id=#{id}")
    GraduationRequirement selectGraduationRequirementById(int id);

    @Select("select req_id as reqId,req_name as reqTitle,req_discrible as reqDiscribe " +
            "from graduation_requirements")
    List<GraduationRequirement> selectGraduationRequirements();

    @Select("select tar_id as targetId,tar_discribe as targetDiscribe,tar_req " +
            "from targets where tar_id=#{id}")
    @Results({
            @Result(property = "graduationRequirement",column = "tar_req",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectGraduationRequirementById"))
    })
    Target selectTargetById(int id);

    @Update("insert into distribution_course(distribution_course,distribution_teacher,distribution_time) " +
            "values(#{courseid},#{teacherid},#{date})")
    int addDistributionCourse(int teacherid, int courseid,String date);




    /**
     *  @author: ZhaoZezhong
     *  @advertisement: welcome to https://zhaozezhong.fun
     *  @Date: 2020/2/27 12:25
     *  @Description: 查询所有已分配课程
     */
    @Select("select distribution_id as distributionId, distribution_course, distribution_teacher,distribution_time as distrTime" +
            " from distribution_course")
    @Results({
            @Result(property = "districourse",column = "distribution_course",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectCourseById")),
            @Result(property = "distriteacher",column = "distribution_teacher",one = @One(select = "iacaasystem.admin.dao.TeacherDao.selectTeacherById"))
    })
    List<DistributionCourse> selectAllDistributionCourse();



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: welcome to https://zhaozezhong.fun
     *  @Date: 2020/2/26 14:30
     *  @Description: 方法用于根据已任课程查询课程
     */
    @Select("select distribution_id as distributionId, distribution_course, distribution_teacher,distribution_time as distrTime" +
            " from distribution_course where distribution_id=#{id}")
    @Results({
            @Result(property = "districourse",column = "distribution_course",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectCourseById")),
            @Result(property = "distriteacher",column = "distribution_teacher",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectTeacherById"))
    })
    DistributionCourse selectDistributionCourseByCourseId(int id);




    /**
     *  @author: ZhaoZezhong
     *  @advertisement: welcome to https://zhaozezhong.fun
     *  @Date: 2020/2/27 12:24
     *  @Description:根据课程ID获取该课程支撑的所有指标点以及权重
     */
    @Select("select coursemixid as ctmixId, coursex, targetx,coursetargetsmix as ctmix" +
            " from ctmix where coursex=#{id}")
    @Results({
            @Result(property = "course",column = "coursex",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectCourseById")),
            @Result(property = "target",column = "targetx",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectTargetById"))
    })
    List<CourseTargetMix> selectCourseTargetMixByCourseId(int courseid);

    @Select("select coursemixid as ctmixId, coursex, targetx,coursetargetsmix as ctmix" +
            " from ctmix where targetx=#{tagetId}")
    @Results({
            @Result(property = "course",column = "coursex",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectCourseById")),
            @Result(property = "target",column = "targetx",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectTargetById"))
    })
    List<CourseTargetMix> selectCourseTargetMixByTargetId(int tagetId);


    /**
     *  @author: ZhaoZezhong
     *  @advertisement: welcome to https://zhaozezhong.fun
     *  @Date: 2020/2/27 12:24
     *  @Description:根据课程ID和指标点ID获取该课程支撑的所有有关该指标点的所有课程教学目标及支撑权重
     */
    @Select("select task_id as taskId,task_describe as taskDiscribe,task_course,target,target_mix as targetMix,task_year as year" +
            " from course_task where task_course=#{courseid} and target=#{targetId}")
    @Results({
            @Result(property = "TCourse",column = "task_course",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectCourseById")),
            @Result(property = "Ttarget",column = "target",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectTargetById"))
    })
    List<CourseTask> selectCourseTaskByCourseIdAndTargetId(int courseid,int targetId);

    @Select("select task_id as taskId,task_describe as taskDiscribe,task_course,target,target_mix as targetMix,task_year as year" +
            " from course_task where task_course=#{courseid}")
    @Results({
            @Result(property = "TCourse",column = "task_course",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectCourseById")),
            @Result(property = "Ttarget",column = "target",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectTargetById"))
    })
    List<CourseTask> selectCourseTaskByCourseId(int courseid);


    @Update("UPDATE course_task SET task_describe = #{taskDiscrible},target_mix = #{taskMix} WHERE task_id = #{taskId}")
    int UpdateCourseTask(int taskId,String taskDiscrible,double taskMix);


    @Delete("DELETE FROM course_task WHERE task_id = #{id}")
    int DeleteCourseTaskByTaskId(int id);


    @Select("select task_id as taskId,task_describe as taskDiscribe,task_course,target,target_mix as targetMix,task_year as year " +
            "from course_task where task_id=#{taskId}")
    @Results({
            @Result(property = "TCourse",column = "task_course",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectCourseById")),
            @Result(property = "Ttarget",column = "target",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectTargetById"))
    })
    CourseTask selectCourseTaskByTaskId(int taskId);

    @Update("INSERT INTO course_task (task_describe,task_course,target,target_mix,task_year) VALUES (#{dis},#{courseId},#{targetId},#{mix},#{year})")
    int addCourseTask(String dis, int courseId, int targetId, double mix, Date year);

    @Select("select el_id as elId,c_task,se_name as elName,target_score as elTargetScore,average_score as elAverageScore,el_mix as elMix " +
            "from examination_link where c_task=#{id}")
    @Results({
            @Result(property = "courseTask",column = "c_task",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectCourseTaskByTaskId"))
    })
    List<ExaminationLink> selectExaminationLinksByCourseTaskId(int id);

    @Update("UPDATE examination_link SET se_name = #{elName},target_score = #{elTargetScore},el_mix = #{elMix} WHERE el_id = #{elId}")
    int updateExaminationLink(int elId,String elName,double elTargetScore,double elMix);

    @Delete("DELETE FROM examination_link WHERE el_id = #{id}")
    int deleteExaminationLinkByElId(int id);

    @Update("insert into examination_link (c_task,se_name,target_score,average_score,el_mix) " +
            "values(#{courseTask},#{elName},#{elTargetScore},#{elAverageScore},#{elMix})")
    int addExaminationLink(int courseTask ,String elName,double elTargetScore,double elAverageScore,double elMix);

    @Update("UPDATE examination_link SET average_score = #{elAvgScore} WHERE el_id = #{elId}")
    int updateExaminationLinkAvgScore(int elId,double elAvgScore);

    @Select("select tar_id as targetId,tar_discribe as targetDiscribe,tar_req " +
            "from targets where tar_req=#{id}")
    @Results({
            @Result(property = "graduationRequirement",column = "tar_req",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectGraduationRequirementById"))
    })
    List<Target> selectTargetByReqId(int id);

    @Update("INSERT INTO req_grade (req_years,req_grade,req) VALUES (#{date},#{score},#{reqid})")
    int addReqScore(Date date,int reqid, double score);

}