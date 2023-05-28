package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.entities.User;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.services.AccountantService;
import com.BillMyCode.app.services.CommentService;
import com.BillMyCode.app.services.DeveloperService;
import com.BillMyCode.app.services.ImageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/thymeleaf")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private AccountantService accountantService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/principal-developers")
    public String getViewCreateDeveloper(HttpSession request, ModelMap model) {
        Developer logueado= (Developer) request.getAttribute("sessionuser");
        model.put("developer",logueado);
        return "principaldevelopers";
    }

    @GetMapping("/monotributo")
    public String getViewMonotributo(HttpSession request, ModelMap model) {
        Developer logueado= (Developer) request.getAttribute("sessionuser");
        model.put("developer",logueado);
        return "monotributo";
    }

    @GetMapping("/faq")
    public String getViewFaq(HttpSession request, ModelMap model) {
        Developer logueado= (Developer) request.getAttribute("sessionuser");
        model.put("developer",logueado);
        return "faq";
    }
    @GetMapping("/normativa-impuestos")
    public String getViewNormativaImpuestos(HttpSession request, ModelMap model) {
        Developer logueado= (Developer) request.getAttribute("sessionuser");
        model.put("developer",logueado);
        return "normativa-impuestos";
    }
    @GetMapping("/card-accountant")
    public String callCardAccountant(HttpSession request, ModelMap model) {
        Developer logueado= (Developer) request.getAttribute("sessionuser");
        model.put("developer",logueado);
        System.out.println(logueado.getNombre()+"AAAAAAAAAAAAAAAAAAAAAAAAAA");
        List<Accountant> accountants = accountantService.searchAllAccounters();
        model.put("accountants", accountants);
        return "contadorescard";
    }
    @GetMapping("/login-bmc")
    public String login() {
        return "login.html";
    }

    /**
     * Metodo registrarDeveloper: Crea un nuevo developer
     *
     * @param archivo
     * @param nombre
     * @param apellido
     * @param email
     * @param nacionalidad
     * @param fechaNacimiento
     * @param password
     * @param genero
     * @param telefono
     * @param salario
     * @param seniority
     * @param especialidad
     * @param descripcion
     * @param comentario
     * @throws: MiException
     * @throws: ParseException
     */
    @PostMapping("/create-developers")
    public String registrarDeveloper(@RequestParam MultipartFile archivo,
                                     @RequestParam String nombre,
                                     @RequestParam String apellido,
                                     @RequestParam String email,
                                     @RequestParam String nacionalidad,
                                     @RequestParam("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                     @RequestParam String password,
                                     @RequestParam String newpassword,
                                     @RequestParam String genero,
                                     @RequestParam String telefono,
                                     @RequestParam Double salario,
                                     @RequestParam String seniority,
                                     @RequestParam String especialidad,
                                     @RequestParam(required = false) String descripcion,
                                     @RequestParam(required = false) String comentario,
                                     ModelMap model
    ) throws MiException, ParseException {
        try {

            Comment comment = commentService.createComment(comentario);

            developerService.createDeveloper(archivo, nombre, apellido, email, nacionalidad, fechaNacimiento, password, newpassword, genero, telefono, salario, seniority, especialidad, descripcion, comment);
            model.put("exito", "El Developer fue creado exitosamente");
            System.out.println(model);
            return "login.html";
        } catch (MiException e) {
            model.put("error", e.getMessage());
            return "crear-cuenta-desarrollador.html";
        }
    }

    /**
     * Método getDevelopersBySeniority(seniority) retorna el o los Developer/s segun el grado de seniority buscado y
     * luego con el String devuelto linkea con el HTML especificado
     *
     * @param seniority
     * @return String "developers.html"
     */
    @GetMapping("/developer/{seniority}")
    public String getDevelopersBySeniority(@PathVariable String seniority) {
        developerService.getDevelopersBySeniority(seniority);
        return "developers"; //vista de la lista de developer donde se cumple la query
    }

    /**
     * Metodo listaDevelopers: Devuelve la lista de todos los Developers
     *
     * @return: ResponseEntity<List < Developer>>
     */

    @GetMapping("/developers/delete/{id}")
    public String deleteDeveloper(@PathVariable Long id) {
        developerService.deleteDeveloperById(id);
        return "redirect:/thymeleaf/lista-developers";
    }

    @GetMapping("/developers/edit/{id}")
    public String editDeveloper(@PathVariable Long id, ModelMap model) {
        Developer result = developerService.searchDeveloperById(id);
        model.put("developer", result);
        return "editar-cuenta-desarrollador";
    }

    @PostMapping("/updateDeveloper/{id}")
    public String updateDeveloper(@PathVariable Long id,
                                  @RequestParam(required = false) MultipartFile archivo,
                                  @RequestParam(required = false) String nombre,
                                  @RequestParam(required = false) String apellido,
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) String nacionalidad,
                                  @RequestParam("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                  @RequestParam(required = false) String password,
                                  @RequestParam(required = false) String genero,
                                  @RequestParam(required = false) String telefono,
                                  @RequestParam(required = false) Double salario,
                                  @RequestParam(required = false) String seniority,
                                  @RequestParam(required = false) String especialidad,
                                  @RequestParam(required = false) String descripcion,
                                  @RequestParam(required = false) String comentario,
                                  ModelMap model,
                                  HttpSession request
    ) throws MiException, ParseException {

        try {
            model.put("exito", "el developer fue editado exitosamente");
            developerService.updateDeveloper(id, archivo, nombre, apellido, email, nacionalidad, fechaNacimiento,
                    password, genero, telefono, salario, seniority, especialidad, descripcion, comentario);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            if (roles.contains("ROLE_ADMIN")) {
                return "redirect:/thymeleaf/admin-lista-developer";
            }else {
                return "redirect:/thymeleaf/principal-developers";
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
