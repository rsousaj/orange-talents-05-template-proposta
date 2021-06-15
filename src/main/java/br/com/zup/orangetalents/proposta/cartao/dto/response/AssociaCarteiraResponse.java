package br.com.zup.orangetalents.proposta.cartao.dto.response;

public class AssociaCarteiraResponse {

	private String resultado;
	private String id;

	public AssociaCarteiraResponse(String resultado, String id) {
		this.resultado = resultado;
		this.id = id;
	}

	public String getResultado() {
		return resultado;
	}

	public String getId() {
		return id;
	}

}
