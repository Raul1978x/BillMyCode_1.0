package com.BillMyCode.app.servicios;

import com.BillMyCode.app.entidades.Admin;
import com.BillMyCode.app.entidades.Imagen;
import com.BillMyCode.app.enumeraciones.Rol;
import com.BillMyCode.app.excepciones.MiException;
import com.BillMyCode.app.repositorios.IAdminRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private IAdminRepositorio repositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    /**
     * Metodo deleteAdminById(params) elimina un "Admin" segun "ID" que se pase por parametro.
     * @param id
     */
    @Transactional
    public void deleteAdminById (Long id) {
        repositorio.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Admin> listAdmins() {
        return repositorio.findAll();
    }

    /**
     * Metodo (param) busca un admin por "ID"
     * @param id
     * @return Admin
     */
    @Transactional(readOnly = true)
    public Admin searchAdminById(Long id) {return repositorio.findById(id).get();}

    /**
     * Método createDeveloper(params) crea un nuevo Developers
     * @param nombre
     * @param apellido
     * @param email
     * @param password
     * @param fechaNac
     * @param telefono
     * @param archivo
     * @throws MiException
     */
    @Transactional
    public void createAdmin (String nombre, String apellido, String email, String password, Date fechaNac,
                             String telefono, MultipartFile archivo) throws MiException{
        validate(nombre, apellido, email, password, fechaNac);

        Imagen imagen = imagenServicio.guardar(archivo);
        Admin admin = new Admin();

        admin.setImagen(imagen);
        admin.setNombre(nombre);
        admin.setApellido(apellido);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setFechaNacimiento(fechaNac);
        admin.setRol(Rol.ADMIN);
        admin.setTelefono(telefono);

        repositorio.save(admin);
    }
    @Transactional
    public void actualizar(MultipartFile archivo, Long id, String apellido, String nombre, String email,
                           String password, String newpassword) throws MiException {

        validate2(nombre, apellido, email, password, newpassword); //Revisar si es posible reutilizar la otra validacion
                                                        // El problema es la segunda contraseña
        Optional<Admin> result = Optional.of(repositorio.findById(id).get());
        if (result.isPresent()) {

            Admin admin = result.get();
            admin.setNombre(nombre);
            admin.setEmail(email);
            admin.setPassword(newpassword);

            Imagen imagen = imagenServicio.actualizar(archivo); //Añadir metodo "acualizar" a la entidad "Imagen"???
            admin.setImagen(imagen);
            repositorio.save(admin);
        }

    }

    /**
     * Metodo validate(param) Valida los datos del en el metodo "createAdmin" y devuelve un mensaje en caso de error
     * @param nombre
     * @param apellido
     * @param email
     * @param password
     * @param fechaNac
     */
    public void validate (String nombre, String apellido, String email, String password, Date fechaNac){
        if (nombre.isEmpty() || nombre.equals(" ")){
            System.out.println("Error, el campo Nombre no puede estar vacio");
        }
        if (apellido.isEmpty() || apellido.equals(" ")){
            System.out.println("Error, el campo Apellido no puede estar vacio");
        }
        if (email.isEmpty() || email.equals(" ") || !email.contains("@") || !email.contains(".")){
            System.out.println("Error, el campo Email debe tener ingresado un correo valido");
        }
        if (password.isEmpty() || password.equals(" ")){ //Preguntar: hay que validar la cantirdad de caracteres de la contraseña?
            System.out.println("Error, el campo Contrasela no puede estar vacio");
        }
        if (fechaNac==null){
            System.out.printf("Error, fecha incorrecta");
        }
    }

    public void validate2 (String nombre, String apellido, String email, String password, String newpassword){
        if (nombre.isEmpty() || nombre.equals(" ")){
            System.out.println("Error, el campo Nombre no puede estar vacio");
        }
        if (apellido.isEmpty() || apellido.equals(" ")){
            System.out.println("Error, el campo Apellido no puede estar vacio");
        }
        if (email.isEmpty() || email.equals(" ") || !email.contains("@") || !email.contains(".")){
            System.out.println("Error, el campo Email debe tener ingresado un correo valido");
        }
        if (password.isEmpty() || password.equals(" ")){
            System.out.println("Error, el campo Contrasela no puede estar vacio");
        }
        if (newpassword.isEmpty() || newpassword.equals(" ")){
            System.out.println("Error, el campo Contrasela no puede estar vacio");
        }
    }

}

