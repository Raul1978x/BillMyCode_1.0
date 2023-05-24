package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IAccountantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountantService implements UserDetailsService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private IAccountantRepository repository;

    @Autowired
    private ImageService imageService;

    @Transactional(readOnly = true)
    public List<Accountant> searchAllAccountants() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Accountant searchAccountantById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteAccountantById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void createAccountant(MultipartFile archivo,
                                 String nombre,
                                 String apellido,
                                 String email,
                                 String nacionalidad,
                                 Date fechaNacimiento,
                                 String genero,
                                 String telefono,
                                 String password,
                                 String especializaciones,
                                 String matricula,
                                 Double honorarios
    ) throws MiException {

        validate(nombre, email, especializaciones);
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
        contador.setEspecializaciones(especializaciones);
        contador.setMatricula(matricula);
        contador.setHonorarios(honorarios);
        contador.setStatus(true);
        contador.setRol(Rol.ACCOUNTANT);
        Image image = imageService.save(archivo);

        contador.setImage(image);
        repository.save(contador);
    }

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
                                 String especializaciones,
                                 String matricula,
                                 Double honorarios) throws MiException {

        Accountant contador = searchAccountantById(id);
        String cryptPassword = passwordEncoder.encode(password);

        if (contador != null && contador.getId().equals(id)) {
            validate(nombre, email, especializaciones);

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
            contador.setEspecializaciones(especializaciones);
            contador.setStatus(true);
            contador.setRol(Rol.ACCOUNTANT);
            Image image = imageService.save(archivo);

            contador.setImage(image);
            repository.save(contador);
        } else {
            throw new MiException("No se puede modificar el contacto del contador.");
        }
    }

    @Transactional
    public void agregarReputacion(Long id, Double reputacion, List<Comment> comentario) throws MiException {
        Accountant contador = searchAccountantById(id);
        if (contador != null && contador.getId().equals(id)) {
            contador.setReputacion(reputacion);
            /*contador.setComments(comentario);*/
            repository.save(contador);
        } else {
            throw new MiException("No se puede agregar la calificación al contador.");
        }
    }

    public void validate(String nombre, String email, String especializacion) throws MiException {
        if (nombre.isEmpty() || nombre.isBlank()) {
            throw new MiException("El nombre no puede estar vacío.");
        }
        if (email.isEmpty() || email.isBlank()) {
            throw new MiException("El email no puede estar vacío.");
        }
        if (especializacion == null) {
            throw new MiException("La especialización no puede estar vacía.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Accountant usuario = repository.searchByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el correo electrónico: " + email);
        }
        List<GrantedAuthority> permisos = new ArrayList<>();
        permisos.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()));
        System.out.println(permisos);
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(permisos)
                .build();
    }
}

