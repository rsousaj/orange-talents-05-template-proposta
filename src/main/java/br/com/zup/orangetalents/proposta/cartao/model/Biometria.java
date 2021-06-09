package br.com.zup.orangetalents.proposta.cartao.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String impressaoDigital;
	
	@Deprecated
	public Biometria() { }
	
	public Biometria(String impressaoDigital) {
		this.impressaoDigital = impressaoDigital;
	}
}
