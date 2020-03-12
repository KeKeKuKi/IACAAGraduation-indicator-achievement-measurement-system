package iacaasystem.admin.controller;

import iacaasystem.entity.*;
import iacaasystem.admin.service.CourseService;
import iacaasystem.admin.service.TeacherService;
import iacaasystem.utils.Page;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teacherService;

    @RequestMapping("/courselist")
    public String sourses(HttpServletRequest request){
        int pageCount = 1;
        try{
            String page = request.getParameter("page");
            pageCount = Integer.parseInt(page);
        }catch (Exception e){
            LoggerFactory.getLogger(getClass()).info("courseController---/course--page is errro!");
        }

        Page<Course> page = new Page<>(pageCount,10,courseService.getAllCourse(),"/admin/courselist?page=");
        request.setAttribute("data",page.getPage(pageCount));
        request.setAttribute("Buffer",page.getPageBuffer(pageCount));

        return "/admin/courselist";
    }


    @RequestMapping("/distrcourse")
    public String distrcourse(HttpServletRequest request){
        int pageCount = 1;
        try{
            request.setAttribute("teachers",teacherService.selectAllTeachers());
            request.setAttribute("courses",courseService.getAllCourse());

            String npage = request.getParameter("page");
            pageCount = Integer.parseInt(npage);
        }catch (Exception e){
            LoggerFactory.getLogger(getClass()).info("courseController---/distrcourse--page is errro!");
        }


        Page<DistributionCourse> page = new Page<>(pageCount,10,courseService.getAllDistributionCourse(),"/admin/distrcourse?page=");
        request.setAttribute("data",page.getPage(pageCount));
        request.setAttribute("buffer",page.getPageBuffer(pageCount));
        return "/admin/distrcourse";
    }

    @RequestMapping("/adddistribution")
    public void addDistribution(HttpServletRequest request, HttpServletResponse response){
        Logger logger = LoggerFactory.getLogger(getClass());
        String teacherId = request.getParameter("teacherid");
        String courseId = request.getParameter("courseid");
        Writer writer;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            logger.warn(e.toString());
            return;
        }
        try{
            int teacherIdN = Integer.parseInt(teacherId);
            int courseIdN = Integer.parseInt(courseId);

            if(courseService.addDistributionCourse(teacherIdN,courseIdN)){
                writer.write("true");
            }
         }catch (Exception e){
            try {
                writer.write("false");
            } catch (IOException ex) {
                logger.warn(ex.toString());
                return;
            }
        }

    }

    @RequestMapping(value = "/editcourse")
    public String editCourse(HttpServletRequest request, Map map){
        try{
            String courseIdStr = request.getParameter("courseId");
            int courseId = Integer.parseInt(courseIdStr);
            Course course = courseService.getCourseById(courseId);
            map.put("course",course);
            map.put("ctmixs",courseService.getAllCourseTargetMixByCourseId(course.getCourseId()));
            map.put("courseTasks",courseService.getThisYearTasksByCourseId(courseId));
            return "admin/distributiongcourseedit";
        }catch (Exception e){
            e.printStackTrace();
            map.put("erromessage",e.toString());
            return "admin/404";
        }
    }


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
            logger.warn("admin/saveCourseTask something wrong"+e.toString());
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
            logger.warn("admin/deleteCourseTask something wrong"+e.toString());
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
            logger.warn("admin/deleteElink something wrong"+e.toString());
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
            logger.warn("admin/addCourseTask something wrong"+e.toString());
        }

    }


    @RequestMapping(value = "/editcoursetask")
    public String editCourseTask(HttpServletRequest request, Map map){
        try{
            String courseIdStr = request.getParameter("courseId");
            int courseId = Integer.parseInt(courseIdStr);
            Course course = courseService.getCourseById(courseId);
            map.put("course",course);
            map.put("els",courseService.getThisYearExaminationLinksByCourseId(courseId));
            map.put("courseTasks",courseService.getThisYearTasksByCourseId(courseId));
            return "admin/coursetaskedit";
        }catch (Exception e){
            map.put("erromessage",e.toString());
            return "admin/404";
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
            logger.warn("/admin/updateElLink "+e.toString());
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
            logger.warn("/admin//addExaminationLink "+e.toString());
            writer.write("false");
        }

    }

    @RequestMapping(value = "/editeLinks")
    public String editeLinks(HttpServletRequest request, Map map){
        try{

            String courseIdStr = request.getParameter("courseId");
            int courseId = Integer.parseInt(courseIdStr);
            Course course = courseService.getCourseById(courseId);

            if(courseService.ifCourseEditFinshed(course.getCourseId())){
                map.put("course",course);
                map.put("els",courseService.getThisYearExaminationLinksByCourseId(courseId));
                map.put("courseTasks",courseService.getThisYearTasksByCourseId(courseId));
                return "admin/elinkedit";
            }else {
                return "admin/coursenotcomplete";
            }
        }catch (Exception e){
            map.put("erromessage",e.toString());
            return "admin/404";
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
            logger.warn("/admin//updateExaminationLinkAvgScore "+e.toString());
            writer.write("false");
        }

    }

    @ResponseBody
    @RequestMapping("/totalReqScore")
    public void totalReqScore(HttpServletRequest request){

    }

    @RequestMapping("/showScore")
    public String showScore(Map map){
        double scores[] = courseService.getAllGraduationReqScoreByYear(2020);
        List<GraduationRequirement> graduationRequirements = courseService.getAllGraduationRequirements();
        int graduationRequirementsSize = graduationRequirements.size();
        String graduationRequirementsName[] = new String[graduationRequirementsSize];
        for(int i=0;i<graduationRequirementsSize;i++){
            graduationRequirementsName[i] = graduationRequirements.get(i).getReqTitle();
        }
        map.put("graduationRequirements",graduationRequirements);
        map.put("graduationRequirementsName",graduationRequirementsName);
        map.put("scores",scores);
        return "/admin/show";
    }
}
