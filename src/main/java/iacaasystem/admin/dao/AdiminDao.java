package iacaasystem.admin.dao;

import iacaasystem.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author ZhaoZezhong
 * @date Created in 2020/4/19
 * @description 管理员相关Dao操作
 * @version 1.0
 */
@Mapper
@Repository
public interface AdiminDao {
    /**
     *  @author ZhaoZezhong
     *  @return Admin
     *  @param username String
     */
    @Select("select " +
            "account," +
            "password," +
            "user_name as adminName," +
            "user_phonenumber as adminPhonenumber " +
            "from " +
            "administrators " +
            "where " +
            "account = #{username}")
    Admin selectAdminByUserName(String username);

    /**
     *  @author ZhaoZezhong
     *  @param dateYear int
     *  @return int
     */
    @Update("UPDATE " +
            "system_config " +
            "SET config_data=#{date} " +
            "WHERE config_name='system_date_year'")
    int updateSystemDate(int dateYear);

    @Select("select " +
            "config_data " +
            "from system_config " +
            "WHERE config_name='system_date_year'")
    int selectSystemDate();
}
