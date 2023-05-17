package com.BillMyCode.app.servicios;

import com.BillMyCode.app.entidades.*;
import com.BillMyCode.app.enumeraciones.Rol;
import com.BillMyCode.app.excepciones.MiException;
import com.BillMyCode.app.repositorios.IDeveloperRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class DeveloperServicio {

    @Autowired
    private IDeveloperRepositorio repositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    /**
     * Metodo listDevelopers() devuelve la lista de todos los Developers.
     * @return List<Developers>
     */
    @Transactional(readOnly = true)
    public List<Developer> listDevelopers() {
        return repositorio.findAll();
    }

    /**
     * Metodo searchDeveloperById(id) devuelve el Developer según su id.
     * @param id
     * @return Developer
     */
    @Transactional(readOnly = true)
    public Developer seachDeveloperById(Long id) {
        return repositorio.findById(id).get();
    }

    /**
     * Método deleteDeveloperById(id) borra Developer según su id.
     * @param id
     */
    @Transactional
    public void deleteDeveloperById(Long id) {
        repositorio.deleteById(id);
    }

    /**
     * Método createDeveloper(params) crea un nuevo Developers
     * @param archivo
     * @param nombre
     * @param apellido
     * @param email
     * @param password
     * @param fechaNac
     * @param salario
     * @param seniority
     * @param especialidad
     * @param descripcion
     * @param comentario
     * @param empresas
     * @param contador
     * @throws MiException
     */
    @Transactional
    public void createDeveloper(MultipartFile archivo, String nombre, String apellido, String email, String password,
                                Date fechaNac, Double salario, String seniority, String especialidad,
                                String descripcion, Comentario comentario, List<Empresa> empresas, Contador contador) throws MiException {
        validate(nombre, apellido, email, password, fechaNac);

        Developer developer = new Developer();

        developer.setNombre(nombre);
        developer.setApellido(apellido);
        developer.setEmail(email);
        developer.setPassword(password);
        developer.setSalario(salario);
        developer.setFechaNacimiento(fechaNac);
        developer.setSeniority(seniority);
        developer.setEspecialidad(especialidad);
        developer.setDescripcion(descripcion);
        developer.setComentario(comentario);
        developer.setEmpresa(empresas);
        developer.setContador(contador);
        developer.setRol(Rol.DEV);

        Imagen imagen = imagenServicio.guardar(archivo);

        developer.setImagen(imagen);

        repositorio.save(developer);
    }

    /**
     * getDeveloperBySeniority(seniority) busca la lista de todos los
     * Developers con el mismo grado de seniority
     * @param seniority
     * @return List<Developer>
     */
    @Transactional(readOnly = true)
    public List<Developer> getDevelopersBySeniority(String seniority) {
        return repositorio.searchBySeniority(seniority);
    }

    /**
     * validate(params) valida que los valores ingresados se cargen conforme a las
     * necesidades de la aplicacion
     * @param nombre
     * @param apellido
     * @param email
     * @param password
     * @param fechaNacimiento
     */
    public void validate(String nombre, String apellido, String email, String password,
                        Date fechaNacimiento) {

        if (nombre.isEmpty() || nombre == "") {
            System.out.println("El nombre no puede ser nulo o estar vacio");
        }
        if (apellido.isEmpty() || apellido == "") {
            System.out.println("El apellido no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email == "") {
            System.out.println("El email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == "") {
            System.out.println("La contraseña no puede ser nula o estar vacia");
        }
        /*
         * if (password2.isEmpty() || (!password2.equals(password))) {
         * System.out.
         * println("La contraseña no puede estar vacia o ser distinta a la anterior");
         * }
         */
        if (fechaNacimiento == null) {
            System.out.println("La fecha de nacimiento no puede estar vacia");
        }
    }

}
