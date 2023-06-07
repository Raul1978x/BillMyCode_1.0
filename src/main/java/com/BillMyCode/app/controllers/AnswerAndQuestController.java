package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.AnswerAndQuestion;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class AnswerAndQuestController {

    @Autowired
    public AccountantService accountantService;
    @Autowired
    public DeveloperService developerService;
    @Autowired
    public AnswerAndQuestionService andQuestionsService;

    @GetMapping("/get-answer-accountant")
    public String getViewQuestAndAnswer(HttpSession request, ModelMap model) {
        Accountant logueado= (Accountant) request.getAttribute("sessionuser");
        model.put("logueado",logueado);

        List<Developer> developerList = developerService.searchDeveloperByAccountantId(logueado.getId());
        model.put("developerList",developerList);
        return "preguntasyrespuestas";

    }

    @PostMapping("/create-answer")
    public String createAnswer(@RequestParam String respuesta,@RequestParam Long id, HttpSession request) {
        Accountant logueado= (Accountant) request.getAttribute("sessionuser");
        System.out.println(id+" AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        AnswerAndQuestion quest = andQuestionsService.createAnswer(respuesta, id);
        List<AnswerAndQuestion> listaPregunta = new ArrayList<>();
        listaPregunta.add(quest);
        accountantService.setAnswer(listaPregunta,logueado.getId());
        return "redirect:/thymeleaf/get-answer-accountant";

    }

    @PostMapping("/create-quest")
    public String createDeveloperQuest(@RequestParam String pregunta, HttpSession request){
        Developer logueado= (Developer) request.getAttribute("sessionuser");
        AnswerAndQuestion question = andQuestionsService.createQuest(pregunta);
        List<AnswerAndQuestion> listaPregunta = new ArrayList<>();
        listaPregunta.add(question);
        developerService.setQuest(listaPregunta,logueado.getId());
        return "redirect:/thymeleaf/get-myaccountant";
    }

    @GetMapping("/select-accountant/{id}")
    public String selectAccountantByAccountantId(@PathVariable Long id, HttpSession request, ModelMap model){
        Developer logueado= (Developer) request.getAttribute("sessionuser");
        if(logueado.getAccountant()==null) {
            Accountant myAccountant = accountantService.searchAccounterById(id);
            developerService.saveAccountant(myAccountant, logueado.getId());
            request.setAttribute("sessionuser", developerService.searchDeveloperById(logueado.getId()));
        return "redirect:/thymeleaf/get-myaccountant";
        }else {
            model.put("logueado", logueado);
            model.put("mensaje", "Ya ha elegido su Contador, si desea cambiarlo contactenos por nuetros canal de comunicación");
        return "redirect:/thymeleaf/get-myaccountant";
        }
    }

    @GetMapping("/get-myaccountant")
    public String getViewmyaccountant(HttpSession request, ModelMap model){
        Developer logueado= (Developer) request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        Accountant myAccountant = developerService.getAccountant(logueado.getId());
        model.put("myAccountant", myAccountant);
        List<String> listaElementos = new ArrayList<>();
        for (AnswerAndQuestion quest : developerService.getAnQ(logueado.getId())) {
            String pregunta = logueado.getNombre() + ": " + quest.getQuest() + " " + quest.getFechaQuest();
            listaElementos.add(pregunta);
            if (quest.getAnswer() != null) {
                String respuesta = myAccountant.getNombre() + ": " + quest.getAnswer() + " " + quest.getFechaAnswer();
                listaElementos.add(respuesta);
            }
        }
        model.put("listaElementos", listaElementos);
        return "myaccountant";
    }

}
