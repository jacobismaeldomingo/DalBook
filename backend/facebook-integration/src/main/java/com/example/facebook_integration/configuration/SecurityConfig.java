//package com.example.facebook_integration.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("password")
//                .roles("SYSTEM_ADMIN")
//                .build();
//
//        // Add more users if needed
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//
//    @Bean
//    public HttpSecurity httpSecurity() throws Exception {
//        return HttpSecurity.http()
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .antMatchers("/", "/home", "/signup", "/login").permitAll() // Allow public access to these paths
//                                .antMatchers("/admin/**").hasRole("SYSTEM_ADMIN") // Only SYSTEM_ADMIN can access /admin/**
//                                .anyRequest().authenticated()
//                )
//                .formLogin(formLogin ->
//                        formLogin
//                                .loginPage("/login")
//                                .permitAll()
//                )
//                .logout(logout ->
//                        logout
//                                .permitAll()
//                )
//                .build();
//    }
//}
//
