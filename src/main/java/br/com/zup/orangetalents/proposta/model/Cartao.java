package br.com.zup.orangetalents.proposta.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String numeroCartao;
	
	@NotNull
	private LocalDateTime dataEmissao;
	
	@NotBlank
	private String nomeTitular;
	
	@OneToOne(mappedBy = "cartao")
	@NotNull
	private Proposta proposta;
	
	@Deprecated
	public Cartao() { }

	public Cartao(@NotBlank String numeroCartao, @NotNull LocalDateTime dataEmissao, @NotBlank String nomeTitular,
			@NotNull Proposta proposta) {
		super();
		this.numeroCartao = numeroCartao;
		this.dataEmissao = dataEmissao;
		this.nomeTitular = nomeTitular;
		this.proposta = proposta;
		proposta.setCartao(this);
	}
}
