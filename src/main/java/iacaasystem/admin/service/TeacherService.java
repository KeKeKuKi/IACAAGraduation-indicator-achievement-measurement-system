package iacaasystem.admin.service;

import iacaasystem.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> selectAllTeachers();
    boolean changeTeacherEditStateByTeacherId(int id) throws Exception;
    boolean changeAllTeacherState(int state) throws Exception;
    boolean ifHaveThisTeacherAcount(String acount);
    boolean addTeacher(Teacher teacher);
    boolean deleteTeacherByTeacherId(int id);
}
