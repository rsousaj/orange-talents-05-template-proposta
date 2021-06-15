package br.com.zup.orangetalents.proposta.cartao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

@Entity
public class Carteira {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nomeCarteira;
	
	@ManyToOne
	@NotNull @Valid
	private Cartao cartao;

	@Deprecated
	public Carteira() { }
	
	public Carteira(@NotBlank String nomeCarteira, @NotNull @Valid Cartao cartao) {
		Assert.state(nomeCarteira != null, "Imprescindível o nome da Carteira.");
		Assert.state(cartao != null, "É necessário um cartão para ser associado.");
		
		this.nomeCarteira = nomeCarteira.toUpperCase();
		this.cartao = cartao;
	}
	
	
}
