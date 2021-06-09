package br.com.zup.orangetalents.proposta.proposta.dto.response;

import java.time.LocalDateTime;

import br.com.zup.orangetalents.proposta.cartao.model.Cartao;
import br.com.zup.orangetalents.proposta.proposta.model.Proposta;

public class CartaoResponse {

	private String id;
	private LocalDateTime emitidoEm;
	private String titular;

	public CartaoResponse(String id, LocalDateTime emitidoEm, String titular) {
		super();
		this.id = id;
		this.emitidoEm = emitidoEm;
		this.titular = titular;
	}
	
	public Cartao toModel(Proposta proposta) {
		return new Cartao(id, emitidoEm, titular, proposta);
	}

	@Override
	public String toString() {
		return "CartaoResponse [id=" + id + ", emitidoEm=" + emitidoEm + ", titular=" + titular + "]";
	}
	
	
}
