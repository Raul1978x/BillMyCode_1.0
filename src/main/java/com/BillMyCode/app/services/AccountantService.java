package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.entities.User;
import com.BillMyCode.app.enumerations.Rol;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IAccountantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AccountantService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private IAccountantRepository repositorio;

    /**
     * Metodo listAccounter() devuelve la lista de todos los Contadores.
     *
     * @return List<Contador>
     */
    @Transactional(readOnly = true)
    public List<Accountant> listAccounter() {
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
    public void crearContador(String nombre, String apellido, String email, String password, List<String> especializaciones, List<Comment> comentario, Double reputacion, String matricula, Double honorarios, Boolean status) throws MiException {
        validate(nombre, email, especializaciones);

        Accountant contador = new Accountant();
        contador.setNombre(nombre);
        contador.setApellido(apellido);
        contador.setEmail(email);
        contador.setEspecializaciones(especializaciones);
        contador.setMatricula(matricula);
        contador.setHonorarios(honorarios);
        String encodedPassword = passwordEncoder.encode(password);
        contador.setPassword(encodedPassword);
        contador.setStatus(true);
        contador.setRol(Rol.ACCOUNTER);
        repositorio.save(contador);


    }


    @Transactional
    public void modificarDatosContacto(Long id, String nuevoContacto, User usuario) throws MiException {
        Accountant contador = searchAccounterById(id);
        if (contador != null && contador.getId().equals(id)) {
/*
                contador.setContacto(nuevoContacto);
*/
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
            contador.setComments(comentario);
            repositorio.save(contador);
        } else {
            throw new MiException("No se puede agregar la calificación al contador.");
        }
    }

    public void validate(String nombre, String email, List<String> especializacion) throws MiException {
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
}

