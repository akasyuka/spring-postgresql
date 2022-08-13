package org.ibs.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.Security;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        String indexUl = "https://localhost:8443/api/swagger-ui/index.html?urls.primaryName=v2";
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/**").hasRole("READER")
                    .antMatchers(HttpMethod.DELETE, "/v2/employee").hasRole("WRITER")
                    .antMatchers(HttpMethod.POST, "/v2/employee").hasRole("WRITER")
                    .and()
                .formLogin()
                    .defaultSuccessUrl(indexUl, true);
        return http.build();
    }


    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails userR = User.withDefaultPasswordEncoder()
                .username("reader")
                .password("123")
                .roles("READER")
                .build();
        UserDetails userW = User.withDefaultPasswordEncoder()
                .username("writer")
                .password("123")
                .roles("WRITER", "READER")
                .build();

        return new InMemoryUserDetailsManager(userR, userW);

    }
}
