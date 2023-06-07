package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.AnswerAndQuestion;
import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.services.AccountantService;
import com.BillMyCode.app.services.CommentService;
import com.BillMyCode.app.services.DeveloperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private AccountantService accountantService;


    @PostMapping("/create-comment/{id}")
    public String crearComentario(@PathVariable Long id, @RequestParam String comentario, HttpSession request){
        Developer logueado= (Developer) request.getAttribute("sessionuser");
        Comment comment = commentService.crearComentario(comentario,logueado.getNombre(),
                accountantService.getAccNombre(id), logueado.getId(), id);
        List<Comment> listaComment = new ArrayList<>();
        listaComment.add(comment);
        developerService.setComentario(listaComment,logueado.getId());
        return "redirect:/thymeleaf/get-myaccountant";
    }

    @PostMapping("/create-respuesta/{id}")
    public String crearRespuesta(@PathVariable Long id, @RequestParam String respuesta, HttpSession request){
        Accountant logueado= (Accountant) request.getAttribute("sessionuser");
        Comment comment = commentService.crearRespuesta(respuesta,id);
        List<Comment> listaComment = new ArrayList<>();
        listaComment.add(comment);
        accountantService.setRespuesta(listaComment, logueado.getId());
        return "redirect:/thymeleaf/comentario-accountant";
    }

    @GetMapping("/comentario-accountant")
    public String getViewCommentAccountant(HttpSession request, ModelMap model){
        Accountant logueado= (Accountant) request.getAttribute("sessionuser");
        model.put("logueado", logueado);

        List<Comment> listaComent = commentService.getAccountantComment(logueado.getId());

        model.put("listaComent", listaComent);
        return "comentario-accounter.html";
    }

}
