package iacaasystem.teacher.controller;

import iacaasystem.admin.service.CourseService;
import iacaasystem.admin.service.TeacherService;
import iacaasystem.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

@Controller
@RequestMapping("/teacher/course")
public class TeacherCourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teacherService;




    @ResponseBody
    @RequestMapping("/saveCourseTask")
    public void saveCourseTask(CourseTask courseTask,HttpServletResponse response){
        try {
            Writer writer = response.getWriter();
            if(courseService.updateCourseTask(courseTask)){
                writer.write("true");
            }else writer.write("false");
        }catch (Exception e){
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.warn("teacher/saveCourseTask something wrong"+e.toString());
            return;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteCourseTask")
    public void deleteCourseTask(HttpServletRequest request,HttpServletResponse response){
        try {
            Writer writer = response.getWriter();
            if(courseService.deleteCourseTaskByTaskId(Integer.parseInt(request.getParameter("taskId")))){
                writer.write("true");
            }else writer.write("false");
        }catch (Exception e){
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.warn("teacher/deleteCourseTask something wrong"+e.toString());
            return;
        }

    }


    @ResponseBody
    @RequestMapping("/deleteElink")
    public void deleteElink(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Writer writer = response.getWriter();
        try {
            if(courseService.deleteExaminationLinkByElId(Integer.parseInt(request.getParameter("elId")))){
                writer.write("true");
            }else writer.write("false");
        }catch (Exception e){
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.warn("teacher/deleteElink something wrong"+e.toString());
            writer.write("false");
            return;
        }

    }


    @ResponseBody
    @RequestMapping("/addCourseTask")
    public void addCourseTask(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Writer writer = response.getWriter();
        try {
            String courseIdstr = request.getParameter("courseId");
            String targetIdstr = request.getParameter("targetId");
            String dis = request.getParameter("dis");
            String mixstr = request.getParameter("mix");
            int courseId = Integer.parseInt(courseIdstr);
            int targetId = Integer.parseInt(targetIdstr);
            double mix = Double.parseDouble(mixstr);
            if(courseService.addCourseTask(dis,courseId,targetId,mix)){
                writer.write("true");
            }else writer.write("false");
        }catch (Exception e){
            writer.write("false");
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.warn("teacher/addCourseTask something wrong"+e.toString());
        }

    }




    @ResponseBody
    @RequestMapping("/updateElLink")
    public void updateElLink(ExaminationLink examinationLink,HttpServletResponse response,HttpServletRequest request) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            String taskId = request.getParameter("courseTaskId");
            int courseTaskId = Integer.parseInt(taskId);
            CourseTask courseTask = new CourseTask();
            courseTask.setTaskId(courseTaskId);
            examinationLink.setCourseTask(courseTask);

            if(examinationLink.getElId()>0&&examinationLink.getElTargetScore()>0&&examinationLink.getElTargetScore()<=1000
                    &&!examinationLink.getElName().equals("")&&examinationLink.getElMix()>0&&examinationLink.getElMix()<1){
                if(courseService.updateExaminationLink(examinationLink)){
                    writer.print("true");
                }else writer.print("false");
            } else{
                writer.print("false");
            }
        }catch (Exception e){
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.warn("/teacher/updateElLink "+e.toString());
            writer.print("false");
        }

    }


    @ResponseBody
    @RequestMapping("/addExaminationLink")
    public void addExaminationLink(ExaminationLink examinationLink,HttpServletRequest request,HttpServletResponse response) throws IOException {
        CourseTask courseTask = new CourseTask();
        PrintWriter writer = response.getWriter();
        try{
            courseTask.setTaskId(Integer.parseInt(request.getParameter("courseTaskId")));
            examinationLink.setCourseTask(courseTask);
            examinationLink.setElAverageScore(0);
            if("".equals(examinationLink.getElName())) {
                writer.write("false");
                return;
            }
            if(courseService.addExaminationLink(examinationLink)){
                writer.write("true");
                return;
            }else {
                writer.write("false");
                return;
            }


        }catch (Exception e){
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.warn("/teacher/addExaminationLink "+e.toString());
            writer.write("false");
        }

    }




    @ResponseBody
    @RequestMapping("/updateExaminationLinkAvgScore")
    public void updateExaminationLinkAvgScore(ExaminationLink examinationLink,HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        try{
            ExaminationLink exdata = courseService.getExaminationLinkByElId(examinationLink.getElId());
            exdata.setElAverageScore(examinationLink.getElAverageScore());
            if(courseService.updateElinkAvgScore(exdata)){
                writer.write("true");
            }else {
                writer.write("false");
            }
        }catch (Exception e){
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.warn("/teacher//updateExaminationLinkAvgScore "+e.toString());
            writer.write("false");
        }

    }

}
