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

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                    .requestMatchers("admin").hasAnyRole("ADMIN","DEV","GUEST","ACCOUNTANT")
                    .requestMatchers("index").permitAll()
                .and().formLogin(
                        form-> form
                                .loginPage("/login")
                                .usernameParameter("usuario")
                                .passwordParameter("password")
                                .loginProcessingUrl("/logincheck")
                                .successForwardUrl("/thymeleaf/redilogin")
                                .failureUrl("/thymeleaf/login")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/thymeleaf")
                                .permitAll()
                );
        return http.build();
    }

}
