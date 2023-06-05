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

    /**
     * Metodo searchAllAccountants: Devuelve la lista de todos los Contadores
     *
     * @return: List<Accountant>
     */
    @GetMapping("/accountants")
    public ResponseEntity<List<Accountant>> searchAllAccountants() {
        return ResponseEntity.ok(accountantService.searchAllAccounters());
    }

    /**
     * Metodo searchAccountantById: Devuelve el Contador según una id
     *
     * @param id
     *
     * @return: Accountant
     */
    @GetMapping("/accountant/{id}")
    public Accountant searchAccountantById(@PathVariable Long id) {
        return accountantService.searchAccounterById(id);
    }

    /**
     * Metodo registrarDeveloper: Crea un nuevo usuario Contador
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
     * @param honorarios
     * @param matricula
     * @param especializaciones
     * @param developers
     *
     * @throws: MiException
     * @throws: ParseException
     */
    @PostMapping("/accountants")
    public void createAccountant(@RequestParam MultipartFile archivo,
                                   @RequestParam String nombre,
                                   @RequestParam String apellido,
                                   @RequestParam String email,
                                   @RequestParam String nacionalidad,
                                   @RequestParam("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                   @RequestParam String password,
                                   @RequestParam String newpassword,
                                   @RequestParam String genero,
                                   @RequestParam String telefono,
                                   @RequestParam Double honorarios,
                                   @RequestParam String matricula,
                                   @RequestParam(required = false) String especializaciones,
                                   @RequestParam(required = false) List<Developer> developers
    ) throws MiException, ParseException {


        accountantService.crearContador(archivo, nombre, apellido, email, nacionalidad, fechaNacimiento,
                genero, telefono, password, newpassword, especializaciones, matricula, honorarios);
    }

    /**
     * Metodo deleteAccountantById: Borra un Contador según una id
     *
     * @param id
     */
    @DeleteMapping("/accountant/{id}")
    public void deleteAccountantById(@PathVariable Long id) {
        accountantService.deleteAccounterById(id);
    }

    /**
     * Metodo updateDeveloper: Actualiza los datos de un usuario Contador
     *
     * @param id
     * @param archivo
     * @param nombre
     * @param apellido
     * @param email
     * @param nacionalidad
     * @param fechaNacimiento
     * @param password
     * @param genero
     * @param telefono
     * @param honorarios
     * @param matricula
     * @param especializaciones
     * @param developers
     * @param model
     *
     * @throws: MiException
     * @throws: ParseException
     */
    @PutMapping("/accountant/{id}")
    public void updateAccoutant(@PathVariable Long id,
                                @RequestParam(required = false) MultipartFile archivo,
                                @RequestParam(required = false) String nombre,
                                @RequestParam(required = false) String apellido,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String nacionalidad,
                                @RequestParam("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                @RequestParam(required = false) String password,
                                @RequestParam(required = false) String newpassword,
                                @RequestParam(required = false) String genero,
                                @RequestParam(required = false) String telefono,
                                @RequestParam Double honorarios,
                                @RequestParam String matricula,
                                @RequestParam(required = false) String especializaciones,
                                @RequestParam(required = false) List<Developer> developers,
                                ModelMap model
    ) throws MiException {

        try {
            model.put("exito", "el developer fue creado exitosamente");
            accountantService.crearContador(archivo, nombre, apellido, email, nacionalidad, fechaNacimiento,
                    genero, telefono, password, newpassword, especializaciones, matricula, honorarios);
        } catch (MiException e) {
            model.put("error", e.getMessage());
            throw new MiException(e.getMessage());
        }
    }



}
