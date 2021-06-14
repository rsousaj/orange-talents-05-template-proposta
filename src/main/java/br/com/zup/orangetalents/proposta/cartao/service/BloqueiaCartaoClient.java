package br.com.zup.orangetalents.proposta.cartao.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.orangetalents.proposta.cartao.dto.request.BloqueioRequest;
import br.com.zup.orangetalents.proposta.cartao.dto.response.ResultadoBloqueio;

@FeignClient(name = "bloqueiaCartao", url = "${servico.cartoes}")
public interface BloqueiaCartaoClient {

	@RequestMapping(path = "${servico.cartoes.bloqueio}", method = RequestMethod.POST)
	public ResultadoBloqueio bloqueia(@PathVariable("id") String idCartao, @RequestBody BloqueioRequest bloqueioRequest);
}