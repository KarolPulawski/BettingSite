package pl.coderslab.bettingsite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/moderator/**").hasRole("MODERATOR")
            .antMatchers("/admin/**").hasRole("ADMIN")
//            .antMatchers("/home/**").authenticated()
            .anyRequest().permitAll()
            .and().formLogin().defaultSuccessUrl("/home/hello")
            .and().logout().logoutSuccessUrl("/logoutpage");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.jdbcAuthentication().dataSource(dataSource)
//            .passwordEncoder(passwordEncoder())
//            .withUser("moderator").password(passwordEncoder().encode("moderator123")).roles("MODERATOR", "USER")
//            .and()
//            .withUser("user").password(passwordEncoder().encode("user123")).roles("USER")
//                .and()
//                .withUser("user2").password(passwordEncoder().encode("user123")).roles("USER");

        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder());
    }
}