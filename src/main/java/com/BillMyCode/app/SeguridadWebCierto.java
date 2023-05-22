package com.BillMyCode.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWebCierto {

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .requestMatchers("/admin").hasAnyRole("ADMIN","DEV","GUEST")
                    .requestMatchers("/index").permitAll()
                .and().formLogin(
                        form -> form
                                .loginPage("/login")
                                .usernameParameter("usuario") // Nombre del campo del correo electrónico
                                .passwordParameter("password") // Nombre del campo de la contraseña
                                .loginProcessingUrl("/logincheck") // Redirige a loadUserByUsername en DeveloperService
                                .defaultSuccessUrl("/thymeleaf/principal-developers") // Alfin ANDA, redirige a prinDev
                                .failureUrl("/thymeleaf/login")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

}
