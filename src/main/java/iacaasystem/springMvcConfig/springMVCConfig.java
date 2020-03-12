package iacaasystem.springMvcConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 *  @author: ZhaoZezhong
 *  @advertisement: welcome to https://zhaozezhong.fun
 *  @Date: 2020/2/24 17:52
 *  @Description: springMvc自定义配置
 */
@Configuration
public class springMVCConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //注册首页
        registry.addViewController("/").setViewName("login");

        //注册管理员主页springmvc
        registry.addViewController("/admin/adminmian").setViewName("admin/adminmain");

        //注册学生主页springmvc
        registry.addViewController("/student/studentmain").setViewName("student/studentmain");

        //注册老师主页springmvc
        registry.addViewController("/teacher/teachermain").setViewName("teacher/teachermain");

        //注册老师主页关于系统
        registry.addViewController("/admin/aboutsystem").setViewName("admin/aboutSystem");
    }
}
