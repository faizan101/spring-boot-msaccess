package com.msaccess.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("testUser").password("{noop}userpassword").roles("USER")
                .and()
                .withUser("testadmin").password("{noop}adminpassword").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .formLogin().defaultSuccessUrl("/accountStatement/fetch").and()
                .sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).and().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/accountStatement/fetch").hasAnyRole("ADMIN", "USER")
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated();//@formatter:on
    }
}

