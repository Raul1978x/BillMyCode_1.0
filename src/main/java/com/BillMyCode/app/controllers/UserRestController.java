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
@RequestMapping("")
public class UserRestController {

    @Autowired
    private UserService userService;

    /**
     * Metodo listUser: Trae los usuarios de la base de datos, pero solo para lectura.
     *
     * @return Lista de Usuarios (User)
     */
    @GetMapping("api/users")
    public ResponseEntity<List<User>> listUser() {
        return ResponseEntity.ok(userService.searchUsers());
    }

    /**
     * Metodo searchUserById: Trae al usuario que coincida con la ID pasada por parametro, pero solo para lectura.
     *
     * @param id
     *
     * @return: User
     */
    @GetMapping("api/users/{id}")
    public ResponseEntity<User> searchUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.searchUserById(id));
    }

    /**
     * Metodo createUser: Crea un usario nuevo
     *
     * @param archivo
     * @param nombre
     * @param apellido
     * @param email
     * @param password
     * @param fechaNacStr
     * @param telefono
     * @param genero
     * @param nacionalidad
     * @param model
     *
     * @throws: MiException
     */
    @PostMapping("api/users")
    public void createUser(@RequestParam MultipartFile archivo,
                                     @RequestParam String nombre,
                                     @RequestParam String apellido,
                                     @RequestParam String email,
                                     @RequestParam String password,
                                     @RequestParam("fechaNac") String fechaNacStr,
                                     @RequestParam String telefono,
                                     @RequestParam String genero,
                                     @RequestParam String nacionalidad,
                                     ModelMap model)
            throws MiException {

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNac = format.parse(fechaNacStr);
            userService.createUser(archivo,nombre,apellido,email,genero,nacionalidad,password,telefono,fechaNac);
            model.put("exito", "el developer fue creado exitosamente");

        } catch (ParseException e) {
            model.put("error", "La fecha de nacimiento es inválida.");
        }
    }

    /**
     * Metodo deleteUserById: Elimina al usuario que coincida con la ID
     *
     * @param id
     */
    @DeleteMapping("api/users/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteById(id);
    }
}
