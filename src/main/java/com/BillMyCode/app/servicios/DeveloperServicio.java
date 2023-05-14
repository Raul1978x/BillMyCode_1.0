package com.BillMyCode.app.servicios;

import com.BillMyCode.app.entidades.*;
import com.BillMyCode.app.enumeraciones.Rol;
import com.BillMyCode.app.excepciones.MiException;
import com.BillMyCode.app.repositorios.IDeveloperRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeveloperServicio {

    @Autowired
    private IDeveloperRepositorio repositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional(readOnly = true)
    public List<Developer> listaDevelopers() {
        return repositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Developer developerById(Long id) {
        return repositorio.findById(id).get();
    }

    @Transactional
    public void borrarDeveloperById(Long id) {
        repositorio.deleteById(id);
    }

    @Transactional
    public void crearDeveloper(MultipartFile archivo, String nombre, String apellido, String email, String password,
                               LocalDate fechaNac, Double salario, String seniority, String especialidad,
                               String descripcion, Comentario comentario, List<Empresa> empresas, Contador contador) throws MiException {
        validar(nombre, apellido, email, password, fechaNac);

        Developer developer = new Developer();

        developer.setNombre(nombre);
        developer.setApellido(apellido);
        developer.setEmail(email);
        developer.setPassword(password);
        developer.setSalario(salario);
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

    @Transactional(readOnly = true)
    public List<Developer> getDevelopersBySeniority(String seniority) {
        return repositorio.searchBySeniority(seniority);
    }

    /*  public Developer crearDeveloper (MultipartFile archivo, Developer developer)
      throws MiException {

            developer.setNombre(developer.getNombre());
            developer.setApellido(developer.getApellido());
            developer.setEmail(developer.getEmail());
            developer.setPassword(developer.getPassword());
            developer.setRol(Rol.GUEST);
            developer.setFechaNacimiento(developer.getFechaNacimiento());

            Imagen imagen = imagenServicio.guardar(archivo);

            developer.setImagen(imagen);

            repositorio.save(developer);
            return developer;
        }*/
    public void validar(String nombre, String apellido, String email, String password,
                        LocalDate fechaNacimiento) {

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
