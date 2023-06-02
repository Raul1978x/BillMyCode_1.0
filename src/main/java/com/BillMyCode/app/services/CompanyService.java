package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Company;
import com.BillMyCode.app.entities.Contact;
import com.BillMyCode.app.entities.Image;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.ICompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class CompanyService {

    /*Las empresas de tecnologías deben poder aportar su granito de arena,
modificando sus datos salariales y agregando comentarios sobre cada
puesto.
 El perfil de EMPRESA deberá traer los puestos de trabajo vinculados a ella
con su calificación, salario promedio e histórico y posibilidad de ver
comentarios.*/

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private ICompanyRepository repositorio;
    @Autowired
    private ImageService imageService;


    /**
     * Metodo searchAllCompany() devuelve la lista de todos las Compañias.
     *
     * @return List<Company>
     */
    @Transactional(readOnly = true)
    public List<Company> searchAllCompany() {return repositorio.findAll();
    }

    /**
     * Metodo searchCompanyById(id) devuelve el Company según una id.
     *
     * @return Company
     */
    @Transactional(readOnly = true)
    public Company searchCompanyById(Long id) {
        return repositorio.findById(id).get();
    }
    /**
     * Método deleteCompanyById(id) borra una Company según una id.
     *
     */
    @Transactional
    public void deleteCompanyById(Long id) {
        repositorio.deleteById(id);
    }

    /**
     * Metodo crearCompany: Crea una Company
     *
     * @throws: MiException
     */
    @Transactional
    public void crearCompany(MultipartFile archivo,
                             String nombre,
                             Contact contacto,
                             String password,
                             String newpassword,
                             Double sueldoPromedio,
                             Boolean status) throws MiException {

        validate(nombre,contacto,password,newpassword, sueldoPromedio, status);

        String cryptPassword = passwordEncoder.encode(password);

        Company company = new Company();
        company.setNombre(nombre);
        company.setContacto(contacto);


        company.setPassword(cryptPassword);
        company.setSalarioPromedio(sueldoPromedio);
        Image logo = imageService.save(archivo);

        company.setLogo (logo);
        repositorio.save(company);


    }

    /**
     * Metodo updateContacto: Actualiza los datos de un contacto
     *
     * @throws: MiException
     */
    @Transactional
    public void updateCompany(Long id,
                                 MultipartFile archivo,
                                 String nombre,
                                 Contact contacto,
                                 String password,
                                 String newpassword,
                                 Double sueldoPromedio,
                                 Boolean status) throws MiException {

        Company company = searchCompanyById(id);
        String cryptPassword = passwordEncoder.encode(password);

        if (company != null && company.getId().equals(id)) {
            validate(nombre,contacto,password,newpassword,sueldoPromedio, status);



            company.setNombre(nombre);
            company.setPassword (cryptPassword);
            company.setContacto(contacto);
            Image image = imageService.save(archivo);

            contacto.setImage(image);
            repositorio.save(company);

            repositorio.save(company);
        } else {
            throw new MiException("No se puede modificar el contacto del contacto.");
        }
    }



    /**
     * Metodo validate: valida que los valores ingresados se cargen conforme a las
     * necesidades de la aplicacion
     * @param nombre
     * @param password
     * @param newpassword
     * * @throws: MiException
     */

    public void validate (String nombre, Contact contacto, String password,
                          String newpassword, Double sueldoPromedio, Boolean status) throws MiException {
        if (nombre.isEmpty() || nombre.isBlank()){
            throw new MiException("Error, el campo Nombre no puede estar vacio");
        }

        if (password.isEmpty() || password.isBlank()){
            throw new MiException("Error, el campo Contraseña no puede estar vacio");
        }
        if (!newpassword.equals(password) || newpassword.isBlank()){
            throw new MiException("Error, el campo Repetir Contraseña no puede distinto a Contraseña o estar vacío");
        }
    }

}















