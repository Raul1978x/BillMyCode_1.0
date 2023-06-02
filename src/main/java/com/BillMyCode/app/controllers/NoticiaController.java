package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.entities.Noticia;
import com.BillMyCode.app.services.NoticiaService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

        List<Noticia> noticia = noticiaServicio.ListarNoticia();
        model.addAttribute("noticia", noticia);

        return "Noticia.html";
    }

    @GetMapping("/crear-noticia{id}")
    public String CrearNoticia(@PathVariable Long id,
                               @RequestParam ("titulo")String titulo,
                               @RequestParam ("contenido")String contenido ,ModelMap modelo) {

//        Path directorioImagenes = Paths.get("src//main//resources//static/images");
//        String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

//        byte[] bytesImg = imagen.getBytes();
//        Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
//        Files.write(rutaCompleta, bytesImg);

        noticiaServicio.CrearNoticia( id, titulo, contenido);
        modelo.put("Exito", "La noticia fue creada con Ã©xito");
         return "crear-noticia.html";
    }
}