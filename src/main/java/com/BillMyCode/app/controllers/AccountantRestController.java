package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IImageRepository;
import com.BillMyCode.app.services.AccountantService;
import com.BillMyCode.app.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountantRestController {

    @Autowired
    private AccountantService accountantService;

    @Autowired
    private IImageRepository imageRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/accountants")
    public ResponseEntity<List<Accountant>> searchAllAccountants() {
        return ResponseEntity.ok(accountantService.searchAllAccounters());
    }

    @GetMapping("/accountant/{id}")
    public Accountant searchAccountantById(@PathVariable Long id) {
        return accountantService.searchAccounterById(id);
    }

    @PostMapping("/accountants")
    public void registrarDeveloper(@RequestParam MultipartFile archivo,
                                   @RequestParam String nombre,
                                   @RequestParam String apellido,
                                   @RequestParam String email,
                                   @RequestParam String nacionalidad,
                                   @RequestParam("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                   @RequestParam String password,
                                   @RequestParam String genero,
                                   @RequestParam String telefono,
                                   @RequestParam Double honorarios,
                                   @RequestParam String matricula,
                                   @RequestParam(required = false) String especializaciones,
                                   @RequestParam(required = false) List<Developer> developers
    ) throws MiException, ParseException {


        accountantService.crearContador(archivo, nombre, apellido, email, nacionalidad, fechaNacimiento,
                genero, telefono, password, especializaciones, matricula, honorarios);
    }

    @DeleteMapping("/accountant/{id}")
    public void deleteAccountantById(@PathVariable Long id) {
        accountantService.deleteAccounterById(id);
    }

    @PutMapping("/accountant/{id}")
    public void updateDeveloper(@PathVariable Long id,
                                @RequestParam(required = false) MultipartFile archivo,
                                @RequestParam(required = false) String nombre,
                                @RequestParam(required = false) String apellido,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String nacionalidad,
                                @RequestParam("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                @RequestParam(required = false) String password,
                                @RequestParam(required = false) String genero,
                                @RequestParam(required = false) String telefono,
                                @RequestParam Double honorarios,
                                @RequestParam String matricula,
                                @RequestParam(required = false) String especializaciones,
                                @RequestParam(required = false) List<Developer> developers,
                                ModelMap model
    ) throws MiException, ParseException {

       /* try {*/
            model.put("exito", "el developer fue creado exitosamente");

           /* Date fechaNacimiento = null; // Inicializar la variable fechaNacimiento como null

            if (fechaNacStr != null && !fechaNacStr.isEmpty()) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                fechaNacimiento = format.parse(fechaNacStr);
            }*/

            accountantService.crearContador(archivo, nombre, apellido, email, nacionalidad, fechaNacimiento,
                    genero, telefono, password, especializaciones, matricula, honorarios);
      /*  } catch (ParseException e) {
            throw new RuntimeException(e);
        }*/
    }

}