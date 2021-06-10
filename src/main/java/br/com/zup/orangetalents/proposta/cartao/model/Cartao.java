package br.com.zup.orangetalents.proposta.cartao.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import br.com.zup.orangetalents.proposta.proposta.model.Proposta;

@Entity
public class Cartao {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	
	@NotBlank
	private String numeroCartao;
	
	@NotNull
	private LocalDateTime dataEmissao;
	
	@NotBlank
	private String nomeTitular;
	
	@OneToOne(mappedBy = "cartao")
	@NotNull
	private Proposta proposta;
	
	@OneToMany(mappedBy = "cartao", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Biometria> biometrias = new ArrayList<>();
	
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

	public String getId() {
		return id;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public LocalDateTime getDataEmissao() {
		return dataEmissao;
	}

	public String getNomeTitular() {
		return nomeTitular;
	}

	public Proposta getProposta() {
		return proposta;
	}
	
	public void adicionaBiometria(Biometria biometria) {
		this.biometrias.add(biometria);
	}
	
	public List<Biometria> getBiometrias() {
		return Collections.unmodifiableList(this.biometrias);
	}
}
