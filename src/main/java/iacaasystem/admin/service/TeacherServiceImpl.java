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
    public boolean changeTeacherEditStateByTeacherId(int id) throws Exception{
        Teacher teacher = teacherDao.selectTeacherById(id);
        if(teacher.getEditState()==1) teacher.setEditState(0);
        else teacher.setEditState(1);
        if(teacherDao.updateTeacherEditState(teacher)==1){
            return true;
        }else return false;
    }

    @Override
    public boolean changeAllTeacherState(int state) throws Exception{
        int count = teacherDao.updateAllTeacherEditState(state);
        return true;
    }
}
