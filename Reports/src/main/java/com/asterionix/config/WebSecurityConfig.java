package com.asterionix.config;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;



@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
// tag::enable-redis-httpsession[]
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 100500)
@EnableConfigurationProperties(AsterionixProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	static Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	 
	@Autowired
	private AsterionixProperties props;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		 .authorizeRequests()
		 .antMatchers("/css/**").permitAll()
		 .antMatchers("/js/**").permitAll()
		 .antMatchers("/img/**").permitAll()
		 .antMatchers("/sounds/**").permitAll()
         .anyRequest().authenticated()
         .and()
     .formLogin()
         .loginPage("/login").defaultSuccessUrl("/reports")
         .permitAll()
         .and()
      .logout()                                    
         .permitAll();
         
	}
	// @formatter:on

	// @formatter:off
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
		auth
			.userDetailsService(userDetailsService);
		//	.passwordEncoder(new BCryptPasswordEncoder());
		
	}
	// @formatter:on
	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		
		return new SecurityEvaluationContextExtension();
		
	}
	 @Bean
	  public ConfigBean configBean() {
	    	Properties prop = new Properties();
	    	InputStream input = null;
	     	ConfigBean configBean = new ConfigBean();
	    	try {
				input = new FileInputStream("/etc/asterionix/cdr-viewer.conf");
		
				try {
					prop.load(input);
				
					configBean.setSounds(prop.getProperty("sounds"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       
	    	} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return configBean;
	    }
	
}