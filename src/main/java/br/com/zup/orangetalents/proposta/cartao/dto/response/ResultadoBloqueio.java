package br.com.zup.orangetalents.proposta.cartao.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ResultadoBloqueio {

	private String resultado;

	@JsonCreator
	public ResultadoBloqueio(String resultado) {
		this.resultado = resultado;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	@Override
	public String toString() {
		return "ResultadoBloqueio [resultado=" + resultado + "]";
	}

}
