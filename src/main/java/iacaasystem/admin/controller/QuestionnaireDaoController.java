package iacaasystem.admin.controller;


import iacaasystem.admin.service.QuestionnaireService;
import iacaasystem.utils.MyTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireDaoController {
    @Autowired
    QuestionnaireService questionnaireService;


    @RequestMapping("/questionnaires_list")
    public String qrList(HttpServletRequest request, Map<String,Object> map){
        String adress = null;
        adress =  request.getRemoteAddr();
        MyTools.printBehaviorLog(getClass(), LocalDateTime.now(),adress,"查看了调查问卷！");
        return "/questionnaire";
    }

}
