package com.example.registrationlogindemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService; // Servicio que permite obtener los datos del usuario.

    /**
     * Método que encripta la contraseña del usuario para que no se guarde en texto plano en la base de datos.
     *
     * @return la contraseña encriptada.
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Método que configura la seguridad de la aplicación y los permisos de acceso a las rutas.
     *
     * @param http de tipo HttpSecurity que permite configurar la seguridad de la aplicación.
     * @return el filtro de seguridad.
     * @throws Exception sí hay algún error en la configuración de la seguridad.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/users").hasRole("ADMIN") // Solo los usuarios con rol ADMIN pueden acceder a la ruta /users.
                .and()
                .formLogin(
                        form -> form // Configuración del formulario de login.
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/users")
                                .permitAll()
                ).logout(
                        logout -> logout // Configuración del formulario de logout.
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    /**
     * Método que configura el servicio de autenticación de la aplicación y el tipo de encriptación de la contraseña.
     *
     * @param auth de tipo AuthenticationManagerBuilder que permite configurar el servicio de autenticación.
     * @throws Exception sí hay algún error en la configuración del servicio de autenticación.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}