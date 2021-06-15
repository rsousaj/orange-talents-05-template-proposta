package br.com.zup.orangetalents.proposta.cartao.dto.request;

public class AssociaCarteiraRequest {

	public String email;
	public String carteira;
	
	private AssociaCarteiraRequest(String email, String carteira) {
		this.email = email;
		this.carteira = carteira;
	}
	
	public static AssociaCarteiraRequest from(String email, String carteira) {
		return new AssociaCarteiraRequest(email, carteira);
	}
}
