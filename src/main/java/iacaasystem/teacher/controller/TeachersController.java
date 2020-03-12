package iacaasystem.teacher.controller;

import iacaasystem.admin.service.CourseService;
import iacaasystem.entity.Course;
import iacaasystem.entity.DistributionCourse;
import iacaasystem.entity.Teacher;
import iacaasystem.teacher.service.TeachersService;
import iacaasystem.utils.MyTools;
import iacaasystem.utils.Page;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeachersController {
    @Autowired
    TeachersService teachersService;

    @Autowired
    CourseService courseService;

    @ResponseBody
    @RequestMapping("/teacherlogin")
    public void list(HttpServletRequest request, ServletResponse response) throws IOException, ServletException {
        PrintWriter writer = response.getWriter();
        String userName = request.getParameter("name");
        String passWord = request.getParameter("passWord");
        if(userName==null||"".equals(userName)||passWord==null||"".equals(passWord)){
            writer.print("If you enter this page, it means that you have performed illegal operations. Please go back to the login page to log in! ");
            return;
        }
        passWord = MyTools.toMd5String(passWord);

        Teacher teacher = teachersService.getTeacherByJobNumber(userName);
        if (teacher != null){
            if(teacher.getPassWord().equals(passWord)){
                request.getSession().setAttribute("teacher",teacher);
                request.getSession().setMaxInactiveInterval(1800);//设置最大非活动时间30分钟
                writer.print("1");
            }else {
                writer.print("0");
            }
        }else {
            writer.print("-1");
        }
    }



    @RequestMapping("/sinout")
    public String sinout(HttpServletRequest request){
        request.getSession().removeAttribute("teacher");
        return "login";
    }


    @RequestMapping("/distrcourses")
    public String distrcourse(HttpServletRequest request){
        int pageCount = 1;
        try{
            String page = request.getParameter("page");
            pageCount = Integer.parseInt(page);
        }catch (Exception e){
            LoggerFactory.getLogger(getClass()).info("courseController---teacher/course--page is errro!");
        }
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        Page<DistributionCourse> page = new Page<>(pageCount,10,teachersService.getDistributionCoursesByTeacherId(teacher.getTeacherId()),"/teacher/distrcourses?page=");
        request.setAttribute("data",page.getPage(pageCount));
        request.setAttribute("Buffer",page.getPageBuffer(pageCount));
        return "/teacher/distributioncourses";
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
            return "teacher/distributiongcourseedit";
        }catch (Exception e){
            e.printStackTrace();
            map.put("erromessage",e.toString());
            return "teacher/404";
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
            return "teacher/coursetaskedit";
        }catch (Exception e){
            map.put("erromessage",e.toString());
            return "teacher/404";
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
                return "teacher/elinkedit";
            }else {
                return "teacher/coursenotcomplete";
            }
        }catch (Exception e){
            map.put("erromessage",e.toString());
            return "teacher/404";
        }
    }


}
