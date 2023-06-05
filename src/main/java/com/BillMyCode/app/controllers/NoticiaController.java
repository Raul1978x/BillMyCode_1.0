package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.entities.Noticia;
import com.BillMyCode.app.services.NoticiaService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/thymeleaf")
@Controller
public class NoticiaController {
    @Autowired
    private NoticiaService noticiaServicio;

    @GetMapping("/Noticia")
    public String mostrarNoticia(ModelMap model, HttpSession request) {
        Developer logueado = (Developer) request.getAttribute("sessionuser");
        model.put("logueado", logueado);

        List<Noticia> noticia = noticiaServicio.listarNoticia();
        model.addAttribute("noticia", noticia);

        return "Noticia.html";
    }
@GetMapping("/crear-noticia")
public String creaNoticia(ModelMap model, HttpSession request){
    Developer logueado = (Developer) request.getAttribute("sessionuser");
    model.put("logueado", logueado);

    return "crear-noticia.html";
}
    @PostMapping("/crear-noticia")
    public String CrearNoticia(@RequestParam (required = false)MultipartFile archivo,
                               @RequestParam String titulo,
                               @RequestParam String contenido, ModelMap modelo) {


        LocalDateTime horaSubida = LocalDateTime.now();
        noticiaServicio.crearNoticia(archivo,titulo, contenido, horaSubida);

        modelo.put("Exito", "La noticia fue creada con éxito");
        return "redirect:/thymeleaf/Noticia";
    }
    @GetMapping("/mostrar-noticia/{id}")
    public String verNoticia(@PathVariable("id") Long id, ModelMap modelo) {

    Noticia noticia = noticiaServicio.searchNoticiaId(id);

        modelo.addAttribute("noticia", noticia);

        return "mostrar-noticia.html";
    }

}