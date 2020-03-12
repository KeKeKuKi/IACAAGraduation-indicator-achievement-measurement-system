package iacaasystem.teacher.service;

import iacaasystem.entity.DistributionCourse;
import iacaasystem.entity.Teacher;

import java.util.List;

public interface TeachersService {
    Teacher getTeacherByJobNumber(String jobNumber);
    List<DistributionCourse> getDistributionCoursesByTeacherId(int id);
}
