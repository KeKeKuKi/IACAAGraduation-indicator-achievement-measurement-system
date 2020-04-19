package iacaasystem.admin.service;

import iacaasystem.admin.dao.AdiminDao;
import iacaasystem.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *  @author: ZhaoZezhong
 *  @advertisement: welcome to https://zhaozezhong.fun
 *  @Date: 2020/2/24 17:51
 *  @Description: admin service层实现类
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdiminDao adiminDao;

    @Override
    public Admin selectAdminByUserName(String userName) {
        return (adiminDao.selectAdminByUserName(userName));
    }

    @Override
    public boolean changeSystemDateYear(int year) {
        return adiminDao.updateSystemDate(year)==1;
    }

    @Override
    public int getSystemDateYear() {
        return adiminDao.selectSystemDate();
    }


}
