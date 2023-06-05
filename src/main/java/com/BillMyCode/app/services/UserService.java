package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Image;
import com.BillMyCode.app.entities.User;
import com.BillMyCode.app.enumerations.Rol;
import com.BillMyCode.app.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Date;

@Service
public class UserService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ImageService imageService;

    /**
     * Metodo searchUsers: Trae los usuarios de la base de datos, pero solo para lectura.
     *
     * @return Una lista de Usuarios (User)
     */
    @Transactional(readOnly = true)
    public List<User> searchUsers() {
        return userRepository.findAll();
    }

    /**
     * Metodo searchUserById: Trae al usuario que coincida con la ID pasada por parametro, pero solo para lectura.
     *
     * @param id: id pasada por por parametro
     *
     * @return Un Usuario (User)
     */
    @Transactional(readOnly = true)
    public User searchUserById(Long id) {
        return userRepository.findById(id).get();
    }

    /**
     * Metodo searchUserById: Elimina al usuario que coincida con la ID
     *
     * @param id: id pasada por por parametro
     */

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Metodo createUser: Crea un usario
     *
     * @param archivo: Imagen
     * @param nombre
     * @param apellido
     * @param email
     * @param genero
     * @param nacionalidad
     * @param password
     * @param telefono
     * @param fechaNacimento
     *
     * @return
     */
    @Transactional
    public User createUser(MultipartFile archivo,
                           String nombre,
                           String apellido,
                           String email,
                           String genero,
                           String nacionalidad,
                           String password,
                           String telefono,
                           Date fechaNacimento
    ) {


        User user = new User();
        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setEmail(email);
        user.setGenero(genero);
        user.setNacionalidad(nacionalidad);
        user.setPassword(password);
        user.setTelefono(telefono);
        user.setFechaNacimiento(fechaNacimento);
        user.setRol(Rol.GUEST);

        Image image = imageService.save(archivo);

        user.setImage(image);

        userRepository.save(user);

        return user;
    }

    /**
     * Metodo updateUser: Actualiza los datos de un Usuario
     *
     * @param id
     * @param archivo: Imagen
     * @param nombre
     * @param apellido
     * @param email
     * @param genero
     * @param nacionalidad
     * @param password
     * @param telefono
     * @param fechaNacimento
     * @param rol
     */

    @Transactional
    public void updateUser(Long id,
                           MultipartFile archivo,
                           String nombre,
                           String apellido,
                           String email,
                           String genero,
                           String nacionalidad,
                           String password,
                           String telefono,
                           Date fechaNacimento,
                           Rol rol) {

        validate(nombre, apellido, email, password, fechaNacimento, genero, telefono, nacionalidad);

        if (!id.equals(null)) {
            User user = searchUserById(id);

            user.setNombre(nombre);
            user.setApellido(apellido);
            user.setEmail(email);
            user.setGenero(genero);
            user.setNacionalidad(nacionalidad);
            user.setPassword(password);
            user.setTelefono(telefono);
            user.setFechaNacimiento(fechaNacimento);
            user.setRol(rol);

            Image image = imageService.save(archivo);

            user.setImage(image);

            userRepository.save(user);
        }
    }

    /**
     * Metodo validate: valida que los valores ingresados se cargen conforme a las
     * necesidades de la aplicacion, se llama en createUser y updateUser
     *
     * @param nombre
     * @param apellido
     * @param email
     * @param password
     * @param fechaNacimiento
     * @param genero
     * @param telefono
     * @param nacionalidad
     */
    public void validate(String nombre,
                         String apellido,
                         String email,
                         String password,
                         Date fechaNacimiento,
                         String genero,
                         String telefono,
                         String nacionalidad) {
        if (nombre.isBlank() || nombre == "") {
            System.out.println("El nombre no puede ser nulo o estar vacio");
        }
        if (apellido.isBlank() || apellido == "") {
            System.out.println("El apellido no puede ser nulo o estar vacio");
        }
        if (email.isBlank() || email == "") {
            System.out.println("El email no puede ser nulo o estar vacio");
        }
        if (password.isBlank() || password == "") {
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
        if (genero.isBlank() || genero == "") {
            System.out.println("El genero no puede ser nulo o estar vacio");
        }
        if (nacionalidad.isBlank() || nacionalidad == "") {
            System.out.println("La nacionalidad no puede ser nula o estar vacia");
        }
        if (telefono.isBlank() || telefono == "") {
            System.out.println("El telefono no puede ser nulo o estar vacio");
        }
    }

}
