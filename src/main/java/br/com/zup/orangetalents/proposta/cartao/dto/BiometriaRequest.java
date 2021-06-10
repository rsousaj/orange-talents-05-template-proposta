package br.com.zup.orangetalents.proposta.cartao.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import br.com.zup.orangetalents.proposta.cartao.model.Biometria;
import br.com.zup.orangetalents.proposta.cartao.model.Cartao;
import br.com.zup.orangetalents.proposta.cartao.validation.Base64;

public class BiometriaRequest {

	@Base64
	private String impressaoDigital;

	@JsonCreator(mode = Mode.PROPERTIES)
	public BiometriaRequest(String impressaoDigital) {
		this.impressaoDigital = impressaoDigital;
	}
	
	@Override
	public String toString() {
		return "BiometriaRequest [impressaoDigital=" + impressaoDigital + "]";
	}

	public Biometria toModel(Cartao cartao) {
		return new Biometria(impressaoDigital, cartao);
	}
}
