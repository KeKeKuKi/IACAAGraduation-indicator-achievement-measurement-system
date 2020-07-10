package iacaasystem.admin.controller;


import iacaasystem.admin.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireDaoController {
    @Autowired
    QuestionnaireService questionnaireService;



}
