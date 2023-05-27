package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.repositories.IAccountantRepository;
import com.BillMyCode.app.repositories.IDeveloperRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private IDeveloperRepository developerRepository;

    @Autowired
    private IAccountantRepository accountantRepository;

    @Autowired
    private HttpSession httpSession;

    /**
     * Metodo loadUserByUsername: Busca y carga un developer o accounter para su autenticación
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
            httpSession.setAttribute("sessionuser", developer);
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
                httpSession.setAttribute("sessionuser", accountant);
                return org.springframework.security.core.userdetails.User.builder()
                        .username(accountant.getEmail())
                        .password(accountant.getPassword())
                        .authorities(permisos)
                        .build();
            }else{
                throw new UsernameNotFoundException("usuario no encontrado con el correo electronico: " + email);
            }
        }
    }
}
