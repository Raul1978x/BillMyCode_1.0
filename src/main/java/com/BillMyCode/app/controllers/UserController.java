package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Admin;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.services.AccountantService;
import com.BillMyCode.app.services.AdminService;
import com.BillMyCode.app.services.DeveloperService;
import com.BillMyCode.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/thymeleaf")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountantService accountantService;

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
}

