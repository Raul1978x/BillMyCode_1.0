package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.User;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.searchUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.searchUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<String> createUser(@RequestParam MultipartFile archivo,
                                             @RequestParam String nombre,
                                             @RequestParam String apellido,
                                             @RequestParam String email,
                                             @RequestParam String password,
                                             @RequestParam("fechaNac") String fechaNacStr,
                                             @RequestParam String telefono,
                                             @RequestParam String genero,
                                             @RequestParam String nacionalidad)
            throws MiException {

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNac = format.parse(fechaNacStr);
            userService.createUser(archivo, nombre, apellido, email, genero, nacionalidad, password, telefono, fechaNac);
            return ResponseEntity.ok("El usuario ha sido creado exitosamente.");
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("La fecha de nacimiento es inválida.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok("El usuario ha sido eliminado exitosamente.");
    }
}