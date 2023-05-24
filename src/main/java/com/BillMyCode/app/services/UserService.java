package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Image;
import com.BillMyCode.app.entities.User;
import com.BillMyCode.app.enumerations.Rol;
import com.BillMyCode.app.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Transactional(readOnly = true)
    public List<User> searchUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User searchUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User createUser(MultipartFile archivo,
                           String nombre,
                           String apellido,
                           String email,
                           String genero,
                           String nacionalidad,
                           String password,
                           String password2,
                           String telefono,
                           Date fechaNacimiento) {

        validate(nombre, apellido, email, password, password2, fechaNacimiento);

        User user = new User();
        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setEmail(email);
        user.setGenero(genero);
        user.setNacionalidad(nacionalidad);
        user.setPassword(password);
        user.setPassword2(password2);
        user.setTelefono(telefono);
        user.setFechaNacimiento(fechaNacimiento);
        user.setRol(Rol.GUEST);

        Image image = imageService.save(archivo);

        user.setImage(image);

        userRepository.save(user);

        return user;
    }

    @Transactional
    public void updateUser(Long id,
                           MultipartFile archivo,
                           String nombre,
                           String apellido,
                           String email,
                           String genero,
                           String nacionalidad,
                           String password,
                           String password2,
                           String telefono,
                           Date fechaNacimiento,
                           Rol rol) {

        validate(nombre, apellido, email, password, password2, fechaNacimiento);

        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            user.setNombre(nombre);
            user.setApellido(apellido);
            user.setEmail(email);
            user.setGenero(genero);
            user.setNacionalidad(nacionalidad);
            user.setPassword(password);
            user.setPassword2(password2);
            user.setTelefono(telefono);
            user.setFechaNacimiento(fechaNacimiento);
            user.setRol(rol);

            Image image = imageService.save(archivo);

            user.setImage(image);

            userRepository.save(user);
        }
    }

    public void validate(String nombre,
                         String apellido,
                         String email,
                         String password,
                         String password2,
                         Date fechaNacimiento) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o estar vacío");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo o estar vacío");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o estar vacío");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o estar vacía");
        }
        if (password2 == null || !password2.equals(password)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }
    }
}
