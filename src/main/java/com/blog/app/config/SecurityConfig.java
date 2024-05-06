package com.blog.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SecurityConfig {

	      @Bean
		  public SecurityFilterChain securityFilterChain(HttpSecurity  http) throws
		  Exception {

	          http

	          .authorizeHttpRequests((authorize) -> authorize

            		  // Allow access to /about for everyone
                      .requestMatchers("/api/users/**").permitAll()

                      // Require authentication for all other requests
                      .anyRequest().authenticated()

              );

			  return http.build();
		  }
}

