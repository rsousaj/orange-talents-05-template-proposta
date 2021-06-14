package br.com.zup.orangetalents.proposta.cartao.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NotificacaoAvisoViagemRequest {
	
	@NotBlank
	private String destino;

	@NotNull
	@Future
	private LocalDate validoAte;

	public NotificacaoAvisoViagemRequest(@NotBlank String destino, @NotNull @Future LocalDate validoAte) {
		this.destino = destino;
		this.validoAte = validoAte;
	}

	@Override
	public String toString() {
		return "NotificacaoAvisoViagemRequest [destino=" + destino + ", validoAte=" + validoAte + "]";
	}

	public String getDestino() {
		return destino;
	}

	public LocalDate getValidoAte() {
		return validoAte;
	}
}
