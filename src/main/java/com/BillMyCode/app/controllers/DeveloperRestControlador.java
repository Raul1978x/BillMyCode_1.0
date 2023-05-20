package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IDeveloperRepository;
import com.BillMyCode.app.services.CommentService;
import com.BillMyCode.app.services.DeveloperService;
import com.BillMyCode.app.services.ImageService;
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
@RequestMapping("/api")
public class DeveloperRestControlador {

    @Autowired
    private IDeveloperRepository repository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private DeveloperService developerService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/developers")
    public ResponseEntity<List<Developer>> listaDevelopers() {
        return ResponseEntity.ok(developerService.listDevelopers());
    }

    @GetMapping("/developers/{id}")
    public Developer developerById(@PathVariable Long id) {
        return developerService.seachDeveloperById(id);
    }

    @PostMapping("/developers")
    public void registrarDeveloper(@RequestParam MultipartFile archivo,
                                   @RequestParam String nombre,
                                   @RequestParam String apellido,
                                   @RequestParam String email,
                                   @RequestParam String nacionalidad,
                                   @RequestParam String fechaNacStr,
                                   @RequestParam String password,
                                   @RequestParam String genero,
                                   @RequestParam String telefono,
                                   @RequestParam Double salario,
                                   @RequestParam String seniority,
                                   @RequestParam String especialidad,
                                   @RequestParam String descripcion,
                                   @RequestParam(required = false) String comentario
    ) throws MiException, ParseException {

        Comment comment = commentService.createComment(comentario);

        developerService.createDeveloper(archivo, nombre, apellido, email, nacionalidad, fechaNacStr,
                password, genero, telefono, salario, seniority, especialidad, descripcion, comment);

    }

    @DeleteMapping("/developers/{id}")
    public void eliminarDeveloper(@PathVariable Long id) {
        developerService.deleteDeveloperById(id);
    }


    @PostMapping("/createUser")
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

        try {
            return ResponseEntity.ok(developerService.createPart1Developer(archivo, nombre, apellido, email, nacionalidad, fechaNacStr, password,
                    genero, telefono));

        } catch (MiException e) {
            model.put("error", e.getMessage());
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/updateDeveloper/{id}")
    public void updateDeveloper(@PathVariable Long id,
                                @RequestParam(required = false) MultipartFile archivo,
                                @RequestParam(required = false) String nombre,
                                @RequestParam(required = false) String apellido,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String nacionalidad,
                                @RequestParam(required = false) String fechaNacStr,
                                @RequestParam(required = false) String password,
                                @RequestParam(required = false) String genero,
                                @RequestParam(required = false) String telefono,
                                @RequestParam(required = false) Double salario,
                                @RequestParam(required = false) String seniority,
                                @RequestParam(required = false) String especialidad,
                                @RequestParam(required = false) String descripcion,
                                @RequestParam(required = false) String comentario,
                                ModelMap model
    ) throws MiException, ParseException {

        try {
            model.put("exito", "el developer fue creado exitosamente");

            Date fechaNacimiento = null; // Inicializar la variable fechaNacimiento como null

            if (fechaNacStr != null && !fechaNacStr.isEmpty()) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                fechaNacimiento = format.parse(fechaNacStr);
            }

            developerService.updateDeveloper(id, archivo, nombre, apellido, email, nacionalidad, fechaNacStr,
                    password, genero, telefono, salario, seniority, especialidad, descripcion, comentario, fechaNacimiento);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/developer/{seniority}")
    public List<Developer> getDevelopersBySeniority(@PathVariable String seniority) {
        return developerService.getDevelopersBySeniority(seniority);
    }

    @GetMapping("/developerEmail/{email}")
    public Developer getDevelopersByEmail(@PathVariable String email) {
        return repository.seachByEmail(email);
    }

}
