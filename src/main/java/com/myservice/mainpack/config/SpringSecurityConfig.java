package com.myservice.mainpack.config;

import com.myservice.mainpack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    UserService userService;
    public UserService getUserService() { return userService; }
    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @Value("${PASSWORD_ENCODER_STRENGTH}")
    private int PASSWORD_ENCODER_STRENGTH;



    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(PASSWORD_ENCODER_STRENGTH);
    }


/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().csrf().disable()
                .x509()
                .subjectPrincipalRegex("CN=(.*?),")
                .userDetailsService(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return new User("Admin",
                        "$2a$10$TxipKKdK4OBdSARGLPlX.eStysMugsdd9bFZQ.gaJNIONerBvtZjy",
                        true,
                        Set.of(Role.USER, Role.ADMIN));
            }
        };
    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
            .antMatchers("/", "/static/**", "/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/")
            .permitAll()
            .and()
            .rememberMe()
            .and()
            .logout()
            .permitAll();

        http.csrf().ignoringAntMatchers("/documents/**");

        //http.requiresChannel().anyRequest().requiresSecure();

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(this.getPasswordEncoder());
        //.passwordEncoder(NoOpPasswordEncoder.getInstance());
    }


}
