package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Admin;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.enumerations.Rol;
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

import java.text.ParseException;
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
        model.put("logueado",logueado);
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
    @PostMapping("/create-Admin")
    public String registrarAdministrador(@RequestParam MultipartFile archivo,
                                         @RequestParam String nombre,
                                         @RequestParam String apellido,
                                         @RequestParam String email,
                                         @RequestParam String nacionalidad,
                                         @RequestParam ("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                         @RequestParam String password,
                                         @RequestParam String newpassword,
                                         @RequestParam String genero,
                                         @RequestParam String telefono,
                                         ModelMap model)
            throws MiException, ParseException {
        try {
            adminService.createAdmin(nombre, apellido, email, nacionalidad, password, newpassword, genero, fechaNacimiento,
                    telefono, archivo);
            model.put("exito","El Administrador fue creado exitosamente");

            return "redirect:/thymeleaf/admin-principal"; // Cambiar a la pagina principal de administrador, ya que lo crea el admin
        } catch (MiException e) {
            model.put("error", e.getMessage());
            return "crear-cuenta-administrador.html";
        }
    }

    @PostMapping("/updateAdmin/{id}")
    public String editarAdministrador(@PathVariable Long id,
                                      @RequestParam String dir,
                                         @RequestParam (required = false) MultipartFile archivo,
                                         @RequestParam (required = false) String nombre,
                                         @RequestParam (required = false) String apellido,
                                         @RequestParam (required = false) String email,
                                         @RequestParam (required = false) String nacionalidad,
                                         @RequestParam ("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                         @RequestParam (required = false) String password,
                                         @RequestParam (required = false) String newpassword,
                                         @RequestParam (required = false) String genero,
                                         @RequestParam (required = false) String telefono,
                                         ModelMap model)
            throws MiException {
        try {
            adminService.updateAdmin(id, nombre, apellido, email, nacionalidad, password, newpassword, genero, fechaNacimiento,
                    telefono, archivo);
            model.put("exito","El Administrador fue actualizado exitosamente");

            System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAA "+ dir);

            return "redirect:/thymeleaf/"+dir;

        } catch (MiException e) {
            Admin logueado = adminService.searchAdminById(id);
            model.addAttribute("logueado", logueado);
            model.put("error", e.getMessage());
            return "admin-editarperfil.html";

        }
    }

    @GetMapping("/admin/edit/{id}")
    public String editAdmin(@PathVariable Long id,ModelMap model) {
        Admin logueado = adminService.searchAdminById(id);
        model.put("logueado", logueado);
        model.put("dir","admin-lista-admin");
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

    @GetMapping("/admin/delete/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdminById(id);
        return "redirect:/thymeleaf/admin-lista-admin";
    }

    @GetMapping("/admin/baja/{id}/{rol}")
    public String bajaUsuarioAdmin(@PathVariable Long id, @PathVariable Rol rol, ModelMap model){

        switch (rol.toString()) {
            case "DEV":
                    developerService.bajaDeveloper(id);
                    model.put("exito", "Usuario dado de baja exitosamente");
                    return "redirect:/thymeleaf/admin-lista-developer";
            case "ACCOUNTANT":
                    accountantService.bajaAccountant(id);
                    model.put("exito", "Usuario dado de baja exitosamente");
                    return "redirect:/thymeleaf/admin-lista-accountant";
            case "ADMIN":
                    adminService.bajaAdmin(id);
                    model.put("exito", "Usuario dado de baja exitosamente");
                    return "redirect:/thymeleaf/admin-lista-admin";
            default:
                model.put("error"," Ocurrio un error, no se ha podido dar de baja al usuario");
                return "redirect:/thymeleaf/admin-principal";
        }
    }

    @GetMapping("/admin/alta/{id}/{rol}")
    public String altaUsuarioAdmin(@PathVariable Long id, @PathVariable Rol rol, ModelMap model){

        switch (rol.toString()) {
            case "DEV":
                developerService.altaDeveloper(id);
                model.put("exito", "Usuario dado de baja exitosamente");
                return "redirect:/thymeleaf/admin-lista-developer";
            case "ACCOUNTANT":
                accountantService.altaAccountant(id);
                model.put("exito", "Usuario dado de baja exitosamente");
                return "redirect:/thymeleaf/admin-lista-accountant";
            case "ADMIN":
                adminService.altaAdmin(id);
                model.put("exito", "Usuario dado de baja exitosamente");
                return "redirect:/thymeleaf/admin-lista-admin";
            default:
                model.put("error"," Ocurrio un error, no se ha podido dar de baja al usuario");
                return "redirect:/thymeleaf/admin-principal";
        }
    }
    @GetMapping("/admin-actualizar")
    public String createAdmin(HttpSession request, ModelMap model) {
        Admin logueado= (Admin) request.getAttribute("sessionuser");
        model.put("logueado",logueado);
        return "admin-vistaprincipal";
    }

}
