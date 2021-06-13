package br.com.zup.orangetalents.proposta.cartao.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BloqueioRequest {

	private String sistemaResponsavel;

	public BloqueioRequest(String sistemaResponsavel) {
		this.sistemaResponsavel = sistemaResponsavel;
	}
	
	@JsonProperty
	public String getSistemaResponsavel() {
		return this.sistemaResponsavel;
	}
	
	public static BloqueioRequest from(String sistemaResponsavel) {
		return new BloqueioRequest(sistemaResponsavel);
	}
}
