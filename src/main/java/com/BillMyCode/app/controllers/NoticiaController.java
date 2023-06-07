package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Admin;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.entities.Noticia;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.services.NoticiaService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/thymeleaf")
@Controller
public class NoticiaController {
    @Autowired
    private NoticiaService noticiaServicio;

    @GetMapping("/Noticia")
    public String mostrarNoticia(ModelMap model, HttpSession request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        if (roles.contains("ROLE_DEV")) {
            Developer logueado = (Developer) request.getAttribute("sessionuser");
            model.put("logueado", logueado);
        }else{
            Accountant logueado = (Accountant) request.getAttribute("sessionuser");
            model.put("logueado", logueado);
        }

        List<Noticia> noticia = noticiaServicio.listarNoticia();
        model.addAttribute("noticia", noticia);

        return "Noticia.html";
    }

    @GetMapping("/crear-noticia")
    public String creaNoticia(ModelMap modelo, HttpSession request) {
        Admin logueado = (Admin) request.getAttribute("sessionuser");
        modelo.put("logueado", logueado);

        return "crear-noticia.html";
    }

    @PostMapping("/crear-noticia")
    public String CrearNoticia(@RequestParam(required = false) MultipartFile archivo,
                               @RequestParam String titulo,
                               @RequestParam String contenido, ModelMap modelo) {


        LocalDateTime horaSubida = LocalDateTime.now();
        noticiaServicio.crearNoticia(archivo, titulo, contenido, horaSubida);

        modelo.put("Exito", "La noticia fue creada con éxito");
        return "redirect:/thymeleaf/Noticia";
    }

    @GetMapping("/mostrar-noticia/{id}")
    public String verNoticia(@PathVariable("id") Long id, ModelMap modelo, HttpSession request) {
        Developer logueado = (Developer) request.getAttribute("sessionuser");
        modelo.put("logueado", logueado);
        Noticia noticia = noticiaServicio.searchNoticiaId(id);

        modelo.addAttribute("noticia", noticia);

        return "mostrar-noticia.html";
    }

    @GetMapping("/admin-lista-noticias")
    public String listarNoticia(ModelMap model, HttpSession request) {
        List<Noticia> listanoticia = noticiaServicio.listarNoticia();
        Admin logueado = (Admin) request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        model.put("listanoticia", listanoticia);


        return "admin-lista-noticias.html";
    }

    @GetMapping("/admin-eliminar-noticias/{id}")
    public String borrarNoticia(@PathVariable Long id, ModelMap model, HttpSession request) {
        noticiaServicio.eliminarNoticia(id);
        Admin logueado = (Admin) request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        return "redirect:/thymeleaf/admin-lista-noticias";
    }

    @GetMapping("/noticia/edit/{id}")
    public String editNoticia(@PathVariable Long id, ModelMap model, HttpSession request) {
        Noticia noticia = noticiaServicio.searchNoticiaId(id);
        Admin logueado = (Admin) request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        model.put("noticia", noticia);
        model.put("dir", "admin-lista-noticias");
        return "editar-noticia";
    }

    @PostMapping("/updateNoticia/{id}")
    public String updateNoticia(@PathVariable Long id,
                                @RequestParam(required = false) MultipartFile archivo,
                                @RequestParam(required = false) String titulo,
                                @RequestParam(required = false) String contenido,
                                HttpSession request,
                                ModelMap model
    ) throws MiException {
        try {
        Admin logueado = (Admin) request.getAttribute("sessionuser");
        model.put("logueado", logueado);
            noticiaServicio.editNoticia(id, archivo, titulo, contenido);
            model.put("exito", "la noticia fue editada exitosamente");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            if (roles.contains("ROLE_ADMIN")) {
                return "redirect:/thymeleaf/admin-lista-noticias";
            } else {
                return "redirect:/thymeleaf/admin-lista-noticias";
            }
        } catch (ParseException e) {
            model.put("error", e.getMessage());
            Noticia noticia = noticiaServicio.searchNoticiaId(id);
            model.put("noticia", noticia);
            return "editar-noticia";

        }


    }
}