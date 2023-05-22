package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.entities.Image;
import com.BillMyCode.app.enumerations.Rol;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IDeveloperRepository;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DeveloperService implements UserDetailsService{
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Crea la ver para encriptar la contraseña 

    @Autowired
    private  CommentService commentService;
    @Autowired
    private IDeveloperRepository repositorio;

    @Autowired
    private ImageService imageService;

    /**
     * Metodo listDevelopers() devuelve la lista de todos los Developers.
     *
     * @return List<Developers>
     */
    @Transactional(readOnly = true)
    public List<Developer> listDevelopers() {
        return repositorio.findAll();
    }

    /**
     * Metodo searchDeveloperById(id) devuelve el Developer según su id.
     *
     * @param id
     * @return Developer
     */
    @Transactional(readOnly = true)
    public Developer seachDeveloperById(Long id) {
        return repositorio.findById(id).get();
    }

    public Developer seachDeveloperByEmail(String email) {
        return repositorio.seachByEmail(email);
    }

    /**
     * Método deleteDeveloperById(id) borra Developer según su id.
     *
     * @param id
     */
    @Transactional
    public void deleteDeveloperById(Long id) {
        repositorio.deleteById(id);
    }

    @Transactional
    public void createDeveloper(MultipartFile archivo,
                                String nombre,
                                String apellido,
                                String email,
                                String nacionalidad,
                                String fechaNacStr,
                                String password,
                                String genero,
                                String telefono,
                                Double salario,
                                String seniority,
                                String especialidad,
                                String descripcion,
                                Comment comentario
    ) throws MiException, ParseException {

        Date fechaNacimiento= null;
        if (fechaNacStr!="") {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            fechaNacimiento = format.parse(fechaNacStr);
        }
        validate(nombre, apellido, email, nacionalidad, fechaNacimiento, password, genero, telefono,
                salario, seniority, especialidad, descripcion, comentario);

        Developer developer = new Developer();

        developer.setNombre(nombre);
        developer.setApellido(apellido);
        developer.setEmail(email);
        developer.setNacionalidad(nacionalidad);
        developer.setFechaNacimiento(fechaNacimiento);
        //Agarra la password, la encripta y la setea
        String encodedPassword = passwordEncoder.encode(password);
        developer.setPassword(encodedPassword);
        
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

    @Transactional
    public void updateDeveloper(Long id,
                                MultipartFile archivo,
                                String nombre,
                                String apellido,
                                String email,
                                String nacionalidad,
                                String fechaNacStr,
                                String password,
                                String genero,
                                String telefono,
                                Double salario,
                                String seniority,
                                String especialidad,
                                String descripcion,
                                String comentario,
                                Date fechaNacimiento) throws MiException, ParseException {

        Optional<Developer> respuesta = repositorio.findById(id);

        if (respuesta.isPresent()) {
            Developer result = respuesta.get();

            Comment comment = commentService.updateComment(result.getComentario().getId(), comentario);

            result.setNombre(nombre);
            result.setApellido(apellido);
            result.setEmail(email);
            result.setNacionalidad(nacionalidad);
            result.setFechaNacimiento(fechaNacimiento);
            String encodedPassword = passwordEncoder.encode(password);
            result.setPassword(encodedPassword);
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


    @Transactional
    public Developer createPart1Developer(MultipartFile archivo,
                                          String nombre,
                                          String apellido,
                                          String email,
                                          String nacionalidad,
                                          String fechaNacStr,
                                          String password,
                                          String genero,
                                          String telefono
    ) throws MiException, ParseException {
/*
        validate(nombre, apellido, email, nacionalidad, fechaNacimiento, password, genero, telefono);
*/

        Date fechaNacimiento= new Date();
        if (fechaNacStr!="") {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            fechaNacimiento = format.parse(fechaNacStr);
        }
        Developer developer = new Developer();

        developer.setNombre(nombre);
        developer.setApellido(apellido);
        developer.setEmail(email);
        developer.setNacionalidad(nacionalidad);
        developer.setFechaNacimiento(fechaNacimiento);
        String encodedPassword = passwordEncoder.encode(password);
        developer.setPassword(encodedPassword);
        developer.setGenero(genero);
        developer.setTelefono(telefono);
        developer.setRol(Rol.DEV);
        Image image = imageService.save(archivo);

        developer.setImage(image);

        repositorio.save(developer);
        return developer;
    }

    @Transactional
    public Developer createPart2Developer(
            Double salario,
            String seniority,
            String especialidad,
            String descripcion,
            Comment comentario
    ) throws MiException {
/*
        validate(salario, seniority, especialidad, descripcion, comentario);
*/

        Developer developer = new Developer();

        developer.setSalario(salario);
        developer.setSeniority(seniority);
        developer.setEspecialidad(especialidad);
        developer.setDescripcion(descripcion);
        developer.setComentario(comentario);
        developer.setRol(Rol.DEV);

        repositorio.save(developer);
        return developer;
    }

    /**
     * getDeveloperBySeniority(seniority) busca la lista de todos los
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
     * validate(params) valida que los valores ingresados se cargen conforme a las
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Developer usuario = repositorio.seachByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el correo electrónico: " + email);
        }
        // Obtener los roles o permisos del usuario
        List<GrantedAuthority> permisos = new ArrayList<>();
        permisos.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()));
        // Crear y devolver los detalles del usuario
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(permisos)
                .build();
    }

}
