package iacaasystem.admin.dao;

import iacaasystem.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherDao {
    @Select("select teacher_table_id as teacherId,teacher_password as passWord,teacher_job_number as teacherNumber,teacher_name as teacherName," +
            "teacher_sex as teacherSex,teacher_age as teacherAge, teacher_phonenumber as teacherPhonenumber,teacher_online_state as onlineState," +
            "teacher_edit_state as editState from teacher")
    List<Teacher> selectAllTeachers();

    @Select("select teacher_table_id as teacherId,teacher_job_number as teacherNumber,teacher_name as teacherName,teacher_sex as teacherSex,teacher_age as teacherAge," +
            "teacher_phonenumber as teacherPhonenumber,teacher_online_state as onlineState,teacher_edit_state as editState from teacher where teacher_table_id = #{id}")
    Teacher selectTeacherById(int id);

    @Update("UPDATE teacher SET teacher_edit_state = #{editState} where teacher_table_id = #{teacherId}")
    int updateTeacherEditState(Teacher teacher);

    @Update("UPDATE teacher SET teacher_edit_state = #{state}")
    int updateAllTeacherEditState(int state);
}
