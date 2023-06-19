package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.Service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserService usuarioServicio;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() { //Como vamos a codificar la contraseña
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() { //Obtener todos los datos originales del usuario
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(usuarioServicio); 
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { //Una vez se tienen los datos, se verifica que sean correctos y se brinda autenticacion 
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { // Configura las rutas y tipos de archivos que serán accesibles sin autenticación.
		http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
        .and().csrf().ignoringAntMatchers("/h2-console/**")
        .and().headers().frameOptions().sameOrigin(); //config. para excluir a h2-console
		http.authorizeRequests().antMatchers(
											"/signup**",
											"/js/**",
											"/css/**",
											"/img/**").permitAll() // Permite que todos los usuarios puedan acceder a la página de inicio de sesión sin autenticación.
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login") //Los parametros ingresados se identifican automaticamente como auqellos correspondintes a la clase padre USER.
		.permitAll()
		.and()
		.logout()
		.invalidateHttpSession(true) //  Invalida la sesión actual al realizar el cierre de sesión.
		.clearAuthentication(true) // Elimina la autenticación actual al realizar el cierre de sesión.
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout") // Después de cerrar sesión, se redirigirá a la página de inicio de sesión con el parámetro logout en la URL.
		.permitAll();
	}
}






