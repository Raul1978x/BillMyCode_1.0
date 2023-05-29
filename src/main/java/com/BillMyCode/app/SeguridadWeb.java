package com.BillMyCode.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SeguridadWeb {

    /**
     * Metodo passwordEncoder: Devuelve un objeto PasswordEncoder que utiliza la encriptacion BCryptPasswordEncoder.
     *
     * @return el objeto PasswordEncoder configurado con la encriptacion BCryptPasswordEncoder
     */
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Metodo filterChain: Configura y devuelve un objeto SecurityFilterChain para la configuración de seguridad de la aplicación.
     *
     * @param http el objeto HttpSecurity utilizado para configurar la seguridad
     *
     * @return el objeto SecurityFilterChain configurado
     *
     * @throws: Exception Si ocurre algun error salta la excepcion
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                    .requestMatchers("/thymeleaf/principaldevelopers").hasRole("DEV")// Controla quien puede acceder a principaldevelopers
                    .requestMatchers("/thymeleaf/principalaccounters").hasRole("ACCOUNTANT")  // Controla quien puede acceder a principalaccounters
                    .requestMatchers("/thymeleaf/admin-vistaprincipal").hasRole("ADMIN")
                    .requestMatchers("index").permitAll() // Controla quien puede acceder al index, en este caso todos
                .and().formLogin(
                        form-> form
                                .loginPage("/login")
                                .usernameParameter("usuario") // Atrapa la contraseña ingresada por el usuario
                                .passwordParameter("password")// Atrapa el email ingresado por el usuario
                                .loginProcessingUrl("/logincheck")
                                .successForwardUrl("/thymeleaf/redilogin") // En caso de una autenticacion correcta redirige a "/thymeleaf/redilogin" que esta en LoginController
                                .failureUrl("/thymeleaf/login") // En caso de una autenticacion incorrecta redirige a /thymeleaf/login
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/thymeleaf/login") // A donde redirige cuando cierro sesion
                                .permitAll()
                ).exceptionHandling()
                 .accessDeniedPage("/thymeleaf/accesoD");
        return http.build();
    }

}
