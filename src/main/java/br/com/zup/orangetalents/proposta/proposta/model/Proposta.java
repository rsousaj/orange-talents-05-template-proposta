package br.com.zup.orangetalents.proposta.proposta.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
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

import br.com.zup.orangetalents.proposta.cartao.model.Cartao;
import br.com.zup.orangetalents.proposta.proposta.validation.CPForCNPJ;
import br.com.zup.orangetalents.proposta.proposta.validation.CriptografadorCampo;

@Entity
public class Proposta {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Convert(converter = CriptografadorCampo.class)
	private @NotBlank String documento;
	
	private @NotBlank String email;
	private @NotBlank String nome;
	private @NotBlank String endereco;
	private @NotNull @Positive BigDecimal salario;
	
	@Enumerated(EnumType.STRING)
	private StatusProposta status;
	
	@OneToOne(cascade = CascadeType.ALL)
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
	
	public String getNome() {
		return this.nome;
	}
	
	public UUID getId() {
		return id;
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
	
	public Cartao getCartao() {
		return this.cartao;
	}

	public String getEmail() {
		return this.email;
	}
	
	public String getEndereco() {
		return this.endereco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documento == null) ? 0 : documento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proposta other = (Proposta) obj;
		if (documento == null) {
			if (other.documento != null)
				return false;
		} else if (!documento.equals(other.documento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void associaCartao(Cartao cartao) {
		this.cartao = cartao;
	}
	
	
}
