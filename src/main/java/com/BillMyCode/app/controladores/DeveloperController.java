package com.BillMyCode.app.controladores;

import com.BillMyCode.app.entidades.Comentario;
import com.BillMyCode.app.entidades.Contador;
import com.BillMyCode.app.entidades.Empresa;
import com.BillMyCode.app.excepciones.MiException;
import com.BillMyCode.app.servicios.DeveloperServicio;
import com.BillMyCode.app.servicios.ImagenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class DeveloperController {

    @Autowired
    private DeveloperServicio servicio;

    @Autowired
    private ImagenServicio imagenServicio;

    /**
     * Método formDeveloper() retorna el HTML del formulario de carga de Developers
     * @return String formDev.html
     */
    @GetMapping("/formDeveloper")
    public String formDeveloper() {
        return "formDev";
    }

    /**
     * Método registrarDeveloper(params) recibe por parámetros todos los datos que se persitiran en el Developer
     * y luego devuelve un String o ruta (segun registro exitoso o no) con el nombre del HTML correspondiente
     * @param archivo
     * @param nombre
     * @param apellido
     * @param email
     * @param password
     * @param fechaNacStr
     * @param salario
     * @param seniority
     * @param especialidad
     * @param descripcion
     * @param comentario
     * @param empresas
     * @param contador
     * @param model
     * @return String (formDev.html o ruta donde redirige segun resultado)
     * @throws MiException
     */
    @PostMapping("/formDeveloper")
    public String registrarDeveloper(@RequestParam MultipartFile archivo, @RequestParam String nombre,
                                     @RequestParam String apellido, @RequestParam String email,
                                     @RequestParam String password, @RequestParam("fechaNac") String fechaNacStr,
                                     @RequestParam Double salario, @RequestParam String seniority,
                                     @RequestParam String especialidad, @RequestParam String descripcion,
                                     @RequestParam(required = false) Comentario comentario, @RequestParam(required = false) List<Empresa> empresas,
                                     @RequestParam(required = false) Contador contador, ModelMap model)
            throws MiException {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaNac = null;
        try {
            fechaNac = format.parse(fechaNacStr);
        } catch (ParseException e) {
            model.put("error", "La fecha de nacimiento es inválida.");
            return "redirect:/formDev";
        }

        try {
            servicio.createDeveloper(archivo, nombre, apellido, email, password, fechaNac, salario, seniority,
                    especialidad, descripcion, comentario, empresas, contador);
            model.put("exito", "el developer fue creado exitosamente");

        } catch (MiException e) {
            model.put("error", e.getMessage());
            return "redirect:/formDev";
        }
        return "redirect:/formDev";
    }

    /**
     * Método getDevelopersBySeniority(seniority) retorna el o los Developer/s segun el grado de seniority buscado y
     * luego con el String devuelto linkea con el HTML especificado
     * @param seniority
     * @return String "developers.html"
     */
    @GetMapping("/developer/{seniority}")
    public String getDevelopersBySeniority(@PathVariable String seniority) {
                servicio.getDevelopersBySeniority(seniority);
        return "developers"; //vista de la lista de developer donde se cumple la query
    }
}
