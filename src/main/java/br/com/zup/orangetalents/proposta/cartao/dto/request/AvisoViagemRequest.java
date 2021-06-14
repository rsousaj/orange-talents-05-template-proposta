package br.com.zup.orangetalents.proposta.cartao.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zup.orangetalents.proposta.cartao.model.AvisoViagem;
import br.com.zup.orangetalents.proposta.cartao.model.Cartao;

public class AvisoViagemRequest {

	private final String formatoData = "dd/MM/yyyy";

	@NotBlank
	private String destino;

	@NotNull
	@Future
	@JsonFormat(pattern = formatoData)
	private LocalDate dataTermino;

	@JsonCreator
	public AvisoViagemRequest(@NotBlank String destino, @NotNull @Future LocalDate dataTermino) {
		this.destino = destino;
		this.dataTermino = dataTermino;
	}

	public AvisoViagem toModel(Cartao cartao, String ipRequisicao, String userAgentRequisicao) {
		Assert.state(cartao != null, "Deve ser informado um cartão existente.");
		
		return new AvisoViagem(cartao, this.destino, this.dataTermino, ipRequisicao, userAgentRequisicao);
	}
	
	public NotificacaoAvisoViagemRequest toNotificacaoRequest() {
		return new NotificacaoAvisoViagemRequest(this.destino, this.dataTermino);
	}

	@Override
	public String toString() {
		return "AvisoViagemRequest [destino=" + destino + ", dataTermino=" + dataTermino + "]";
	}

}
