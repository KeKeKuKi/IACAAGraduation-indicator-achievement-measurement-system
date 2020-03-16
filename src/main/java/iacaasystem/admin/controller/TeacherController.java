package iacaasystem.admin.controller;

import iacaasystem.entity.Teacher;
import iacaasystem.admin.service.TeacherService;
import iacaasystem.utils.MyTools;
import iacaasystem.utils.Page;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @RequestMapping("/teacherlist")
    public String techerlist(HttpServletRequest request){
        int pageCount = 1;
        try{
            String page = request.getParameter("page");
            pageCount = Integer.parseInt(page);
        }catch (Exception e){
            LoggerFactory.getLogger(getClass()).info("adminController---/teacher--page is errro!");
        }

        Page<Teacher> page = new Page<>(pageCount,9,teacherService.selectAllTeachers(),"/admin/teacherlist?page=");
        request.setAttribute("teachers",page.getPage(pageCount));
        request.setAttribute("Buffer",page.getPageBuffer(pageCount));
        return "/admin/teacherslist";
    }

    @ResponseBody
    @RequestMapping("/changeTeacherEditState")
    public void changeTeacherEditState(HttpServletRequest request) throws Exception{
        String idstr = request.getParameter("teacherId");
        int id = Integer.parseInt(idstr);
        if(!teacherService.changeTeacherEditStateByTeacherId(id)) throw new Exception();
    }

    @ResponseBody
    @RequestMapping("/changeAllTeacherEditStateOn")
    public void changeAllTeacherEditStateOn() throws Exception {
        teacherService.changeAllTeacherState(1);
    }

    @ResponseBody
    @RequestMapping("/changeAllTeacherEditStateOff")
    public void changeAllTeacherEditStateOff() throws Exception {
        teacherService.changeAllTeacherState(0);
    }

    @ResponseBody
    @RequestMapping("/checkTeacherAcount")
    public void checkTeacherAcount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String acount = request.getParameter("useracount");
        if(teacherService.ifHaveThisTeacherAcount(acount)){
            response.getWriter().print("true");
        }else response.getWriter().print("false");

    }

    @ResponseBody
    @RequestMapping("/addTeacher")
    public void addTeacher(Teacher teacher, HttpServletResponse response) throws IOException {
        teacher.setPassWord(MyTools.toMd5String("teacher"));
        teacher.setEditState(0);
        teacher.setOnlineState(0);
        teacher.setTeacherAge(0);
        if(teacherService.addTeacher(teacher)){
            response.getWriter().print("true");
        }else response.getWriter().print("false");
    }

    @ResponseBody
    @RequestMapping("/deleteTeacher")
    public void deleteTeacher(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String id = request.getParameter("teacherId");
        try{
            if(teacherService.deleteTeacherByTeacherId(Integer.parseInt(id))){
                response.getWriter().print("true");
            }else response.getWriter().print("false");
        }catch (Exception e){
            response.getWriter().print("false");
        }

    }
}
