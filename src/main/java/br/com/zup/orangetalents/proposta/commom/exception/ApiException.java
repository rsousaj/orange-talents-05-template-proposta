package br.com.zup.orangetalents.proposta.commom.exception;

import org.springframework.http.HttpStatus;


public class ApiException extends RuntimeException {
	
	private static final long serialVersionUID = -6074429277143247974L;

	private HttpStatus status;
	
	public ApiException(HttpStatus status, String mensagem) {
		super(mensagem);
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return this.status;
	}
}
