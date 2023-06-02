package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.*;
import com.BillMyCode.app.enumerations.Rol;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IAccountantRepository;
import com.BillMyCode.app.repositories.IContactRepository;
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
public class ContactService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private IContactRepository repositorio;
    @Autowired
    private ImageService imageService;

    /**
     * Metodo searchAllContact() devuelve la lista de todos los Contactos.
     *
     * @return List<Contact>
     */
    @Transactional(readOnly = true)
    public List<Contact> searchAllContact() {
        return repositorio.findAll();
    }

    /**
     * Metodo searchContactById(id) devuelve el contacto según una id.
     *
     * @return Developer
     */
    @Transactional(readOnly = true)
    public Contact searchContactById(Long id) {
        return repositorio.findById(id).get();
    }

    /**
     * Método deleteContactById(id) borra un contacto según una id.
     *
     */
    @Transactional
    public void deleteContactById(Long id) {
        repositorio.deleteById(id);
    }

    /**
     * Metodo crearContact: Crea un Contact
     *
     * @throws: MiException
     */
    @Transactional
    public void crearContact(MultipartFile archivo,
                              String nombre,
                              String apellido,
                              String email,
                              String telefono,
                              String password,
                              String newpassword,

    ) throws MiException {

        validate(nombre,apellido,email,password,newpassword);

        String cryptPassword = passwordEncoder.encode(password);

        Contact contacto = new Contact();
        contacto.setNombre(nombre);
        contacto.setApellido(apellido);
        contacto.setEmail(email);

        contacto.setPassword(cryptPassword);
        contacto.setTelefono(telefono);
        Image image = imageService.save(archivo);

        contacto.setImage(image);
        repositorio.save(contacto);


    }

    /**
     * Metodo updateContacto: Actualiza los datos de un contacto
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

        Contact contacto = searchContactById(id);
        String cryptPassword = passwordEncoder.encode(password);

        if (contacto != null && contacto.getId().equals(id)) {
            validate(nombre,apellido,email,password,newpassword);



            contacto.setNombre(nombre);
            contacto.setApellido(apellido);
            contacto.setEmail(email);
            contacto.setNacionalidad(nacionalidad);
            contacto.setFechaNacimiento(fechaNacimiento);
            contacto.setPassword(cryptPassword);
            contacto.setGenero(genero);
            contacto.setTelefono(telefono);
            Image image = imageService.save(archivo);

            contacto.setImage(image);
            repositorio.save(contacto);

            repositorio.save(contacto);
        } else {
            throw new MiException("No se puede modificar el contacto del contacto.");
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
     * * @throws: MiException
     */
    public void validate (String nombre, String apellido, String email, String password,
                          String newpassword) throws MiException {
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
        if (!newpassword.equals(password) || newpassword.isBlank()){
            throw new MiException("Error, el campo Repetir Contrasela no puede distinto a Contraseña o estar vacío");
        }
    }

}

