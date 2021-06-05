package br.com.zup.orangetalents.proposta.dto;

import br.com.zup.orangetalents.proposta.model.Proposta;

public class ConsultaCartaoRequest {

	private Long idProposta;
	private String documento;
	private String nome;
	
	public ConsultaCartaoRequest(Long idProposta, String documento, String nome) {
		super();
		this.idProposta = idProposta;
		this.documento = documento;
		this.nome = nome;
	}
	
	public Long getIdProposta() {
		return idProposta;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public static ConsultaCartaoRequest build(Proposta proposta) {
		return new ConsultaCartaoRequest(proposta.getId(), proposta.getDocumento(), proposta.getNome());
	}
}
