package com.devsuperior.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	// "configure(AuthenticationManagerBuilder auth)" configura o algoritmo da encriptacao da senha e qm q eh o meu "UserDetailsService".
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Faz o "Spring Security" saber (na hr da autenticacao) cmo q vai buscar o usu por e-mail (userDetailsService), e cmo q ele vai ter q analisar a senha criptografada (passwordEncoder).
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// "actuator" eh uma lib q o "Spring Cloud OAuth2" usa p/ poder fazer o objetivo desse codigo por enqt q eh liberar tdas as rotas do sist. Precisei fazer isso pq agr inclui o OAuth2 e ai s/ isso n ia + funfar essa liberacao.
		web.ignoring().antMatchers("/actuator/**");
	}
	
	// Bean (componente) p/ efetuar a autenticacao. Vou precisar dele p/ fazer o 'servidor de autorizacao'.
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
