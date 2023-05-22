package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.*;
import com.BillMyCode.app.enumerations.Rol;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class AccountantService implements UserDetailsService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private IAccountantRepository repositorio;
    @Autowired
    private ImageService imageService;

    /**
     * Metodo listAccounter() devuelve la lista de todos los Contadores.
     *
     * @return List<Contador>
     */
    @Transactional(readOnly = true)
    public List<Accountant> searchAllAccounters() {
        return repositorio.findAll();
    }

    /**
     * Metodo searchAccounterById(id) devuelve el Contador según su id.
     *
     * @param id
     * @return Developer
     */
    @Transactional(readOnly = true)
    public Accountant searchAccounterById(Long id) {
        return repositorio.findById(id).get();
    }

    /**
     * Método deleteAccounterById(id) borra el Contador según su id.
     *
     * @param id
     */
    @Transactional
    public void deleteAccounterById(Long id) {
        repositorio.deleteById(id);
    }

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
        contador.setPassword(password);
        contador.setStatus(true);
        contador.setRol(Rol.ACCOUNTANT);
        Image image = imageService.save(archivo);

        contador.setImage(image);
        repositorio.save(contador);


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
                                 String password,
                                 String especializaciones,
                                 String matricula,
                                 Double honorarios) throws MiException {

        Accountant contador = searchAccounterById(id);
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

            contador.setPassword(password);
            contador.setStatus(true);
            contador.setRol(Rol.ACCOUNTANT);
            Image image = imageService.save(archivo);

            contador.setImage(image);
            repositorio.save(contador);

            repositorio.save(contador);
        } else {
            throw new MiException("No se puede modificar el contacto del contador.");
        }
    }

    @Transactional
    public void agregarReputacion(Long id, Double reputacion, List<Comment> comentario) throws MiException {
        Accountant contador = searchAccounterById(id);
        if (contador != null && contador.getId().equals(id)) {
            contador.setReputacion(reputacion);
            /*contador.setComments(comentario);*/
            repositorio.save(contador);
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
           /* if (contacto.isEmpty() || contacto.isBlank()) {
                throw new MiException("El contacto no puede estar vacío.");
            }*/
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Accountant usuario = repositorio.seachByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("usuario no encontrado con el correo electronico: " + email);
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

