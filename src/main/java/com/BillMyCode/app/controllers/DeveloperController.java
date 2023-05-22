package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.entities.Company;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.entities.User;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.services.CommentService;
import com.BillMyCode.app.services.DeveloperService;
import com.BillMyCode.app.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/principal-developers")
    public String getViewCreateDeveloper() {
        return "principaldevelopers.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    @PostMapping("/create-developers")
    public String registrarDeveloper(@RequestParam MultipartFile archivo,
                                     @RequestParam String nombre,
                                     @RequestParam String apellido,
                                     @RequestParam String email,
                                     @RequestParam String nacionalidad,
                                     @RequestParam String fechaNacStr,
                                     @RequestParam String password,
                                     @RequestParam String genero,
                                     @RequestParam String telefono,
                                     @RequestParam Double salario,
                                     @RequestParam String seniority,
                                     @RequestParam String especialidad,
                                     @RequestParam String descripcion,
                                     @RequestParam(required = false) String comentario,
                                     ModelMap model
    ) throws MiException, ParseException {
        try {

            Comment comment = commentService.createComment(comentario);

            developerService.createDeveloper(archivo, nombre, apellido, email, nacionalidad, fechaNacStr,
                    password, genero, telefono, salario, seniority, especialidad, descripcion, comment);
            model.put("exito", "el developer fue creado exitosamente");

        } catch (MiException e) {
            model.put("error", e.getMessage());
            return "redirect:/";
        }
        return "redirect:/thymeleaf/principal-developers";
    }
    /**
     * Método getDevelopersBySeniority(seniority) retorna el o los Developer/s segun el grado de seniority buscado y
     * luego con el String devuelto linkea con el HTML especificado
     * @param seniority
     * @return String "developers.html"
     */
    @GetMapping("/developer/{seniority}")
    public String getDevelopersBySeniority(@PathVariable String seniority) {
                developerService.getDevelopersBySeniority(seniority);
        return "developers"; //vista de la lista de developer donde se cumple la query
    }

    @GetMapping("/developerEmail/{email}")
    public Developer getDevelopersByEmail(@PathVariable String email) {
        return developerService.seachDeveloperByEmail(email);
    }
}
