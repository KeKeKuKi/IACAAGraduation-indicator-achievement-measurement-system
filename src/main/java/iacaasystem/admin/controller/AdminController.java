package iacaasystem.admin.controller;

import iacaasystem.entity.Admin;
import iacaasystem.admin.service.AdminService;
import iacaasystem.utils.MyTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;


    /**
     * description: 方法用于判断用户是否登录成功，输出值 1：用户名及密码正确 0：密码错误 -1;用户不存在
     * Created in 2020/4/19
     * @author ZhaoZezhong
     */
    @ResponseBody
    @GetMapping("/adminlogin")
    public void list(HttpServletRequest request, ServletResponse response) throws IOException {
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

    /**
     * description: 注销功能
     * Created in 2020/4/19
     * @author ZhaoZezhong
     *  @return String
     */
    @RequestMapping("/sinout")
    public String sinout(HttpServletRequest request){
        request.getSession().removeAttribute("admin");

        return "login";
    }




    /**
     * description: 跳转至控制台页面
     * Created in 2020/4/19
     * @author ZhaoZezhong
     */
    @RequestMapping("/toConsole")
    public String toConsole(Map map){
        List<Integer> years = new LinkedList<>();
        years.add(adminService.getSystemDateYear());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        for (int j= calendar.get(Calendar.YEAR);j>2015;j--){
            years.add(j);
        }
        map.put("years",years);
        return "/admin/adminConsole";
    }


    @RequestMapping("/changeSystemDate")
    public String changeSystemDate(Integer year,Map map,HttpServletRequest request){
        final Logger logger = LoggerFactory.getLogger(getClass());
        if(adminService.changeSystemDateYear(year)){
            logger.warn(request.getSession().getAttribute("admin")+"更改了系统时间#DateYear:"+year);
        }else {
            logger.warn(request.getSession().getAttribute("admin")+"更改系统时间#DateYear:"+year+"失败！");
        }

        List<Integer> years = new LinkedList<>();
        years.add(adminService.getSystemDateYear());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        for (int i= calendar.get(Calendar.YEAR);i>2015;i--){
            years.add(i);
        }
        map.put("years",years);
        return "/admin/adminConsole";
    }

}
