package br.com.zup.orangetalents.proposta.cartao.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.zup.orangetalents.proposta.cartao.dto.request.NotificacaoAvisoViagemRequest;
import br.com.zup.orangetalents.proposta.cartao.dto.response.AvisoViagemResponse;

@FeignClient(name = "avisaViagem", url = "${servico.cartoes}")
public interface AvisoViagemClient {

	@PostMapping(path = "${servico.cartoes.avisos}")
	public AvisoViagemResponse cria(@PathVariable("id") String numeroCartao, @RequestBody NotificacaoAvisoViagemRequest avisoViagemRequest);
}
