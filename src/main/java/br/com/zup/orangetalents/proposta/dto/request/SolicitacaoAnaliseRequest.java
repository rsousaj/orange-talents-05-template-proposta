package br.com.zup.orangetalents.proposta.dto.request;

import br.com.zup.orangetalents.proposta.model.Proposta;

public class SolicitacaoAnaliseRequest {

	private String documento;
	private String nome;
	private String idProposta;
	
	public SolicitacaoAnaliseRequest(String documento, String nome, String idProposta) {
		super();
		this.documento = documento;
		this.nome = nome;
		this.idProposta = idProposta;
	}
	
	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public String getIdProposta() {
		return idProposta;
	}

	public static SolicitacaoAnaliseRequest build(Proposta propostaEmCriacao) {
		return new SolicitacaoAnaliseRequest(propostaEmCriacao.getDocumento(), 
				propostaEmCriacao.getNome(), 
				propostaEmCriacao.getId().toString());
	}
}
