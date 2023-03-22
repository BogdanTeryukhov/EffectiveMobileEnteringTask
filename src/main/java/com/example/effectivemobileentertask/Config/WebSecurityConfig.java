package com.example.effectivemobileentertask.Config;

import com.example.effectivemobileentertask.Repository.UserService;
import com.example.effectivemobileentertask.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserServiceImpl userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                    .authorizeHttpRequests(authorize ->
                            authorize
                                    .requestMatchers("/login","/registration").permitAll()
                                    .requestMatchers("/**").fullyAuthenticated()
                    )
                    .formLogin()
                    .loginPage("/login")
                    .successForwardUrl("/")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/", true).permitAll()
                    .permitAll()
                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessUrl("/login")
                .and()
                    .cors()
                    .and()
                    .csrf()
                    .disable();
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
