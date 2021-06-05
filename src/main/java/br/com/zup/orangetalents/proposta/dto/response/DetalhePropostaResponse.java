package br.com.zup.orangetalents.proposta.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.zup.orangetalents.proposta.model.Proposta;
import br.com.zup.orangetalents.proposta.model.StatusProposta;

public class DetalhePropostaResponse {

	private String id;
	private String nome;
	private String email;
	private String endereco;
	private StatusProposta statusProposta;
	
	@JsonInclude(Include.NON_NULL)
	private StatusCartao statusCartao;

	public DetalhePropostaResponse(String id, String nome, String email, String endereco, StatusProposta statusProposta,
			StatusCartao statusCartao) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
		this.statusProposta = statusProposta;
		this.statusCartao = statusCartao;
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getEndereco() {
		return endereco;
	}
	
	public StatusProposta getStatusProposta() {
		return statusProposta;
	}

	public StatusCartao getStatusCartao() {
		return statusCartao;
	}

	public static DetalhePropostaResponse build(Proposta proposta) {
		StatusCartao statusCartao = 
				proposta.getStatus() == StatusProposta.ELEGIVEL ?
						proposta.cartaoFoiGerado() ? StatusCartao.GERADO : StatusCartao.PENDENTE : null;
		
		return new DetalhePropostaResponse(proposta.getId().toString(), proposta.getNome(), proposta.getEmail(),
				proposta.getEndereco(), proposta.getStatus(), statusCartao);
	}
}

enum StatusCartao {
	PENDENTE, GERADO;
}
