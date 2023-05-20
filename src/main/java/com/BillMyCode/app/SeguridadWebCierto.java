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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWebCierto {

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/index").permitAll()
                    .requestMatchers("/users").hasRole("ADMIN")
                .and().formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/logincheck")
                                .defaultSuccessUrl("/user")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                        /*logout -> {
                            try {
                                logout
                                        .logoutUrl("/logout")
                                        .logoutSuccessUrl("/login")
                                        .permitAll()
                                        .and()
                                        .csrf()
                                        .disable();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }*/

                );
        return http.build();
    }

}
