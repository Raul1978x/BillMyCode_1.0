package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Admin;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IAccountantRepository;
import com.BillMyCode.app.repositories.IAdminRepository;
import com.BillMyCode.app.repositories.IDeveloperRepository;
import com.BillMyCode.app.services.AccountantService;
import com.BillMyCode.app.services.AdminService;
import com.BillMyCode.app.services.DeveloperService;
import com.BillMyCode.app.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
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

    @GetMapping("/user/baja/{id}")
    public String bajaUser(@PathVariable Long id, ModelMap model) throws MiException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Developer developer = developerService.searchDeveloperById(id);
        if (developer != null) {

            developer.setStatus(false);
            if (roles.contains("ROLE_ADMIN")) {
                return "redirect:/thymeleaf/admin-lista-developer";
            }

        } else {
            Accountant accountant = accountantService.searchAccounterById(id);
            if (accountant != null){

                accountant.setStatus(false);
                if (roles.contains("ROLE_ADMIN")) {
                    return "redirect:/thymeleaf/admin-lista-accountant";
                }

            }else {
                Admin admin = adminService.searchAdminById(id);
                if (admin != null){

                    admin.setStatus(false);
                    if (roles.contains("ROLE_ADMIN")) {
                        return "redirect:/thymeleaf/admin-lista-admin";
                    }

                }else {

                    model.put("error","Error al dar de baja al usuario");
                    throw new UsernameNotFoundException("usuario no encontrado con el correo electronico: ");

                }
            }
        }
        return "index";
    }
}

