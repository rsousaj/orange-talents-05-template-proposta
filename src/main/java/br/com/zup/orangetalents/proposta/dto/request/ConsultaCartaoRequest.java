package br.com.zup.orangetalents.proposta.dto.request;

import br.com.zup.orangetalents.proposta.model.Proposta;

public class ConsultaCartaoRequest {

	private String idProposta;
	private String documento;
	private String nome;
	
	public ConsultaCartaoRequest(String idProposta, String documento, String nome) {
		super();
		this.idProposta = idProposta;
		this.documento = documento;
		this.nome = nome;
	}
	
	public String getIdProposta() {
		return idProposta;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public static ConsultaCartaoRequest build(Proposta proposta) {
		return new ConsultaCartaoRequest(proposta.getId().toString(), proposta.getDocumento(), proposta.getNome());
	}
}
