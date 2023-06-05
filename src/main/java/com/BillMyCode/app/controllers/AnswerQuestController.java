package com.BillMyCode.app.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf")
public class AnswerQuestController {

    @GetMapping("/answer-accountant")
    public String answerAccountant(HttpSession request, ModelMap model){
        return "preguntasyrespuestas";
    }
}
