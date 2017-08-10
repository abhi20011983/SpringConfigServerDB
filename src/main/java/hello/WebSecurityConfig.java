/*
package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity


public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



  //@Autowired
 // private DataSource dataSource;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/home", "/fetch", "/compare", "/compare2", "/compare3", "/LDAP").permitAll()
        .anyRequest().authenticated().and().formLogin().defaultSuccessUrl("/formSubmit")
        .loginPage("/login").permitAll().and()

        .logout().permitAll();

    http.csrf().csrfTokenRepository(csrfTokenRepository());
  }

 

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {


   
 

	  auth .inMemoryAuthentication() .withUser("user").password("password").roles("USER");
  
  }



  private CsrfTokenRepository csrfTokenRepository() {
    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    repository.setSessionAttributeName("_csrf");
    return repository;
  }


}*/
