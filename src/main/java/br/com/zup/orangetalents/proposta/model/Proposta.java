package br.com.zup.orangetalents.proposta.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.GenericGenerator;

import br.com.zup.orangetalents.proposta.validation.CPForCNPJ;

@Entity
public class Proposta {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id;
	
	private @NotBlank @CPForCNPJ String documento;
	private @NotBlank String email;
	private @NotBlank String nome;
	private @NotBlank String endereco;
	private @NotNull @Positive BigDecimal salario;
	
	@Enumerated(EnumType.STRING)
	private StatusProposta status;
	
	@OneToOne
	private Cartao cartao;
	
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

	public UUID getId() {
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
	
	public boolean cartaoFoiGerado() {
		return this.cartao != null;
	}
	
	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}

	public String getEmail() {
		return this.email;
	}
	
	public String getEndereco() {
		return this.endereco;
	}
}
