package br.com.zup.orangetalents.proposta.cartao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.zup.orangetalents.proposta.cartao.validation.Base64;

@Entity
public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Base64 @NotNull
	private String impressaoDigital;
	
	@ManyToOne(optional = false)
	@NotNull @Valid
	private Cartao cartao;
	
	@Deprecated
	public Biometria() { }

	public Biometria(@NotNull String impressaoDigital, @NotNull Cartao cartao) {
		this.impressaoDigital = impressaoDigital;
		this.cartao = cartao;
	}
	
	public String getImpressaoDigital() {
		return this.impressaoDigital;
	}
	
	public Long getId() {
		return this.id;
	}
}
