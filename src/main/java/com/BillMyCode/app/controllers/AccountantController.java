package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IImageRepository;
import com.BillMyCode.app.services.AccountantService;
import com.BillMyCode.app.services.CommentService;
import com.BillMyCode.app.services.DeveloperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/thymeleaf")
public class AccountantController {

    @Autowired
    private AccountantService accountantService;
    @Autowired
    private DeveloperService developerService;

    @Autowired
    private IImageRepository imageRepository;

    @Autowired
    private CommentService commentService;

    /**
     * Metodo searchAllAccountants: Devuelve la lista de todos los Contadores
     *
     * @return: List<Accountant>
     */
    @GetMapping("/accountants")
    public ResponseEntity<List<Accountant>> searchAllAccountants() {
        return ResponseEntity.ok(accountantService.searchAllAccounters());
    }

    /**
     * Metodo searchAccountantById: Devuelve el Contador según una id
     *
     * @param id
     * @return: Accountant
     */
    @GetMapping("/accountant/{id}")
    public Accountant searchAccountantById(@PathVariable Long id) {
        return accountantService.searchAccounterById(id);
    }



    /**
     * Metodo registrarDeveloper: Crea un nuevo usuario Contador
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
     * @param honorarios
     * @param matricula
     * @param especializacion
     * @param developers
     * @throws: MiException
     * @throws: ParseException
     */
    @PostMapping("/create-accountants")
    public String createAccountant(@RequestParam MultipartFile archivo,
                                   @RequestParam String nombre,
                                   @RequestParam String apellido,
                                   @RequestParam String email,
                                   @RequestParam String nacionalidad,
                                   @RequestParam("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                   @RequestParam String password,
                                   @RequestParam String newpassword,
                                   @RequestParam String genero,
                                   @RequestParam String telefono,
                                   @RequestParam Double honorarios,
                                   @RequestParam String matricula,
                                   @RequestParam(required = false) String especializacion,
                                   @RequestParam(required = false) List<Developer> developers,
                                   ModelMap model
    ) throws MiException, ParseException {

        try {

            accountantService.crearContador(archivo, nombre, apellido, email, nacionalidad, fechaNacimiento,
                    genero, telefono, password, newpassword, especializacion, matricula, honorarios);
            model.put("exito", "El Contador fue creado exitosamente");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            if (roles.contains("ROLE_ADMIN")) {
                return "redirect:/thymeleaf/admin-lista-accountant";
            }else {
                return "login.html";
            }
        } catch (MiException e) {
            model.put("error", e.getMessage());
            return "crear-cuenta-contador.html";
        }

    }

    /**
     * Metodo deleteAccountantById: Borra un Contador según una id
     *
     * @param id
     */
    @DeleteMapping("/accountant/{id}")
    public void deleteAccountantById(@PathVariable Long id) {
        accountantService.deleteAccounterById(id);
    }

    /**
     * Metodo updateDeveloper: Actualiza los datos de un usuario Contador
     *
     * @param id
     * @param archivo
     * @param nombre
     * @param apellido
     * @param email
     * @param nacionalidad
     * @param fechaNacimiento
     * @param password
     * @param genero
     * @param telefono
     * @param honorarios
     * @param matricula
     * @param especializacion
     * @param developers
     * @param model
     * @throws: MiException
     * @throws: ParseException
     */
    @PostMapping("updateAccountant/{id}")
    public String updateAccountant(@PathVariable Long id,
                                   @RequestParam(required = false) MultipartFile archivo,
                                   @RequestParam(required = false) String nombre,
                                   @RequestParam(required = false) String apellido,
                                   @RequestParam(required = false) String email,
                                   @RequestParam(required = false) String nacionalidad,
                                   @RequestParam("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                   @RequestParam(required = false) String password,
                                   @RequestParam(required = false) String newpassword,
                                   @RequestParam(required = false) String genero,
                                   @RequestParam(required = false) String telefono,
                                   @RequestParam Double honorarios,
                                   @RequestParam String matricula,
                                   @RequestParam(required = false) String especializacion,
                                   @RequestParam(required = false) List<Developer> developers,
                                   ModelMap model
    ) throws MiException {

        try {
            accountantService.updateAccountant(id, archivo, nombre, apellido, email, nacionalidad, fechaNacimiento,
                    genero, telefono, password, newpassword, especializacion, matricula, honorarios);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            if (roles.contains("ROLE_ADMIN")) {
                return "redirect:/thymeleaf/admin-lista-accountant";
            } else {
                return "redirect:/thymeleaf/principal-accountant";
            }
        } catch (MiException e) {
            model.put("error", e.getMessage());
            Accountant logueado = accountantService.searchAccounterById(id);
            model.put("logueado", logueado);
            return "editar-cuenta-contador";
        }

    }

    @GetMapping("/principal-accountant")
    public String viewAccounters(HttpSession request, ModelMap model) {
        Accountant logueado = (Accountant) request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        return "principalaccounter";
    }

    @GetMapping("/lista-developers")
    public String listDeveloper(HttpSession request, ModelMap model) {
        List<Developer> developerList = developerService.listDevelopers();
        model.put("developerList", developerList);
        Accountant logueado = (Accountant) request.getAttribute("sessionuser");
        model.addAttribute("logueado", logueado);
        return "listadedevelopers";
    }

    @GetMapping("/accountant/edit/{id}")
    public String editAccountant(@PathVariable Long id, ModelMap model) {
        Accountant logueado = accountantService.searchAccounterById(id);
        model.put("logueado", logueado);
        model.put("dir", "admin-lista-developer");
        return "editar-cuenta-contador";
    }

    @GetMapping("accountant/normativa-impuestos")
    public String getViewNormativaImpuestos(HttpSession request, ModelMap model) {
        Accountant logueado = (Accountant) request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        return "normativa-impuestos";
    }

    @GetMapping("accountant/monotributo")
    public String getViewMonotributo(HttpSession request, ModelMap model) {
        Accountant logueado = (Accountant) request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        return "monotributo";

    }


    @GetMapping("/preguntasyrespuestas")
    public String viewAnswersAndQuest(HttpSession request, ModelMap model) {
        Accountant logueado = (Accountant) request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        return "preguntasyrespuestas";
    }


    @GetMapping("/accountant/delete/{id}")
    public String deleteAccountant(@PathVariable Long id) {
        accountantService.deleteAccounterById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/thymeleaf/admin-lista-accountant";
        } else {
            return "redirect:/thymeleaf/lista-developers";
        }

    }



}


 
