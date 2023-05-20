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
        return userRepository.findById(id).get();
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

    public void validate(String nombre,
                         String apellido,
                         String email,
                         String password,
                         Date fechaNacimiento,
                         String genero,
                         String telefono,
                         String nacionalidad) {

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
    }

    /* @Override
     public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException{
         User user = userRepository.seachByEmail(email);
         if (user != null){
             List<GrantedAuthority> permisos = new ArrayList<>();
             GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+user.getRol().toString());
             permisos.add(p);

             ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
             HttpSession session = attr.getRequest().getSession(true);
             session.setAttribute("usersession", user);

           new User(user.getEmail(), user.getPassword(), permisos);

         }
         return null;
     }*/

}
