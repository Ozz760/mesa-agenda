package com.mesa.agenda.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not recommended for
                                                              // production)
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/login", "/signup", "/public/**", "/VAADIN/**",
                                                                "/frontend/**", "/webjars/**",
                                                                "/images/**", "/h2-console/**", "/rest/students")
                                                .permitAll() // Allow access to Vaadin resources and /rest/students
                                                .requestMatchers("/todoview").permitAll() // Ensure Vaadin handles
                                                                                          // /todoview
                                                .anyRequest().authenticated() // Protect all other endpoints
                                )
                                .exceptionHandling(exception -> exception
                                                .accessDeniedPage("/error") // Redirect to a custom error page for
                                                                            // access denied
                                )
                                .formLogin(form -> form
                                                .loginPage("/login") // Use Vaadin's LoginView for the login page
                                                .defaultSuccessUrl("/todoview", true) // Redirect to /todoview after
                                                                                      // successful login
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login") // Redirect to login page after logout
                                                .permitAll());

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
