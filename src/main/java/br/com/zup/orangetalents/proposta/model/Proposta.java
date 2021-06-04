package br.com.zup.orangetalents.proposta.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zup.orangetalents.proposta.validation.CPForCNPJ;

@Entity
public class Proposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private @NotBlank @CPForCNPJ String documento;
	private @NotBlank String email;
	private @NotBlank String nome;
	private @NotBlank String endereco;
	private @NotNull @Positive BigDecimal salario;
	
	@Enumerated(EnumType.STRING)
	private StatusProposta status;
	
	@Deprecated
	public Proposta() { }
	
	public Proposta(@NotBlank String documento, @NotBlank String email, @NotBlank String nome,
			@NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
		super();
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}

	public Long getId() {
		return this.id;
	}

	public String getNome() {
		return this.nome;
	}
	
	public String getDocumento() {
		return this.documento;
	}
	
	public StatusProposta getStatus() {
		return this.status;
	}
	
	public void setNaoElegivel() {
		this.status = StatusProposta.NAO_ELEGIVEL;
	}
	
	public void setStatus(StatusProposta status) {
		this.status = status;
	}
}
