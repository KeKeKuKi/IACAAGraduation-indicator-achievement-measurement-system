package iacaasystem.admin.service;

import iacaasystem.admin.dao.AdiminDao;
import iacaasystem.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



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
