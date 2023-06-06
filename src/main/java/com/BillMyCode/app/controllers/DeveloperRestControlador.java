package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IDeveloperRepository;
import com.BillMyCode.app.services.CommentService;
import com.BillMyCode.app.services.DeveloperService;
import com.BillMyCode.app.services.ImageService;
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
public class DeveloperRestControlador {

    @Autowired
    private IDeveloperRepository repository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private DeveloperService developerService;

    @Autowired
    private ImageService imageService;

    /**
     * Metodo listaDevelopers: Devuelve la lista de todos los Developers
     *
     * @return: ResponseEntity<List<Developer>>
     */
    @GetMapping("/developers")
    public ResponseEntity<List<Developer>> listaDevelopers() {
        return ResponseEntity.ok(developerService.listDevelopers());
    }

    /**
     * Metodo developerById: Devuelve el Developer según una id
     *
     * @param id
     *
     * @return: Developer
     */
    @GetMapping("/developers/{id}")
    public Developer developerById(@PathVariable Long id) {
        return developerService.searchDeveloperById(id);
    }

    /**
     * Metodo registrarDeveloper: Crea un nuevo developer
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
     * @param salario
     * @param seniority
     * @param especialidad
     * @param descripcion
     * @param comentario
     *
     * @throws: MiException
     * @throws: ParseException
     */
    @PostMapping("/developers")
    public void registrarDeveloper(@RequestParam MultipartFile archivo,
                                   @RequestParam String nombre,
                                   @RequestParam String apellido,
                                   @RequestParam String email,
                                   @RequestParam String nacionalidad,
                                   @RequestParam("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
                                   @RequestParam String password,
                                   @RequestParam String newpassword,
                                   @RequestParam String genero,
                                   @RequestParam String telefono,
                                   @RequestParam Double salario,
                                   @RequestParam String seniority,
                                   @RequestParam String especialidad,
                                   @RequestParam String descripcion,
                                   @RequestParam(required = false) String comentario
    ) throws MiException, ParseException {

        Comment comment = commentService.createComment(comentario);

        developerService.createDeveloper(archivo, nombre, apellido, email, nacionalidad, fechaNacimiento,
                password,newpassword,genero,telefono,salario,seniority,especialidad,descripcion,comment);

    }

    /**
     * Metodo eliminarDeveloper: Borra Developer según una id
     *
     * @param id
     */
    @DeleteMapping("/developers/{id}")
    public void eliminarDeveloper(@PathVariable Long id) {
        developerService.deleteDeveloperById(id);
    }

    /**
     * Metodo updateDeveloper: Actualiza los datos de un developer
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
     * @param salario
     * @param seniority
     * @param especialidad
     * @param descripcion
     * @param comentario
     * @param model
     *
     * @throws: MiException
     * @throws: ParseException
     */
    @PutMapping("/updateDeveloper/{id}")
    public void updateDeveloper(@PathVariable Long id,
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
                                @RequestParam(required = false) Double salario,
                                @RequestParam(required = false) String seniority,
                                @RequestParam(required = false) String especialidad,
                                @RequestParam(required = false) String descripcion,
                                @RequestParam(required = false) String comentario,
                                ModelMap model
    ) throws MiException, ParseException {

        try {
            model.put("exito", "el developer fue creado exitosamente");

            developerService.updateDeveloper(id, archivo, nombre, apellido, email, nacionalidad, fechaNacimiento,
                    password, newpassword, genero, telefono, salario, seniority, especialidad, descripcion, comentario);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo getDevelopersBySeniority: Busca la lista de todos los
     * developers con el mismo grado de seniority
     *
     * @param seniority
     *
     * @return: List<Developer>
     */
    @GetMapping("/developer/{seniority}")
    public List<Developer> getDevelopersBySeniority(@PathVariable String seniority) {
        return developerService.getDevelopersBySeniority(seniority);
    }

    /**
     * Metodo getDevelopersByEmail: Busca un developer segun un email
     *
     * @param email
     *
     * @return: Developer
     */
    @GetMapping("/developerEmail/{email}")
    public Developer getDevelopersByEmail(@PathVariable String email) {
        return repository.seachByEmail(email);
    }
 @GetMapping("/developerByAccountant/{accountantId}")
    public List<Developer> searchDeveloperByAccountantId(@PathVariable Long accountantId) {
        return repository.searchAllDevelopersByAccountant(accountantId);
    }

}
