package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Admin;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.entities.Image;
import com.BillMyCode.app.enumerations.Rol;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private IAdminRepository repositorio;

    @Autowired
    private ImageService imageService;

    @Autowired
    private LoginService loginService;


    /**
     * Metodo deleteAdminById(params) elimina un "Admin" segun "ID" que se pase por parametro.
     *
     * @param id
     */
    @Transactional
    public void deleteAdminById (Long id) {
        repositorio.deleteById(id);
    }

    /**
     * Metodo listAdmins: Devuelve una lista de todos los administradores
     *
     * @return: Lista de Admin
     */
    @Transactional(readOnly = true)
    public List<Admin> listAdmins() {
        return repositorio.findAll();
    }

    /**
     * Metodo (param) busca un admin por "ID", solo para lectura
     *
     * @param id
     *
     * @return Un Admin
     */
    @Transactional(readOnly = true)
    public Admin searchAdminById(Long id) {return repositorio.findById(id).get();}

    /**
     * Método createAdmin: Crea un Administrador
     *
     * @param nombre
     * @param apellido
     * @param email
     * @param password
     * @param
     * @param fechaNacimiento
     * @param telefono
     * @param archivo
     *
     * @throws: MiException
     */
    @Transactional
    public void createAdmin (String nombre, String apellido, String email, String nacionalidad, String password, String newpassword, String genero, Date fechaNacimiento,
                             String telefono, MultipartFile archivo) throws MiException{

        validate(nombre, apellido, email, password, newpassword, fechaNacimiento, genero, telefono, nacionalidad);

        String cryptPassword = passwordEncoder.encode(password);

        Admin admin = new Admin();

        admin.setNombre(nombre);
        admin.setApellido(apellido);
        admin.setEmail(email);
        admin.setNacionalidad(nacionalidad);
        admin.setPassword(cryptPassword);
        admin.setGenero(genero);
        admin.setFechaNacimiento(fechaNacimiento);
        admin.setRol(Rol.ADMIN);
        admin.setTelefono(telefono);
        admin.setStatus(true);
        if (archivo != null && !archivo.isEmpty()) {
            Image image = imageService.save(archivo);
            admin.setImage(image);
        } else {
            Image defaultImage = imageService.saveDefaultImage();
            admin.setImage(defaultImage);
        }
        repositorio.save(admin);
    }

    /**
     * Metodo updateAdmin: Actualiza los datos de un Administrador
     *
     * @param id
     * @param nombre
     * @param apellido
     * @param email
     * @param password
     * @param fechaNacimiento
     * @param telefono
     * @param archivo
     *
     * @throws: MiException
     */
    @Transactional
    public void updateAdmin (Long id, String nombre, String apellido, String email, String nacionalidad, String password,
                             String newpassword, String genero, Date fechaNacimiento,
                             String telefono, MultipartFile archivo) throws MiException {

        Optional<Admin> respuesta = repositorio.findById(id);
        String cryptPassword = passwordEncoder.encode(password);

        if (respuesta.isPresent()) {
            Admin admin = respuesta.get();

            validate(nombre, apellido, email, password, newpassword, fechaNacimiento, genero, telefono, nacionalidad);

            admin.setNombre(nombre);
            admin.setApellido(apellido);
            admin.setEmail(email);
            admin.setNacionalidad(nacionalidad);
            admin.setPassword(cryptPassword);
            admin.setGenero(genero);
            admin.setFechaNacimiento(fechaNacimiento);
            admin.setTelefono(telefono);
            admin.setRol(Rol.ADMIN);

            if (archivo.isEmpty()){
                Image image = imageService.buscarImagenById(admin.getImage().getId());

                admin.setImage(image);
            }else {
                Image image = imageService.save(archivo);

                admin.setImage(image);
            }

            repositorio.save(admin);
        }
    }

    /**
     * Metodo validate: valida que los valores ingresados se cargen conforme a las
     * necesidades de la aplicacion
     *
     * @param nombre
     * @param apellido
     * @param email
     * @param password
     * @param
     * @param fechaNacimiento
     */
    public void validate(String nombre,
                         String apellido,
                         String email,
                         String password,
                         String newpassword,
                         Date fechaNacimiento,
                         String genero,
                         String telefono,
                         String nacionalidad
    ) throws MiException {

        if (nombre.isBlank() || nombre.isEmpty()) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
        if (apellido.isBlank() || apellido.isEmpty()) {
            throw new MiException("El apellido no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email.isBlank() || !email.contains("@") || !email.contains(".")){
            throw new MiException("El campo Email debe tener ingresado un correo valido");
        }
        if (loginService.validarEmail(email)){
            throw new MiException("El Email ingresado ya se encuentra registrado");
        }
        if (password.isBlank() || password.isEmpty()) {
            throw new MiException("La contraseña no puede ser nula o estar vacia");
        }
        if (newpassword.isEmpty() || (!newpassword.equals(password))) {
            throw new MiException("Las contraseñas no coinciden");
        }
        if (fechaNacimiento == null) {
            throw new MiException("La fecha de nacimiento no puede estar vacia");
        }
        if (genero.isBlank() || genero.isEmpty()) {
            throw new MiException("El genero no puede ser nulo o estar vacio");
        }
        if (nacionalidad.isBlank() || nacionalidad.isEmpty()) {
            throw new MiException("La nacionalidad no puede ser nula o estar vacia");
        }
        if (telefono.isBlank() || telefono.isEmpty()) {
            throw new MiException("El telefono no puede ser nulo o estar vacio");
        }

    }
    @Transactional
    public void bajaAdmin(Long id){
        Admin admin = searchAdminById(id);
        admin.setStatus(false);
        repositorio.save(admin);
    }

    @Transactional
    public void altaAdmin(Long id) {
        Admin admin = searchAdminById(id);
        admin.setStatus(true);
        repositorio.save(admin);
    }
}