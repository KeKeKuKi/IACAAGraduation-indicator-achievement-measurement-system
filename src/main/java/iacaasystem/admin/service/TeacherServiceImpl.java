package iacaasystem.admin.service;

import iacaasystem.admin.dao.TeacherDao;
import iacaasystem.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("teacherservice")
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherDao teacherDao;
    @Override
    public List<Teacher> selectAllTeachers() {
        return teacherDao.selectAllTeachers();
    }

    @Override
    public synchronized boolean changeTeacherEditStateByTeacherId(int id) throws Exception{
        Teacher teacher = teacherDao.selectTeacherById(id);
        if(teacher.getEditState()==1) teacher.setEditState(0);
        else teacher.setEditState(1);
        return teacherDao.updateTeacherEditState(teacher)==1;
    }

    @Override
    public synchronized boolean changeAllTeacherState(int state) throws Exception{
        int count = teacherDao.updateAllTeacherEditState(state);
        return true;
    }

    @Override
    public boolean ifHaveThisTeacherAcount(String acount) {
        Teacher teacher = teacherDao.selectTeacherByTeacherJobnumber(acount);
        return teacher==null;
    }

    @Override
    public synchronized boolean addTeacher(Teacher teacher) {
        return teacherDao.addTeacher(teacher)==1;
    }

    @Override
    public boolean deleteTeacherByTeacherId(int id) {
        return teacherDao.deleteTeacherByTeacherId(id)==1;
    }
}
