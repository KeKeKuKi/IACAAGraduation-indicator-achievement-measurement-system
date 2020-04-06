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
import java.util.*;



/**
 *  @author: ZhaoZezhong
 *  @advertisement: zhaozezhong.mail@foxmail.com
 *  @Date: 2020/3/22
 *  @Description:该控制器负责管理员对课程信息操作的所有业务包装与分发
 */
@Controller
@RequestMapping("/admin")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teacherService;

    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/22 19:43
     *  @Description:查询所有课程信息返回分页数据
     */
    @RequestMapping("/courselist")
    public String sourses(HttpServletRequest request){
        int pageCount = 1;
        try{
            request.setAttribute("teachers",teacherService.selectAllTeachers());
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

    
    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/22 19:44
     *  @Description:返回所有毕业要求及其对应指标点
     */
    @RequestMapping("/reqshow")
    public String reqShow(HttpServletRequest request){
        int cPage = 1;
        try{
            String page = request.getParameter("page");
            cPage = Integer.parseInt(page);
        }catch (Exception e){
            LoggerFactory.getLogger(getClass()).info("courseController---/reqshoe--page is errro!");
        }

        Page<GraduationRequirement> page = new Page<>(cPage,12,courseService.getAllGraduationRequirements(),"/admin/reqshow?page=");
        request.setAttribute("data",page.getPage(cPage));
        request.setAttribute("Buffer",page.getPageBuffer(cPage));
        request.setAttribute("targets",courseService.getAllTargets());

        return "/admin/guaduation_show";
    }



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/22 19:45
     *  @Description:返回所有已分配的课程任务信息
     */
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


    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/22 19:46
     *  @Description:根据课程以及账号Id添加本年度课程任务分配
     */
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


    @RequestMapping("/updateThisYearReqScore")
    public String updateThisYearReqScore(){
        courseService.setAllThisYearReqScore();
        return "admin/adminConsole";
    }



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/22 19:48
     *  @Description:根据课程Id返回课程编辑页面
     */
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



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/22 19:49
     *  @Description:通过SpringMvc将前端数据自动封装，根据课程目标更改数据库课程目标信息
     */
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

    
    
    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/3/22 19:51
     *  @Description:
     */
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



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/22 19:52
     *  @Description:删除课程目标考核环节
     */
    @ResponseBody
    @RequestMapping("/deleteElink")
    public void deleteElink(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Writer writer = response.getWriter();
        try {
            if(courseService.deleteExaminationLinkByElId(Integer.parseInt(request.getParameter("elId")))){
                writer.write("true");
            }else writer.write("false");
        }catch (Exception e){ //捕获异常并处理，同时记录错误日志
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.warn("admin/deleteElink something wrong"+e.toString());
            writer.write("false");
            return;
        }

    }



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/22 19:53
     *  @Description:根据课程Id以及指标点添加课程目标
     */
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



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/22 19:54
     *  @Description:更改课程目标
     */
    @RequestMapping(value = "/editcoursetask")
    public String editCourseTask(HttpServletRequest request, Map map){
        try{
            String courseIdStr = request.getParameter("courseId");
            String targetIdStr = request.getParameter("targetId");
            int courseId = Integer.parseInt(courseIdStr);
            int targetId = Integer.parseInt(targetIdStr);
            Course course = courseService.getCourseById(courseId);
            List<CourseTask> courseTasks = courseService.getThisYearTasksByCourseId(courseId);
            for (int i=0;i<courseTasks.size();i++){
                if(courseTasks.get(i).getTtarget().getTargetId()!=targetId){
                    courseTasks.remove(i);
                    --i;
                }
            }
            if (courseTasks.size()<=0) return "admin/courseTaskNotComplete";
            map.put("course",course);
            map.put("els",courseService.getThisYearExaminationLinksByCourseId(courseId));
            map.put("courseTasks",courseTasks);
            return "admin/coursetaskedit";
        }catch (Exception e){
            map.put("erromessage",e.toString());
            return "admin/404";
        }
    }



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/22 19:55
     *  @Description:更改课程目标考核环节
     */
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



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/22 19:55
     *  @Description:添加课程目标考核环节
     */
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



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020//22 19:56
     *  @Description:返回课程目标考核环节编辑页面
     *
     */
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



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/22 19:57
     *  @Description: 更改课程目标考核环节平均分数
     */
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



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/23
     *  @Description:
     */
    @ResponseBody
    @RequestMapping("/totalReqScore")
    public void totalReqScore(HttpServletRequest request){

    }



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/2/24
     *  @Description:返回本年度毕业指标展示分数页面
     */
    @RequestMapping("/showThisYearScore")
    public String showScore(Map map,HttpServletRequest request){
        String year = request.getParameter("year");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int thisyear = calendar.get(Calendar.YEAR);
        try{
            calendar.set(Integer.parseInt(year),1,1);
        }catch (Exception e){}
        double scores[] = courseService.getAllGraduationReqScoreByYear(calendar.get(Calendar.YEAR));
        List<GraduationRequirement> graduationRequirements = courseService.getAllGraduationRequirements();
        int graduationRequirementsSize = graduationRequirements.size();
        String graduationRequirementsName[] = new String[graduationRequirementsSize];
        for(int i=0;i<graduationRequirementsSize;i++){
            graduationRequirementsName[i] = graduationRequirements.get(i).getReqId()+":"+ graduationRequirements.get(i).getReqTitle();
        }
        map.put("graduationRequirements",graduationRequirements);
        map.put("graduationRequirementsName",graduationRequirementsName);

        map.put("scores",scores);
        List<Integer> years = new LinkedList<>();
        years.add(calendar.get(Calendar.YEAR));

        for (int k=thisyear;k>=2015;k--){
            if(k==calendar.get(Calendar.YEAR)) continue;
            years.add(k);
        }
        //添加年份列表
        map.put("years",years);
        int now = calendar.get(Calendar.YEAR);
        //添加本年年份标记
        map.put("thisyear",now);
        request.setAttribute("thisyear",year);
        return "/admin/show";
    }


    @RequestMapping("/showTwoYearsScore")
    public String showTwoYearsScore(Map map,HttpServletRequest request){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int thisyear = calendar.get(Calendar.YEAR);

        double scores[] = courseService.getAllGraduationReqScoreByYear(calendar.get(Calendar.YEAR));
        double lastScores[]  = courseService.getAllGraduationReqScoreByYear(calendar.get(Calendar.YEAR)-1);
        double lastTwoScores[]  = courseService.getAllGraduationReqScoreByYear(calendar.get(Calendar.YEAR)-2);

        List<GraduationRequirement> graduationRequirements = courseService.getAllGraduationRequirements();
        String graduationRequirementsName[] = new String[graduationRequirements.size()];
        for(int i=0;i<graduationRequirements.size();i++){
            graduationRequirementsName[i] = graduationRequirements.get(i).getReqId()+":"+ graduationRequirements.get(i).getReqTitle();
        }
        map.put("graduationRequirements",graduationRequirements);
        map.put("graduationRequirementsName",graduationRequirementsName);

        map.put("thisyearscores",scores);
        map.put("lastyearscores",lastScores);
        map.put("lastTwoScores",lastTwoScores);
        List<Integer> years = new LinkedList<>();
        years.add(calendar.get(Calendar.YEAR));

        for (int k=2010;k<=thisyear;k++){
            if(k==calendar.get(Calendar.YEAR)) continue;
            years.add(k);
        }
        //添加年份列表
        map.put("years",years);
        int now = calendar.get(Calendar.YEAR);
        //添加本年年份标记
        map.put("thisyear",now);
        map.put("lastyear",now-1);
        request.setAttribute("thisyear",thisyear);
        request.setAttribute("lastyear",thisyear-1);
        request.setAttribute("lasttwoyear",thisyear-2);
        return "/admin/show_years";
    }



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/3/25
     *  @Description:根据毕业指标Id返回该毕业指标具体分数展示页面
     */
    @RequestMapping("/showscoreByReqId")
    public String showscoreByReqId(Map map,HttpServletRequest request){
        String req = request.getParameter("reqid");
        String years = request.getParameter("year");
        int reqId = 0;
        int year = 0;
        try{
            reqId= Integer.parseInt(req);
            year = Integer.parseInt(years);
        }catch (Exception e){
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.error("/admin/showscoreByReqId##"+e.toString());
            return "/admin/404";
        }
        double scores [] = courseService.getAllTargetScoreByReqIdAndYear(reqId,year);
        List<Target> targets = courseService.getAllTargetsByReqId(reqId);
        int targetsSize = targets.size();
        String [] targetsName = new String[targetsSize];
        for(int i=0;i<targetsSize;i++){
            targetsName[i] = targets.get(i).getTargetId()+":"+targets.get(i).getTargetDiscribe();
        }
        String [] targetsId = new String[targetsSize];
        for(int i=0;i<targetsSize;i++) {
            targetsId[i] = "指标点："+targets.get(i).getTargetId();
        }
        map.put("year",year);
        map.put("targets",targets);
        map.put("targetsName",targetsName);
        map.put("scores",scores);
        map.put("targetId",targetsId);
        return "/admin/show_req_score";
    }



    /**
     *  @author: ZhaoZezhong
     *  @advertisement: zhaozezhong.mail@foxmail.com
     *  @Date: 2020/3/25
     *  @Description:根据指标点返回该指标点分数详细情况
     */
    @RequestMapping("/showscoreByTargetId")
    public String showscoreByTargetId(Map map,HttpServletRequest request){
        String tar = request.getParameter("targetId");
        String ye = request.getParameter("year");

        try {
            int targetId = Integer.parseInt(tar);
            int year = Integer.parseInt(ye);
            List<CourseTargetMix> courseTargetMixes = courseService.getAllCourseTargetMixByTargetId(targetId);
            int size=courseTargetMixes.size();
            String [] courses = new String[size];
            int [] coursesId = new int[size];
            double [] coursesMix = new double[size];
            double [] coursesScorse = new double[size];
            for (int i=0;i<size;i++){
                courses[i] = courseTargetMixes.get(i).getCourse().getCourseId()+":"+courseTargetMixes.get(i).getCourse().getCourseName();
                coursesId[i] = courseTargetMixes.get(i).getCourse().getCourseId();
                coursesMix[i] = courseTargetMixes.get(i).getCtmix();
                coursesScorse[i] = (double) Math.round(courseService.getAvgCourseTaskScoreByCoursIdAndTargetIdAndYear(targetId,courseTargetMixes.get(i).getCourse().getCourseId(),year) * 100) / 100;
            }

            map.put("courses",courses);
            map.put("coursesId",coursesId);
            map.put("coursesMix",coursesMix);
            map.put("coursesScorse",coursesScorse);
            return "/admin/show_target_score";
        }catch (Exception e){
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.error("/admin/showscoreByTargetId##"+e.toString());
            return "/admin/404";
        }
    }
}
