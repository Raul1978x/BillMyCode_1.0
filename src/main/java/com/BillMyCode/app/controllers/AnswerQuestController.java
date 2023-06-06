package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Answer;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.services.AnswerService;
import com.BillMyCode.app.services.CommentService;
import com.BillMyCode.app.services.DeveloperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class AnswerQuestController {
    @Autowired
    public AnswerService answerService;
    @Autowired
    public DeveloperService developerService;
    @GetMapping("/answer-accountant")
    public String answerAccountant(HttpSession request, ModelMap model) {
        return "preguntasyrespuestas";
    }

    @GetMapping("/get-answer")
    public String getAnswer(HttpSession request, ModelMap model) {
        Accountant logueado = (Accountant) request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        return "answer";
    }

    @GetMapping("/accountant/get-answer")
    public String getViewQuestAndAnswer(HttpSession request, ModelMap model) {
        Accountant logueado= (Accountant) request.getAttribute("sessionuser");
        model.put("logueado",logueado);
        List<Developer> developerList = developerService.searchDeveloperByAccountantId(logueado.getId());
        model.put("developerList",developerList);
        return "preguntasyrespuestas";

    }
    @PostMapping("/create-answer")
    public String createAnswer(@RequestParam String respuesta, ModelMap model, HttpSession request) {
        Accountant logueado = (Accountant) request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        Answer answer = answerService.createAnswer(respuesta);
        model.put("answer", answer);
        return "redirect:/thymeleaf/accountant/get-answer";
    }
}