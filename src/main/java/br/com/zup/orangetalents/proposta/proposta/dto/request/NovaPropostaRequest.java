package br.com.zup.orangetalents.proposta.proposta.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zup.orangetalents.proposta.commom.validation.CPForCNPJ;
import br.com.zup.orangetalents.proposta.proposta.model.Proposta;

public class NovaPropostaRequest {

	private @NotBlank @CPForCNPJ String documento;
	private @NotBlank String email;
	private @NotBlank String nome;
	private @NotBlank String endereco;
	private @NotNull @Positive BigDecimal salario;
	
	public NovaPropostaRequest(@NotBlank String documento, @NotBlank String email, @NotBlank String nome,
			@NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
		super();
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}

	public Proposta toModel() {
		return new Proposta(documento, email, nome, endereco, salario);
	}

	public String getDocumento() {
		return this.documento;
	}
}
