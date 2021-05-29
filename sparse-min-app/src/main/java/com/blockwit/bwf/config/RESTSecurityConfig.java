package com.blockwit.bwf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Order(2)
@Configuration
@EnableWebSecurity
public class RESTSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService appUserDetailsService;
  private final PasswordEncoder passwordEncoder;

  public RESTSecurityConfig(UserDetailsService appUserDetailsService, PasswordEncoder passwordEncoder) {
    this.appUserDetailsService = appUserDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .requestMatchers().antMatchers("/api/v1/**")
        .and()
//        .requestMatcher(request -> {
//          String header = request.getHeader("authorization");
//          if (header != null && header.startsWith("Basic "))
//            return true;
//          return false;
//        })
        .csrf().disable()
        .exceptionHandling()
//        .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
//        .accessDeniedHandler(new RestAccessDeniedHandler())

        .and()
        .anonymous()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()

        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
        .antMatchers(HttpMethod.POST, "/api/v1/**").permitAll()
        .anyRequest().authenticated()
        .and().httpBasic();

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(appUserDetailsService)
        .passwordEncoder(passwordEncoder);
  }

}
