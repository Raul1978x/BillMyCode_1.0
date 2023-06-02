package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Answer;
import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.services.AnswerService;
import com.BillMyCode.app.services.CommentService;
import com.BillMyCode.app.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class CommentController {
    @Autowired
    public AnswerService answerService;
    @Autowired
    public CommentService commentService;
    @Autowired
    public DeveloperService developerService;

    @GetMapping("/get-answer")
    public String getAnswer(HttpSession request, ModelMap model) {
        Accountant logueado= (Accountant) request.getAttribute("sessionuser");
        model.put("logueado",logueado);
        return "answer";
    }

    @PostMapping("/create-answer")
    public String createAnswer(@RequestParam String respuesta, Long id, ModelMap model) {
        Developer developer = developerService.searchDeveloperById(id);
        model.put("developer", developer);
       /* Comment comentario = commentService.searchCommentById(developer.getComentario().getId());*/
        Comment comentario = new Comment();
        comentario.setComentario("Como liquido mi sueldo en dolares");
        comentario.setId(1L);
        comentario.setFecha(new Date());
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(comentario.getComentario());
        System.out.println("---------------------------------------------------------------------------------");
        model.put("comentario", comentario);
        Answer answer = answerService.createAnswer(respuesta);
        model.put("answer", answer);
        return "principalaccounter";
    }
}
