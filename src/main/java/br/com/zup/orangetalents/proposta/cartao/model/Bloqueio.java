package br.com.zup.orangetalents.proposta.cartao.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Bloqueio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String userAgent;
	
	@NotBlank
	private String ipAddress;
	
	@ManyToOne(optional = false)
	@NotNull @Valid
	private Cartao cartao;
	
	private LocalDateTime instanteBloqueio = LocalDateTime.now();
	
	@Deprecated
	public Bloqueio() { }

	public Bloqueio(@NotBlank String userAgent, @NotBlank String ipAddress, @NotNull @Valid Cartao cartao) {
		this.userAgent = userAgent;
		this.ipAddress = ipAddress;
		this.cartao = cartao;
	}
}
