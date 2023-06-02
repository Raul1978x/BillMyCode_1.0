package com.BillMyCode.app.controllers;

import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class AdminRestController {

    @Autowired
    private AdminService adminService;
    @PostMapping("/createAdmin")
    public String registrarAdministrador(@RequestParam MultipartFile archivo,
                                         @RequestParam String nombre,
                                         @RequestParam String apellido,
                                         @RequestParam String email,
                                         @RequestParam String nacionalidad,
                                         @RequestParam ("fechaNacStr") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacStr,
                                         @RequestParam String password,
                                         @RequestParam String newpassword,
                                         @RequestParam String genero,
                                         @RequestParam String telefono,
                                         ModelMap model)
            throws MiException {
        try {
            adminService.createAdmin(nombre, apellido, email, nacionalidad, password, newpassword, genero, fechaNacStr,
                    telefono, archivo);
            model.put("exito","El Administrador fue creado exitosamente");
            System.out.println(model);
            return "redirect:/thymeleaf/admin-principal"; // Cambiar a la pagina principal de administrador, ya que lo crea el admin
        } catch (MiException e) {
            model.put("error", "El Administrador no se puedo crear: "+e.getMessage());
            return "redirect:/thymeleaf/admin-principal"; // Tambien que rediriga a la pagina principal de administrador
        }
    }
}
