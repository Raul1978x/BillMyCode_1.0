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

    /**
     * Metodo viewAccounters: Redirige a principalaccounter.html
     *
     * @return: principalaccounter
     */
    @GetMapping("/principalaccounters")
    public String viewAccounters() {
        return "principalaccounter";
    }
}
