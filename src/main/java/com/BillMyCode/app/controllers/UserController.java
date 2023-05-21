package com.BillMyCode.app.controllers;

import com.BillMyCode.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Método formDeveloper() retorna el HTML del formulario de carga de Developers
     *
     * @return String formDev.html
     */
    @GetMapping("")
    public String index() {
        return "index";
    }

    /*@GetMapping("/login")
    public String login() {
        return "login.html";
    }*/

    @GetMapping("/select-user")
    public String selectUser() {
        return "crear-cuenta-user2.html";
    }

    @GetMapping("/developer")
    public String developer() {
        return "crear-cuenta-desarrollador.html";
    }

    @GetMapping("/crear-cuenta-contador")
    public String createAccounter() {
        return "crear-cuenta-contador";
    }

    @GetMapping("/principaldevelopers")
    public String viewDevelopers() {
        return "principaldevelopers";
    }

    @GetMapping("/principalaccounters")
    public String viewAccounters() {
        return "principalaccounter";
    }
}
