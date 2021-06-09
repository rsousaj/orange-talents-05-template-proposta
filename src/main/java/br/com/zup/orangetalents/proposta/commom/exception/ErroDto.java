package br.com.zup.orangetalents.proposta.commom.exception;

import java.util.Collection;

public class ErroDto {
	
	private Collection<String> mensagens;

	public ErroDto(Collection<String> mensagens) {
		super();
		this.mensagens = mensagens;
	}

	public Collection<String> getMensagens() {
		return mensagens;
	}
}
