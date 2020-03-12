package iacaasystem.teacher.service;

import iacaasystem.entity.DistributionCourse;
import iacaasystem.entity.Teacher;
import iacaasystem.teacher.dao.TeachersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("teachersService")
public class teachersServiceImpl implements TeachersService{
    @Autowired
    TeachersDao teachersDao;

    @Override
    public Teacher getTeacherByJobNumber(String jobNumber) {
        return teachersDao.selectTeacherByJobNumber(jobNumber);
    }

    @Override
    public List<DistributionCourse> getDistributionCoursesByTeacherId(int id) {
        return teachersDao.selectDistributionCoursesByTeacherId(id);
    }
}
