package iacaasystem.teacher.dao;

import iacaasystem.entity.DistributionCourse;
import iacaasystem.entity.Teacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeachersDao {
    @Select("select teacher_table_id as teacherId,teacher_password as passWord,teacher_job_number as teacherNumber,teacher_name as teacherName," +
            "teacher_sex as teacherSex,teacher_age as teacherAge, teacher_phonenumber as teacherPhonenumber, teacher_online_state as onlineState" +
            ",teacher_edit_state as editState" +
            " from teacher where teacher_job_number = #{jobNumber}")
    Teacher selectTeacherByJobNumber(String jobNumber);

    @Select("select teacher_table_id as teacherId,teacher_password as passWord,teacher_job_number as teacherNumber,teacher_name as teacherName," +
            "teacher_sex as teacherSex,teacher_age as teacherAge, teacher_phonenumber as teacherPhonenumber, teacher_online_state as onlineState" +
            ",teacher_edit_state as editState" +
            " from teacher where teacher_table_id = #{id}")
    Teacher selectTeacherByTeacherId(String id);


    @Select("select distribution_id as distributionId, distribution_course, distribution_teacher,distribution_time as distrTime" +
            " from distribution_course where distribution_teacher=#{id}")
    @Results({
            @Result(property = "districourse",column = "distribution_course",one = @One(select = "iacaasystem.admin.dao.CourseDao.selectCourseById")),
            @Result(property = "distriteacher",column = "distribution_teacher",one = @One(select = "iacaasystem.teacher.dao.TeachersDao.selectTeacherByTeacherId"))
    })
    List<DistributionCourse> selectDistributionCoursesByTeacherId(int id);
}
