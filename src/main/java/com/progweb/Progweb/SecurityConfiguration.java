/*package com.progweb.Progweb;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/index.html").permitAll()
                .loginProcessingUrl("/connexion").permitAll().usernameParameter("email").passwordParameter("password")
                .defaultSuccessUrl("/Page_gestionSondages.html",true)
                .failureForwardUrl("/index.html")
                .and()
                .rememberMe()
                .alwaysRemember(true)
                .tokenValiditySeconds(30*5)
                .rememberMeCookieName("mouni")
                .key("somesecret")
                .and()
                .csrf().disable();
                super.configure(http);
    }
}*/
