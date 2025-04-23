package com.mesa.agenda.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true)   // for @RolesAllowed/@PermitAll
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      // we’ll use Vaadin’s own login form, so disable CSRF for the login endpoint
      .csrf(csrf -> csrf.ignoringRequestMatchers("/login"))
      .headers(headers -> headers.frameOptions(frame ->
        frame.sameOrigin()))      // if you use H2 console

      // permit static and login endpoints, secure everything else
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(
          "/login",
          "/VAADIN/**",
          "/frontend/**",
          "/webjars/**",
          "/h2-console/**"
        ).permitAll()
        .anyRequest().authenticated()
      )

      // configure form‑login at /login
      .formLogin(form -> form
        .loginPage("/login")
        .permitAll()
      )

      // logout goes back to login page
      .logout(logout -> logout
        .logoutSuccessUrl("/login")
      );

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder encoder) {
    // simple in‑memory user; swap in JDBC or your own UserDetailsService as needed
    UserDetails user = User.withUsername("user")
      .password(encoder.encode("password"))
      .roles("USER")
      .build();

    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
