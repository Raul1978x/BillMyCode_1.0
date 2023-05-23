package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Admin;
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
     * @param fechaNac
     * @param telefono
     * @param archivo
     *
     * @throws: MiException
     */
    @Transactional
    public void createAdmin (String nombre, String apellido, String email, String password, Date fechaNac,
                             String telefono, MultipartFile archivo) throws MiException{
        validate(nombre, apellido, email, password, fechaNac);
        String cryptPassword = passwordEncoder.encode(password);

        Image imagen = imagenServicio.save(archivo);
        Admin admin = new Admin();

        admin.setImage(imagen);
        admin.setNombre(nombre);
        admin.setApellido(apellido);
        admin.setEmail(email);
        admin.setPassword(cryptPassword);
        admin.setFechaNacimiento(fechaNac);
        admin.setRol(Rol.ADMIN);
        admin.setTelefono(telefono);

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
     * @param fechaNac
     * @param telefono
     * @param archivo
     *
     * @throws: MiException
     */
    @Transactional
    public void updateAdmin (Long id, String nombre, String apellido, String email, String password, Date fechaNac,
                             String telefono, MultipartFile archivo) throws MiException {

        Admin admin = repositorio.findById(id).get();

        if (admin != null) {

            validate(nombre, apellido, email, password, fechaNac);
            String cryptPassword = passwordEncoder.encode(password);

            Image imagen = imagenServicio.save(archivo);

            admin.setImage(imagen);
            admin.setNombre(nombre);
            admin.setApellido(apellido);
            admin.setEmail(email);
            admin.setPassword(cryptPassword);
            admin.setFechaNacimiento(fechaNac);
            admin.setRol(Rol.ADMIN);
            admin.setTelefono(telefono);

            repositorio.save(admin);
        }else{
            System.out.println("No se encontro al usuario");
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
     * @param fechaNac
     */
    public void validate (String nombre, String apellido, String email, String password, Date fechaNac){
        if (nombre.isEmpty() || nombre.equals(" ")){
            System.out.println("Error, el campo Nombre no puede estar vacio");
        }
        if (apellido.isEmpty() || apellido.equals(" ")){
            System.out.println("Error, el campo Apellido no puede estar vacio");
        }
        if (email.isEmpty() || email.equals(" ") || !email.contains("@") || !email.contains(".")){
            System.out.println("Error, el campo Email debe tener ingresado un correo valido");
        }
        if (password.isEmpty() || password.equals(" ")){ //Preguntar: hay que validar la cantirdad de caracteres de la contraseña?
            System.out.println("Error, el campo Contrasela no puede estar vacio");
        }
        if (fechaNac==null){
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
     * @param newpassword
     */
    public void validate2 (String nombre, String apellido, String email, String password, String newpassword){
        if (nombre.isEmpty() || nombre.equals(" ")){
            System.out.println("Error, el campo Nombre no puede estar vacio");
        }
        if (apellido.isEmpty() || apellido.equals(" ")){
            System.out.println("Error, el campo Apellido no puede estar vacio");
        }
        if (email.isEmpty() || email.equals(" ") || !email.contains("@") || !email.contains(".")){
            System.out.println("Error, el campo Email debe tener ingresado un correo valido");
        }
        if (password.isEmpty() || password.equals(" ")){
            System.out.println("Error, el campo Contrasela no puede estar vacio");
        }
        if (newpassword.isEmpty() || newpassword.equals(" ")){
            System.out.println("Error, el campo Contrasela no puede estar vacio");
        }
    }

}