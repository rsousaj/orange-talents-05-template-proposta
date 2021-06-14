package br.com.zup.orangetalents.proposta.commom.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	@Value("${proposta.proposta.uri}")
	private String uriPropostas;
	
	@Value("${proposta.cartao.uri}")
	private String uriCartoes;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests((authorizeRequests) -> 
			authorizeRequests
				.antMatchers(HttpMethod.GET, uriPropostas + "/**").hasAuthority("SCOPE_propostas:read")
				.antMatchers(HttpMethod.POST, uriPropostas + "/**").hasAuthority("SCOPE_propostas:write")
				.antMatchers(HttpMethod.GET, uriCartoes + "/**").hasAuthority("SCOPE_cartoes:read")
				.antMatchers(HttpMethod.POST, uriCartoes + "/**").hasAuthority("SCOPE_cartoes:write")
				.anyRequest().authenticated()
			)
			.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

}
