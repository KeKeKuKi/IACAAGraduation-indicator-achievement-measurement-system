package iacaasystem.admin.dao;


import iacaasystem.entity.QuestionnaireRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface QuestionnaireDao {

    @Select("select " +
            "qr_id as id," +
            "ip_adress as ipAdress," +
            "req_id," +
            "qr_grade as grade," +
            "qr_year as year "+
            "from questionnaire_record")
    @Results({
            @Result(property = "req",column = "req_id",
                    one = @One(select = "iacaasystem.admin.dao.CourseDao.selectGraduationRequirementById"))
    })
    List<QuestionnaireRecord> selectAllQR();


    @Select("select " +
            "qr_id as id," +
            "ip_adress as ipAdress," +
            "req_id," +
            "qr_grade as grade," +
            "qr_year as year "+
            "from questionnaire_record " +
            "where req_id = #{reqId}")
    @Results({
            @Result(property = "req",column = "req_id",
                    one = @One(select = "iacaasystem.admin.dao.CourseDao.selectGraduationRequirementById"))
    })
    List<QuestionnaireRecord> selectAllQRByReqId(int reqId);

    @Update("insert into questionnaire_record " +
            "(" +
            "ip_adress," +
            "req_id," +
            "qr_grade," +
            "qr_year" +
            ") " +
            "values(" +
            "#{ipAdress}," +
            "#{req.reqId}," +
            "#{grade}," +
            "#{year}" +
            ")")
    int insrtQR(QuestionnaireRecord qr);

}
