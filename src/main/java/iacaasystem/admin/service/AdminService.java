package iacaasystem.admin.service;

import iacaasystem.entity.Admin;


/**
 *  @author: ZhaoZezhong
 *  @advertisement: welcome to https://zhaozezhong.fun
 *  @Date: 2020/2/24 17:50
 *  @Description: admin service层接口
 */
public interface AdminService {
    Admin selectAdminByUserName(String userName);
}
