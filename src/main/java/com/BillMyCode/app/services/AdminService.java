package com.BillMyCode.app.services;

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
    private ImageService imagenServicio;


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
    public void createAdmin (String nombre, String apellido, String email, String nacionalidad, String password, String genero, Date fechaNacimiento,
                             String telefono, MultipartFile archivo) throws MiException{

        validate2(nombre, apellido, email, password, fechaNacimiento);

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
        Image image = imagenServicio.save(archivo);

        admin.setImage(image);

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
                             String genero, Date fechaNacimiento,
                             String telefono, MultipartFile archivo) throws MiException {

        Optional<Admin> respuesta = repositorio.findById(id);
        String cryptPassword = passwordEncoder.encode(password);

        if (respuesta.isPresent()) {
            Admin admin = respuesta.get();

            /*validate2(nombre, apellido, email, password, fechaNacimiento);*/

            admin.setNombre(nombre);
            admin.setApellido(apellido);
            admin.setEmail(email);
            admin.setNacionalidad(nacionalidad);
            admin.setPassword(cryptPassword);
            admin.setGenero(genero);
            admin.setFechaNacimiento(fechaNacimiento);
            admin.setTelefono(telefono);
            admin.setRol(Rol.ADMIN);

            if (archivo != null) {
                Image image = imagenServicio.save(archivo);
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
    public void validate (String nombre, String apellido, String email, String password, Date fechaNacimiento){
        if (nombre.isBlank() || nombre.equals("")){
            System.out.println("Error, el campo Nombre no puede estar vacio");
        }
        if (apellido.isBlank() || apellido.equals("")){
            System.out.println("Error, el campo Apellido no puede estar vacio");
        }
        if (email.isBlank() || email.equals("") || !email.contains("@") || !email.contains(".")){
            System.out.println("Error, el campo Email debe tener ingresado un correo valido");
        }
        if (password.isBlank() || password.equals("")){ //Preguntar: hay que validar la cantirdad de caracteres de la contraseña?
            System.out.println("Error, el campo Contrasela no puede estar vacio");
        }
        if (fechaNacimiento==null){
            System.out.print("Error, fecha incorrecta");
        }
    }

    /**
     * Metodo validate2: valida que los valores ingresados se cargen conforme a las
     * necesidades de la aplicacion
     *
     * @param nombre
     * @param apellido
     * @param email
     * @param password
     * @param
     */
    public void validate2 (String nombre, String apellido, String email, String password, Date fechaNacimiento) throws MiException {
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
        /*if (!newpassword.equals(password)){
            throw new MiException("Error, el campo Repetir Contrasela no puede distinto a Contraseña");
        }*/
        if (fechaNacimiento==null){
            throw new MiException("Error, fecha incorrecta");
        }
    }

}