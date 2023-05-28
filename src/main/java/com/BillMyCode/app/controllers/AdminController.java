package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Admin;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.services.AccountantService;
import com.BillMyCode.app.services.AdminService;
import com.BillMyCode.app.services.DeveloperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private AccountantService accountantService;

    @GetMapping("/admin-principal")
    public String getViewCreateDeveloper(HttpSession request, ModelMap model) {
        Admin logueado= (Admin) request.getAttribute("sessionuser");
        model.put("admin",logueado);
        return "admin-vistaprincipal";
    }

    /**
     * Metodo registrarAdministrador: Crea un nuevo administador
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
     * @param model
     *
     *
     * @throws: MiException
     */
    @PostMapping("/createAdmin")
    public String registrarAdministrador(@RequestParam MultipartFile archivo,
                                         @RequestParam String nombre,
                                         @RequestParam String apellido,
                                         @RequestParam String email,
                                         @RequestParam String nacionalidad,
                                         @RequestParam ("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                         @RequestParam String password,
                                         @RequestParam String genero,
                                         @RequestParam String telefono,
                                         ModelMap model)
            throws MiException {
        try {
            adminService.createAdmin(nombre, apellido, email, nacionalidad, password, genero, fechaNacimiento,
                    telefono, archivo);
            model.put("exito","El Administrador fue creado exitosamente");
            System.out.println(model);
            return "redirect:/thymeleaf/admin-principal"; // Cambiar a la pagina principal de administrador, ya que lo crea el admin
        } catch (MiException e) {
            model.put("error", "El Administrador no se puedo crear: "+e.getMessage());
            return "redirect:/thymeleaf/admin-principal"; // Tambien que rediriga a la pagina principal de administrador
        }
    }

    @PostMapping("/updateAdmin/{id}")
    public String editarAdministrador(@PathVariable Long id,
                                         @RequestParam MultipartFile archivo,
                                         @RequestParam String nombre,
                                         @RequestParam String apellido,
                                         @RequestParam String email,
                                         @RequestParam String nacionalidad,
                                         @RequestParam ("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                         @RequestParam String password,
                                         @RequestParam String genero,
                                         @RequestParam String telefono,
                                         ModelMap model)
            throws MiException {
        try {
            adminService.updateAdmin(id, nombre, apellido, email, nacionalidad, password, genero, fechaNacimiento,
                    telefono, archivo);
            model.put("exito","El Administrador fue creado exitosamente");
            System.out.println(model);
            return "redirect:/thymeleaf/admin-principal"; // Cambiar a la pagina principal de administrador, ya que lo crea el admin
        } catch (MiException e) {
            model.put("error", "El Administrador no se puedo crear: "+e.getMessage());
            return "redirect:/thymeleaf/admin-principal"; // Tambien que rediriga a la pagina principal de administrador
        }
    }

    @GetMapping("/admin/edit/{id}")
    public String editAdmin(@PathVariable Long id, ModelMap model) {
        Admin result = adminService.searchAdminById(id);
        model.put("admin", result);
        return "admin-editarperfil";
    }

    @GetMapping("/admin-lista-developer")
    public String adminListDevelopers(HttpSession request, ModelMap model){
        List<Developer> developerList = developerService.listDevelopers();
        model.put("developerList", developerList);
        Admin logueado= (Admin) request.getAttribute("sessionuser");
        model.addAttribute("logueado",logueado);
        return "admin-listadevelopers";
    }

    @GetMapping("/admin-lista-accountant")
    public String adminListAccountants(HttpSession request, ModelMap model){
        List<Accountant> accountantList = accountantService.searchAllAccounters();
        model.put("accountantList", accountantList);
        Admin logueado= (Admin) request.getAttribute("sessionuser");
        model.addAttribute("logueado",logueado);
        System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA "+logueado.getEmail());
        return "admin-listaaccountants";
    }

    @GetMapping("/admin-lista-admin")
    public String adminListAdmins(HttpSession request, ModelMap model){
        List<Admin> adminList = adminService.listAdmins();
        model.put("adminList", adminList);
        Admin logueado= (Admin) request.getAttribute("sessionuser");
        model.addAttribute("logueado",logueado);
        return "admin-listaadmins";
    }

}
