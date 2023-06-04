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
public class DeveloperService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private CommentService commentService;
    @Autowired
    private IDeveloperRepository developerRepository;
    @Autowired
    private ImageService imageService;

    /**
     * Metodo listDevelopers(): Devuelve la lista de todos los Developers.
     *
     * @return List<Developers>
     */
    @Transactional(readOnly = true)
    public List<Developer> listDevelopers() {
        return developerRepository.findAll();
    }

    /**
     * Metodo searchDeveloperById(id): Devuelve el Developer según una id.
     *
     * @param id
     * @return Developer
     */
    @Transactional(readOnly = true)
    public Developer searchDeveloperById(Long id) {
        return developerRepository.findById(id).get();
    }

    /**
     * Método deleteDeveloperById(id): Borra Developer según una id.
     *
     * @param id
     */
    @Transactional
    public void deleteDeveloperById(Long id) {
        developerRepository.deleteById(id);
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
                                String newpassword,
                                String genero,
                                String telefono,
                                Double salario,
                                String seniority,
                                String especialidad,
                                String descripcion,
                                Comment comentario
    ) throws MiException {


        validate(nombre, apellido, email, password, newpassword, fechaNacimiento, genero, telefono, nacionalidad, salario, seniority, especialidad);

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
        developer.setStatus(true);
        Image image = imageService.save(archivo);

        developer.setImage(image);

        developerRepository.save(developer);
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
                                String newpassword,
                                String genero,
                                String telefono,
                                Double salario,
                                String seniority,
                                String especialidad,
                                String descripcion,
                                String comentario
    ) throws MiException, ParseException {

        Optional<Developer> respuesta = developerRepository.findById(id);
        String cryptPassword = passwordEncoder.encode(password);

        validate(nombre,apellido,email,password,newpassword,fechaNacimiento,genero,telefono,nacionalidad,salario,seniority,especialidad);


        if (respuesta.isPresent()) {
            Developer result = respuesta.get();

            Comment comment = commentService.updateComment(result.getComentario().getId(), comentario);

            result.setNombre(nombre);
            result.setApellido(apellido);
            result.setEmail(email);
            result.setNacionalidad(nacionalidad);
            result.setFechaNacimiento(fechaNacimiento);
            result.setPassword(cryptPassword);
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

            developerRepository.save(result);
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
        return developerRepository.searchBySeniority(seniority);
    }

    /**
     * Metodo validate: valida que los valores ingresados se cargen conforme a las
     * necesidades de la aplicacion
     *
     * @param salario
     * @param seniority
     * @param especialidad
     */
    public void validate(String nombre,
                         String apellido,
                         String email,
                         String password,
                         String newpassword,
                         Date fechaNacimiento,
                         String genero,
                         String telefono,
                         String nacionalidad,
                         Double salario,
                         String seniority,
                         String especialidad
    ) throws MiException {

        if (nombre.isBlank() || nombre.isEmpty()) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
        if (apellido.isBlank() || apellido.isEmpty()) {
            throw new MiException("El apellido no puede ser nulo o estar vacio");
        }
        if (email.isBlank() || email.isEmpty()) {
            throw new MiException("El email no puede ser nulo o estar vacio");
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

        if (salario == null) {
            throw new MiException("El salario no puede ser nulo o estar vacio");
        }
        if (seniority.isBlank() || seniority == "") {
            throw new MiException("La seniority no puede ser nula o estar vacia");
        }
        if (especialidad.isBlank() || especialidad == "") {
            throw new MiException("La especialidad no puede ser nula o estar vacia");
        }
    }

    @Transactional
    public void bajaDeveloper(Long id) {
        Developer developer = searchDeveloperById(id);
        developer.setStatus(false);
        developerRepository.save(developer);
    }

    @Transactional(readOnly = true)
    public Developer searchDeveloperByAccountantId(Long accountantId) {
        return developerRepository.searchDeveloperByAccountant(accountantId);
    }

    @Transactional
    public void altaDeveloper(Long id){
        Developer developer = searchDeveloperById(id);
        developer.setStatus(true);
        developerRepository.save(developer);
    }
}
