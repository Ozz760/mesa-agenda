package com.mesa.agenda.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mesa.agenda.todo.domain.Student;
import com.mesa.agenda.todo.repository.StudentRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final StudentRepository studentRepository;

  public CustomUserDetailsService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Student student = studentRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

    String role = student.getRole() != null ? student.getRole().replaceFirst("^ROLE_", "") : "USER"; // Remove "ROLE_"
                                                                                                     // prefix if
                                                                                                     // present

    return User.builder()
        .username(student.getUsername())
        .password(student.getPassword())
        .roles(role) // Use the resolved role
        .build();
  }
}