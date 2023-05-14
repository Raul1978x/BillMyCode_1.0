package com.BillMyCode.app.controladores;

import com.BillMyCode.app.entidades.Developer;
import com.BillMyCode.app.excepciones.MiException;
import com.BillMyCode.app.servicios.DeveloperServicio;
import com.BillMyCode.app.servicios.ImagenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeveloperRestControlador {

    @Autowired
    private DeveloperServicio servicio;

    @Autowired
    private ImagenServicio imagenServicio;

    @GetMapping("/developers")
    public ResponseEntity<List<Developer>> listaDevelopers() {
        return ResponseEntity.ok(servicio.listaDevelopers());
    }

    @GetMapping("/developers/{id}")
    public Developer developerById(@PathVariable  Long id) {
        return servicio.developerById(id);
    }

    @PostMapping("/developers")
    public void registrarDeveloper(@RequestBody Developer developer) throws MiException {
        MultipartFile archivo = (MultipartFile) developer.getImagen();

        servicio.crearDeveloper( archivo, developer.getNombre(), developer.getApellido(), developer.getEmail(),
                developer.getPassword(), developer.getFechaNacimiento(), developer.getSalario(), developer.getSeniority(),
                developer.getEspecialidad(), developer.getDescripcion(), developer.getComentario(), developer.getEmpresa(),
                developer.getContador());

    }

    @DeleteMapping("/developers/{id}")
    public void eliminarDeveloper(@PathVariable Long id){
        servicio.borrarDeveloperById(id);
    }


   /* public ResponseEntity<Developer> cargarDeveloper(@RequestBody Developer developer, @RequestPart MultipartFile archivo)
            throws MiException {
        developer = servicio.crearDeveloper(archivo, developer);
        imagenServicio.guardar((MultipartFile) archivo);
        return ResponseEntity.ok(developer);
    }*/

    @GetMapping("/developer/{seniority}")
    public List<Developer> getDevelopersBySeniority(@PathVariable String seniority) {
        return servicio.getDevelopersBySeniority(seniority);
    }

}
