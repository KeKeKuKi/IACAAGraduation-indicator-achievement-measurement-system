package iacaasystem.admin.dao;

import iacaasystem.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdiminDao {
    /**
     *  @author: ZhaoZezhong
     *  @advertisement: welcome to https://zhaozezhong.fun
     *  @Date: 2020/2/24 17:48
     *  @Description:根据管理员账户查询管理员
     */
    @Select("select account,password,user_name as adminName,user_phonenumber as adminPhonenumber " +
            "from administrators where account = #{username}")
    Admin selectAdminByUserName(String username);

}
