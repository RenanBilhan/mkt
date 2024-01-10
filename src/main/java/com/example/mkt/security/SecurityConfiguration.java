package com.example.mkt.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        final String ROLE_ADMIN = "ADMIN";
        final String ROLE_USUARIO = "USUARIO";
        final String ROLE_PARCEIRO = "PARCEIRO";
        final String ROLE_DESATIVADO = "DESATIVADO";

        http.headers().frameOptions().disable().and()
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers(HttpMethod.GET,"/product/**","/stock/{idEstoque}").permitAll()
                        .antMatchers(HttpMethod.POST, "/user", "/auth").permitAll()
                        .antMatchers(HttpMethod.POST, "/simulator/**").hasAnyRole(ROLE_USUARIO, ROLE_ADMIN)
                        .antMatchers(HttpMethod.PUT, "/client/**").hasAnyRole(ROLE_USUARIO, ROLE_ADMIN)
                        .antMatchers(HttpMethod.GET, "/client/{idClient}", "/client/foto/{idClient}").hasAnyRole(ROLE_USUARIO, ROLE_ADMIN)
                        .antMatchers(HttpMethod.GET, "/user/loged", "/user/loged/id").hasAnyRole(ROLE_USUARIO, ROLE_ADMIN)
                        .antMatchers(HttpMethod.GET, "/order/{idClient}").hasAnyRole(ROLE_USUARIO, ROLE_ADMIN)
                        .antMatchers(HttpMethod.GET, "/address/{idClient}").hasAnyRole(ROLE_USUARIO, ROLE_ADMIN)
                        .antMatchers(HttpMethod.GET, "/address/cliente/{idClient}").hasAnyRole(ROLE_USUARIO, ROLE_ADMIN)
                        .antMatchers(HttpMethod.POST, "/stock", "/stock/**").hasAnyRole(ROLE_PARCEIRO, ROLE_ADMIN)
                        .antMatchers(HttpMethod.PUT, "/stock", "/stock/**").hasAnyRole(ROLE_PARCEIRO, ROLE_ADMIN)
                        .antMatchers(HttpMethod.POST, "/product").hasAnyRole(ROLE_PARCEIRO, ROLE_ADMIN)
                        .antMatchers(HttpMethod.PUT, "/product", "/product/**").hasAnyRole(ROLE_PARCEIRO, ROLE_ADMIN)
                        .antMatchers(HttpMethod.GET, "/address/{idClient}", "/stock", "/product", "/client", "/user", "/order", "/address").hasAnyRole(ROLE_ADMIN)
                        .antMatchers(HttpMethod.POST, "/user/admin").hasRole(ROLE_ADMIN)
                        .antMatchers(HttpMethod.DELETE, "/product/{idproduto}").hasRole(ROLE_ADMIN)
                        .anyRequest().denyAll()
                );
        http.addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}