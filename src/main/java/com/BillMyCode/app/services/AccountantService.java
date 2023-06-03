package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.*;
import com.BillMyCode.app.enumerations.Rol;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IAccountantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class AccountantService  {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private IAccountantRepository repositorio;
    @Autowired
    private ImageService imageService;

    /**
     * Metodo searchAllAccounters() devuelve la lista de todos los Contadores.
     *
     * @return List<Contador>
     */
    @Transactional(readOnly = true)
    public List<Accountant> searchAllAccounters() {
        return repositorio.findAll();
    }

    /**
     * Metodo searchAccounterById(id) devuelve el Contador según una id.
     *
     * @return Developer
     */
    @Transactional(readOnly = true)
    public Accountant searchAccounterById(Long id) {
        return repositorio.findById(id).get();
    }

    /**
     * Método deleteAccounterById(id) borra un Contador según una id.
     *
     */
    @Transactional
    public void deleteAccounterById(Long id) {
        repositorio.deleteById(id);
    }


    /**
     * Metodo crearContador: Crea un Contador
     *
     * @throws: MiException
     */
    @Transactional
    public void crearContador(MultipartFile archivo,
                              String nombre,
                              String apellido,
                              String email,
                              String nacionalidad,
                              Date fechaNacimiento,
                              String genero,
                              String telefono,
                              String password,
                              String newpassword,
                              String especializacion,
                              String matricula,
                              Double honorarios
    ) throws MiException {

        validate(nombre,apellido,email,password,newpassword,fechaNacimiento,especializacion);

        String cryptPassword = passwordEncoder.encode(password);

        Accountant contador = new Accountant();
        contador.setNombre(nombre);
        contador.setApellido(apellido);
        contador.setEmail(email);
        contador.setNacionalidad(nacionalidad);
        contador.setFechaNacimiento(fechaNacimiento);
        contador.setPassword(cryptPassword);
        contador.setGenero(genero);
        contador.setTelefono(telefono);
        contador.setEspecializacion(especializacion);
        contador.setMatricula(matricula);
        contador.setHonorarios(honorarios);
        contador.setStatus(true);
        contador.setRol(Rol.ACCOUNTANT);
        Image image = imageService.save(archivo);

        contador.setImage(image);
        repositorio.save(contador);


    }

    /**
     * Metodo updateAccountant: Actualiza los datos de un Contador
     *
     * @throws: MiException
     */
    @Transactional
    public void updateAccountant(Long id,
                                 MultipartFile archivo,
                                 String nombre,
                                 String apellido,
                                 String email,
                                 String nacionalidad,
                                 Date fechaNacimiento,
                                 String genero,
                                 String telefono,
                                 String password,
                                 String newpassword,
                                 String especializacion,
                                 String matricula,
                                 Double honorarios
    ) throws MiException {

        Accountant contador = searchAccounterById(id);
        String cryptPassword = passwordEncoder.encode(password);


        if (contador != null && contador.getId().equals(id)) {
            validate(nombre,apellido,email,password,newpassword,fechaNacimiento,especializacion);



            contador.setNombre(nombre);
            contador.setApellido(apellido);
            contador.setEmail(email);
            contador.setNacionalidad(nacionalidad);
            contador.setFechaNacimiento(fechaNacimiento);
            contador.setPassword(cryptPassword);
            contador.setGenero(genero);
            contador.setTelefono(telefono);
            contador.setMatricula(matricula);
            contador.setHonorarios(honorarios);
            contador.setEspecializacion(especializacion);
            contador.setStatus(true);
            contador.setRol(Rol.ACCOUNTANT);
            if (archivo.isEmpty()){
            Image image = imageService.buscarImagenById(contador.getImage().getId());

            contador.setImage(image);
            }else {
                Image image = imageService.save(archivo);

                contador.setImage(image);
            }
            repositorio.save(contador);

        } else {
            throw new MiException("No se puede modificar el contacto del contador.");
        }
    }

    /**
     * Metodo agregarReputacion: Añade una calificacion (reputacion) a un contador
     *
     * @throws: MiException
     */
    @Transactional

    public void agregarReputacion(Long id, Reputacion reputacion, Comment comentario) throws MiException {

        Accountant contador = searchAccounterById(id);
        if (contador != null && contador.getId().equals(id)) {
            contador.setReputacion(reputacion);
            List<Comment> comentarios = new ArrayList<>();
            comentarios.add(comentario);
            contador.setComments(comentarios);

            repositorio.save(contador);
        } else {
            throw new MiException("No se puede agregar la calificación al contador.");
        }
    }

    /**
     * Metodo validate: valida que los valores ingresados se cargen conforme a las
     * necesidades de la aplicacion
     * @param nombre
     * @param apellido
     * @param email
     * @param password
     * @param newpassword
     * @param fechaNacimiento
     * * @throws: MiException
     */
    public void validate (String nombre, String apellido, String email, String password,
                           String newpassword,Date fechaNacimiento, String especializacion) throws MiException {
        if (nombre.isEmpty() || nombre.isBlank()){
            throw new MiException("Error, el campo Nombre no puede estar vacio");
        }
        if (apellido.isEmpty() || apellido.isBlank()){
            throw new MiException("Error, el campo Apellido no puede estar vacio");
        }
        if (email.isEmpty() || email.isBlank() || !email.contains("@") || !email.contains(".")){
            throw new MiException("Error, el campo Email debe tener ingresado un correo valido");
        }
        if (password.isEmpty() || password.isBlank()){
            throw new MiException("Error, el campo Contrasela no puede estar vacio");
        }
        if (newpassword.isEmpty() || (!newpassword.equals(password))) {
            throw new MiException("Las contraseñas no coinciden");
        }
        if (fechaNacimiento==null || fechaNacimiento.toString()==""){
            throw new MiException("Error, fecha incorrecta");
        }
        if (especializacion == null) {
            throw new MiException("La especialización no puede estar vacía.");
        }
    }

    @Transactional
    public void bajaAccountant(Long id) {
        Accountant contador = searchAccounterById(id);
        contador.setStatus(false);
        repositorio.save(contador);
    }

    @Transactional
    public void altaAccountant(Long id) {
        Accountant contador = searchAccounterById(id);
        contador.setStatus(true);
        repositorio.save(contador);
    }
}

