package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.exceptions.MiException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class AdminController {
     @PostMapping("/createAdmin")
    public ResponseEntity<Developer> registerPart1Developer(@RequestParam MultipartFile archivo,
                                                            @RequestParam String nombre,
                                                            @RequestParam String apellido,
                                                            @RequestParam String email,
                                                            @RequestParam String nacionalidad,
                                                            @RequestParam String fechaNacStr,
                                                            @RequestParam String password,
                                                            @RequestParam String genero,
                                                            @RequestParam String telefono,
                                                            ModelMap model)
            throws MiException {

      /*  try {
            return ResponseEntity.ok(developerService.createPart1Developer(archivo, nombre, apellido, email, nacionalidad, fechaNacStr, password,
                    genero, telefono));

        } catch (MiException e) {
            model.put("error", e.getMessage());
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }*/
         return null;
    }
}
