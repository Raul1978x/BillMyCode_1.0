package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Admin;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IAccountantRepository;
import com.BillMyCode.app.repositories.IAdminRepository;
import com.BillMyCode.app.repositories.IDeveloperRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private IDeveloperRepository developerRepository;

    @Autowired
    private IAccountantRepository accountantRepository;

    @Autowired
    private IAdminRepository adminRepository;

    /*@Autowired
    private HttpSession httpSession;*/

    /**
     * Metodo loadUserByUsername: Busca y carga un developer, admin o accounter para su autenticación, tambien guarda la
     * sesion iniciada
     *
     * @param email: Correo ingresado por el usuario
     *
     * @return: Un UserDetails con el correo,contraseña y rol para la autenticacion;
     *
     * @throws : Tira una excepcion UsernameNotFoundException si no encontro a nadie con el email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Developer developer = developerRepository.seachByEmail(email);
        if (developer != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            permisos.add(new SimpleGrantedAuthority("ROLE_" + developer.getRol().toString()));

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("sessionuser",developer);

            /*httpSession.setAttribute("sessionuser", developer);*/

            return org.springframework.security.core.userdetails.User.builder()
                    .username(developer.getEmail())
                    .password(developer.getPassword())
                    .authorities(permisos)
                    .build();
        } else {
            Accountant accountant = accountantRepository.seachByEmail(email);
            if (accountant != null){
                List<GrantedAuthority> permisos = new ArrayList<>();
                permisos.add(new SimpleGrantedAuthority("ROLE_" + accountant.getRol().toString()));

                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession(true);
                session.setAttribute("sessionuser",accountant);

                /*setAttribute("sessionuser", accountant);*/

                return new User(accountant.getEmail(), accountant.getPassword(), permisos);
            }else {
                Admin admin = adminRepository.searchByEmail(email);
                if (admin != null){
                    List<GrantedAuthority> permisos = new ArrayList<>();
                    permisos.add(new SimpleGrantedAuthority("ROLE_" + admin.getRol().toString()));

                    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                    HttpSession session = attr.getRequest().getSession(true);
                    session.setAttribute("sessionuser",admin);

                    /*httpSession.setAttribute("sessionuser", admin);*/

                    return org.springframework.security.core.userdetails.User.builder()
                            .username(admin.getEmail())
                            .password(admin.getPassword())
                            .authorities(permisos)
                            .build();
                }else {
                    throw new UsernameNotFoundException("usuario no encontrado con el correo electronico: " + email);
                }
            }
        }
    }
    public Boolean validarEmail (String email){
        Developer developer = developerRepository.seachByEmail(email);
        if (developer != null) {
            return true;
        } else {
            Accountant accountant = accountantRepository.seachByEmail(email);
            if (accountant != null){
                return true;
            }else {
                Admin admin = adminRepository.searchByEmail(email);
                if (admin != null){
                    return true;
                }else {
                    return false;
                }
            }
        }
    }

}
