package iacaasystem.admin.controller;

import iacaasystem.entity.Admin;

import iacaasystem.admin.service.AdminService;
import iacaasystem.utils.MyTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @ResponseBody
    @RequestMapping("/adminlogin")
    public void list(Model model, HttpServletRequest request, ServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        String userName = request.getParameter("name");
        String passWord = request.getParameter("passWord");
        if(userName==null||"".equals(userName)||passWord==null||"".equals(passWord)){
            writer.print("If you enter this page, it means that you have performed illegal operations. Please go back to the login page to log in!");
            return;
        }
        passWord = MyTools.toMd5String(passWord);
        Admin admin = adminService.selectAdminByUserName(userName);
        if (admin!=null){
            if(passWord.equals(admin.getPassWord())){
                request.getSession().setAttribute("admin",admin.getAdminName());
                request.getSession().setMaxInactiveInterval(600);//设置最大非活动时间10分钟
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
        request.getSession().removeAttribute("admin");
        return "login";
    }




    @RequestMapping("/toConsole")
    public String toConsole(){
        return "/admin/adminConsole";
    }
}
