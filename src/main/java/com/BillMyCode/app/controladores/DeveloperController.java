package com.BillMyCode.app.controladores;

import com.BillMyCode.app.entidades.Comentario;
import com.BillMyCode.app.entidades.Contador;
import com.BillMyCode.app.entidades.Developer;
import com.BillMyCode.app.entidades.Empresa;
import com.BillMyCode.app.excepciones.MiException;
import com.BillMyCode.app.servicios.DeveloperServicio;
import com.BillMyCode.app.servicios.ImagenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("")
public class DeveloperController {

    @Autowired
    private DeveloperServicio servicio;

    @Autowired
    private ImagenServicio imagenServicio;

    @GetMapping("/formDeveloper")
    public String formulario() {
        return "formDev";
    }

    @PostMapping("/formDeveloper")
    public String registrarDeveloper(@RequestParam MultipartFile archivo, @RequestParam String nombre,
                                     @RequestParam String apellido, @RequestParam String email,
                                     @RequestParam String password, @RequestParam LocalDate fechaNac,
                                     @RequestParam Double salario, @RequestParam String seniority,
                                     @RequestParam String especialidad, @RequestParam String descripcion,
                                     @RequestParam Comentario comentario, @RequestParam List<Empresa> empresas,
                                     @RequestParam(required = false) Contador contador, ModelMap model)
            throws MiException {

        try {
            servicio.crearDeveloper(archivo, nombre, apellido, email, password, fechaNac, salario, seniority,
                    especialidad, descripcion, comentario, empresas, contador);
           /* servicio.crearDeveloper(archivo, "nombre", "apellido", "email", "password", LocalDate.parse(1990/04/25), 500.0, "seniority",
                    "especialidad", "descripcion", comentario, empresas, contador);*/
            model.put("exito", "el developer fue creado exitosamente");

        } catch (MiException e) {
            model.put("error", e.getMessage());

            return "formDev";
        }
        return "formDev";
    }

  /*  @GetMapping("/query/{id}")
    public Developer searchBySeniority(@PathVariable Long id){
        return servicio.searchBySeniority(id);
    }*/
}
