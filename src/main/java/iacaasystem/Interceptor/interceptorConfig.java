package iacaasystem.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  @author: ZhaoZezhong
 *  @advertisement: welcome to https://zhaozezhong.fun
 *  @Date: 2020/2/25 13:23
 *  @Description: 自定义拦截器，添加拦截路径和排除拦截路径
 */
@Configuration
public class interceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册管理员权限拦截器，排除管理员登录请求拦截
        registry.addInterceptor(new AdminLoginInterceptor()).addPathPatterns("/admin/**").excludePathPatterns("/admin/adminlogin");

        //注册学生权限拦截器，排除学生登录拦截请求
        registry.addInterceptor(new StudentLoginInterceptor()).addPathPatterns("/student/**").excludePathPatterns("/student/studentlogin");

        //注册教师权限拦截器，排除教师登录拦截请求
        registry.addInterceptor(new TeacherLoginInterceptor()).addPathPatterns("/teacher/**").excludePathPatterns("/teacher/teacherlogin");

        //注册教师课程编辑权限拦截器
        registry.addInterceptor(new TeacherEditInterceptor()).addPathPatterns("/teacher/course/**");
    }
}
