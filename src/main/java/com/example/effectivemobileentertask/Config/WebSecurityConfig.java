package com.example.effectivemobileentertask.Config;

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
    DataSource dataSource;

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
        authenticationManagerBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select email,password,active from users where email=?")
                .authoritiesByUsernameQuery("select users.email,role.roles from users inner join role on users.id=role.id where users.email=?");
    }
}
