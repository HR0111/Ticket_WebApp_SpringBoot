package com.hemant.tickets.config;


import com.hemant.tickets.filters.UserProvisioningFilter;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http , UserProvisioningFilter userProvisioningFilter ,
                                                   JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {

        http.authorizeHttpRequests( authorize->
                        authorize
                                .requestMatchers(HttpMethod.GET , "/api/v1/published-events/**").permitAll()
                                .requestMatchers("/api/v1/events").hasRole("ORGANIZER")

                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2->
                        oauth2.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
                )
                .addFilterAfter(userProvisioningFilter, BearerTokenAuthenticationFilter.class);

        return http.build();

    }


}
