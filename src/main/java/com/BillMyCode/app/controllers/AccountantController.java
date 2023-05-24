package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IImageRepository;
import com.BillMyCode.app.services.AccountantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class AccountantController {

    @Autowired
    private AccountantService accountantService;

    @Autowired
    private IImageRepository imageRepository;

    @GetMapping("/contadores")
    public ResponseEntity<List<Accountant>> searchAllAccountants() {
        return ResponseEntity.ok(accountantService.searchAllAccountants());
    }

    @GetMapping("/contador/{id}")
    public ResponseEntity<Accountant> searchAccountantById(@PathVariable Long id) {
        Accountant accountant = accountantService.searchAccountantById(id);
        if (accountant != null) {
            return ResponseEntity.ok(accountant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create-accountants")
    public String createAccountant(
            @RequestParam MultipartFile archivo,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestParam String nacionalidad,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
            @RequestParam String genero,
            @RequestParam String telefono,
            @RequestParam String password,
            @RequestParam(required = false) String especializaciones,
            @RequestParam String matricula,
            @RequestParam Double honorarios,
            ModelMap model) {

        try {
            accountantService.createAccountant(archivo, nombre, apellido, email, nacionalidad, fechaNacimiento, genero, telefono, password, especializaciones, matricula, honorarios);
            model.put("exito", "El contador fue creado exitosamente");
            return "redirect:/thymeleaf/login-bmc";
        } catch (MiException e) {
            model.put("error", e.getMessage());
            return "redirect:/thymeleaf/crear-cuenta-contador";
        } catch (ParseException e) {
            model.put("error", "Error en el formato de fecha de nacimiento");
            return "redirect:/thymeleaf/crear-cuenta-contador";
        }
    }

    @DeleteMapping("/contador/{id}")
    public ResponseEntity<Void> deleteAccountantById(@PathVariable Long id) {
        accountantService.deleteAccountantById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/contador/{id}")
    public String updateAccountant(
            @PathVariable Long id,
            @RequestParam(required = false) MultipartFile archivo,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestParam String nacionalidad,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
            @RequestParam String genero,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String especializaciones,
            @RequestParam String matricula,
            @RequestParam Double honorarios,
            ModelMap model) {

        try {
            accountantService.updateAccountant(id, archivo, nombre, apellido, email, nacionalidad, fechaNacimiento, genero, telefono, especializaciones, matricula, honorarios);
            model.put("exito", "El contador fue actualizado exitosamente");
            return "redirect:/thymeleaf/login-bmc";
        } catch (MiException e) {
            model.put("error", e.getMessage());
            return "redirect:/thymeleaf/editar-cuenta-contador";
        } catch (ParseException e) {
            model.put("error", "Error en el formato de fecha de nacimiento");
            return "redirect:/thymeleaf/editar-cuenta-contador";
        }
    }
}