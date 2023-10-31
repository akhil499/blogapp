package com.blogapp.blogapp.security;

import com.blogapp.blogapp.users.UsersService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {


    //private JWTAuthenticationFilter jwtAuthenticationFilter;
    private JWTService jwtService;
    private UsersService usersService;

    @Autowired
    public AppSecurityConfig(JWTService jwtService, UsersService usersService) {
//        this.jwtAuthenticationFilter = new JWTAuthenticationFilter(
//                new JWTAuthenticationManager(jwtService, usersService));
        this.jwtService = jwtService;
        this.usersService = usersService;
    }



    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JWTAuthenticationFilter(
                new JWTAuthenticationManager(jwtService, usersService));

    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        .anyRequest().permitAll()
//                )
//                .httpBasic(withDefaults());
//
//        http.addFilterBefore(jwtAuthenticationFilter(), AnonymousAuthenticationFilter.class);
//
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(antMatcher(HttpMethod.POST, "/users")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/users/login")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/articles")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/articles/*")).permitAll()
                        .anyRequest().authenticated()

                )
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access Denied: " + accessDeniedException.getMessage());
                });
        http.addFilterBefore(jwtAuthenticationFilter(), AnonymousAuthenticationFilter.class);

        return http.build();
    }



}
