package br.com.orion.loja.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import br.com.orion.loja.service.UserDetailApplicationService;

/**
 * SecurityConfig
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private  UserDetailApplicationService userDetailApplicationService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailApplicationService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
        .and().authorizeRequests()
        .anyRequest()
        .authenticated()
        .antMatchers("/*/admin/**").hasRole("ADMINISTRADOR")
        .antMatchers("/*/protected/**").hasAnyRole("ADMINISTRADOR", "USUARIO")
        //Use it when the CSRF is enable
        //.and().httpBasic().and().csrf().ignoringAntMatchers("/h2-console/**")
        .and().headers().frameOptions().sameOrigin()
        .and().httpBasic()
        .and().csrf().disable();
    }
    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}