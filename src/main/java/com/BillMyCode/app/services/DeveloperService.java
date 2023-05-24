package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.entities.Image;
import com.BillMyCode.app.enumerations.Rol;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IDeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DeveloperService{

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private CommentService commentService;
    @Autowired
    private IDeveloperRepository repositorio;
    @Autowired
    private ImageService imageService;

    /**
     * Metodo listDevelopers(): Devuelve la lista de todos los Developers.
     *
     * @return List<Developers>
     */
    @Transactional(readOnly = true)
    public List<Developer> listDevelopers() {
        return repositorio.findAll();
    }

    /**
     * Metodo searchDeveloperById(id): Devuelve el Developer según una id.
     *
     * @param id
     * @return Developer
     */
    @Transactional(readOnly = true)
    public Developer seachDeveloperById(Long id) {
        return repositorio.findById(id).get();
    }

    /**
     * Método deleteDeveloperById(id): Borra Developer según una id.
     *
     * @param id
     */
    @Transactional
    public void deleteDeveloperById(Long id) {
        repositorio.deleteById(id);
    }

    /**
     * Metodo createDeveloper: Crea un developer
     *
     * @param archivo
     * @param nombre
     * @param apellido
     * @param email
     * @param nacionalidad
     * @param fechaNacimiento
     * @param password
     * @param genero
     * @param telefono
     * @param salario
     * @param seniority
     * @param especialidad
     * @param descripcion
     * @param comentario
     *
     * @throws: MiException
     * @throws: ParseException
     */
    @Transactional
    public void createDeveloper(MultipartFile archivo,
                                String nombre,
                                String apellido,
                                String email,
                                String nacionalidad,
                                Date fechaNacimiento,
                                String password,
                                String genero,
                                String telefono,
                                Double salario,
                                String seniority,
                                String especialidad,
                                String descripcion,
                                Comment comentario
    ) throws MiException, ParseException {


        validate(nombre, apellido, email, nacionalidad, fechaNacimiento, password, genero, telefono,
                salario, seniority, especialidad, descripcion, comentario);
        String cryptPassword = passwordEncoder.encode(password);

        Developer developer = new Developer();

        developer.setNombre(nombre);
        developer.setApellido(apellido);
        developer.setEmail(email);
        developer.setNacionalidad(nacionalidad);
        developer.setFechaNacimiento(fechaNacimiento);
        developer.setPassword(cryptPassword);
        developer.setGenero(genero);
        developer.setTelefono(telefono);
        developer.setSalario(salario);
        developer.setSeniority(seniority);
        developer.setEspecialidad(especialidad);
        developer.setDescripcion(descripcion);
        developer.setComentario(comentario);
        developer.setRol(Rol.DEV);
        Image image = imageService.save(archivo);

        developer.setImage(image);

        repositorio.save(developer);
    }

    /**
     * Metodo updateDeveloper: Actualiza los datos de un developer
     *
     * @param id
     * @param archivo
     * @param nombre
     * @param apellido
     * @param email
     * @param nacionalidad
     * @param fechaNacimiento
     * @param password
     * @param genero
     * @param telefono
     * @param salario
     * @param seniority
     * @param especialidad
     * @param descripcion
     * @param comentario
     *
     * @throws: MiException
     * @throws: ParseException
     */
    @Transactional
    public void updateDeveloper(Long id,
                                MultipartFile archivo,
                                String nombre,
                                String apellido,
                                String email,
                                String nacionalidad,
                                Date fechaNacimiento,
                                String password,
                                String genero,
                                String telefono,
                                Double salario,
                                String seniority,
                                String especialidad,
                                String descripcion,
                                String comentario
    ) throws MiException, ParseException {

        Optional<Developer> respuesta = repositorio.findById(id);
        String cryptPassword = passwordEncoder.encode(password);

        if (respuesta.isPresent()) {
            Developer result = respuesta.get();

            Comment comment = commentService.updateComment(result.getComentario().getId(), comentario);

            result.setNombre(nombre);
            result.setApellido(apellido);
            result.setEmail(email);
            result.setNacionalidad(nacionalidad);
            result.setFechaNacimiento(fechaNacimiento);
            result.setPassword(password);
            result.setGenero(genero);
            result.setTelefono(telefono);
            result.setSalario(salario);
            result.setSeniority(seniority);
            result.setEspecialidad(especialidad);
            result.setDescripcion(descripcion);
            result.setComentario(comment);
            result.setRol(Rol.DEV);

            if (archivo != null) {
                Image image = imageService.save(archivo);
                result.setImage(image);
            }

            repositorio.save(result);
        }
    }


    /**
     * getDeveloperBySeniority(seniority): Busca la lista de todos los
     * Developers con el mismo grado de seniority
     *
     * @param seniority
     * @return List<Developer>
     */
    @Transactional(readOnly = true)
    public List<Developer> getDevelopersBySeniority(String seniority) {
        return repositorio.searchBySeniority(seniority);
    }

    /**
     * Metodo validate: valida que los valores ingresados se cargen conforme a las
     * necesidades de la aplicacion
     *
     * @param salario
     * @param seniority
     * @param especialidad
     * @param descripcion
     * @param comentario
     */
    public void validate(String nombre,
                         String apellido,
                         String email,
                         String password,
                         Date fechaNacimiento,
                         String genero,
                         String telefono,
                         String nacionalidad,
                         Double salario,
                         String seniority,
                         String especialidad,
                         String descripcion,
                         Comment comentario) {

        if (nombre.isEmpty() || nombre == "") {
            System.out.println("El nombre no puede ser nulo o estar vacio");
        }
        if (apellido.isEmpty() || apellido == "") {
            System.out.println("El apellido no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email == "") {
            System.out.println("El email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == "") {
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
        if (genero.isEmpty() || genero == "") {
            System.out.println("El genero no puede ser nulo o estar vacio");
        }
        if (nacionalidad.isEmpty() || nacionalidad == "") {
            System.out.println("La nacionalidad no puede ser nula o estar vacia");
        }
        if (telefono.isEmpty() || telefono == "") {
            System.out.println("El telefono no puede ser nulo o estar vacio");
        }

        if (salario == null) {
            System.out.println("El salario no puede ser nulo o estar vacio");
        }
        if (seniority.isEmpty() || seniority == "") {
            System.out.println("La seniority no puede ser nula o estar vacia");
        }
        if (especialidad.isEmpty() || especialidad == "") {
            System.out.println("La especialidad no puede ser nula o estar vacia");
        }
        if (descripcion == null) {
            System.out.println("La descripcion no puede ser nula o estar vacia");
        }
        if (comentario == null) {
            System.out.println("El comentario no puede ser nulo");
        }
    }

}
