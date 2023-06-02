package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Admin;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.entities.User;
import com.BillMyCode.app.enumerations.Rol;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.services.AccountantService;
import com.BillMyCode.app.services.AdminService;
import com.BillMyCode.app.services.DeveloperService;
import com.BillMyCode.app.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/thymeleaf")
public class UserController {

    @Autowired
    private AccountantService accountantService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private AdminService adminService;

    /**
     * Metodo index(): Redirige a index.html
     *
     * @return: index
     */
    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/resetpass")
    public String resetpass() {
        return "resetpass";
    }

    /**
     * Metodo login(): Redirige a login.html
     *
     * @return: login.html
     */
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    /**
     * Metodo selectUser: Redirige a crear-cuenta-user2.html
     *
     * @return: crear-cuenta-user2.html
     */
    @GetMapping("/select-user")
    public String selectUser() {
        return "crear-cuenta-user2.html";
    }

    /**
     * Metodo developer: Redirige a crear-cuenta-desarrollador.html
     *
     * @return: crear-cuenta-desarrollador.html
     */
    @GetMapping("/developer")
    public String developer() {
        return "crear-cuenta-desarrollador.html";
    }

    /**
     * Metodo createAccounter: Redirige a crear-cuenta-contador.html
     *
     * @return: crear-cuenta-contador
     */
    @GetMapping("/crear-cuenta-contador")
    public String createAccounter() {
        return "crear-cuenta-contador";
    }

    /**
     * Metodo viewDevelopers: Redirige a principaldevelopers.html
     *
     * @return: principaldevelopers
     */
    @GetMapping("/principaldevelopers")
    public String viewDevelopers() {
        return "principaldevelopers";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable Long id, ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        if (roles.contains("ROLE_ADMIN")){
            Admin logueado = adminService.searchAdminById(id);
            model.put("logueado", logueado);
            return "admin-editarperfil";
        }else if (roles.contains("ROLE_DEV")){
            Developer logueado = developerService.searchDeveloperById(id);
            model.put("logueado", logueado);
            return "editar-cuenta-desarrollador";
        }else if (roles.contains("ROLE_ACCOUNTANT")) {
            Accountant logueado = accountantService.searchAccounterById(id);
            model.put("logueado", logueado);
            return "editar-cuenta-contador";
        }else{
            return "redirect:/thymeleaf/accesoD";
        }

    }

    @GetMapping ("/user/baja/{id}/{rol}/{dir}")
    public String confirmarBaja(@PathVariable Long id,@PathVariable Rol rol ,@PathVariable String dir, ModelMap model){
        model.addAttribute("id",id);
        model.addAttribute("rol",rol);
        model.addAttribute("dir",dir);
        return "confirmar-baja";
    }

    @PostMapping ("/user/confirmarbaja")
    public String confirmBaja(@RequestParam Long id, @RequestParam Rol rol, @RequestParam String dir,
                              @RequestParam String password, ModelMap model, HttpSession request) throws MiException {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User usuario = (User) request.getAttribute("sessionuser");

        switch (rol.toString()) {
            case "DEV":
                if (passwordEncoder.matches(password, usuario.getPassword())) {
                    developerService.bajaDeveloper(id);
                    model.put("exito", "Usuario dado de baja exitosamente");
                } else {
                    model.put("error", "Contraseña incorrecta");
                    return "redirect:/thymeleaf/"+dir;
                }
                break;
            case "ACCOUNTANT":
                if (passwordEncoder.matches(password, usuario.getPassword())) {
                    accountantService.bajaAccountant(id);
                    model.put("exito", "Usuario dado de baja exitosamente");
                } else {
                    model.put("error", "Contraseña incorrecta");
                    return "redirect:/thymeleaf/"+dir;
                }
                break;
        }
        if (request != null) {
            request.invalidate();
        }
        return "index";
    }
}

