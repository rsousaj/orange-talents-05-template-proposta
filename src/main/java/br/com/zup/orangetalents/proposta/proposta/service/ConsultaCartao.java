package br.com.zup.orangetalents.proposta.proposta.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.zup.orangetalents.proposta.proposta.dto.response.CartaoResponse;

@FeignClient(name = "consultaCartao", url = "${servico.cartoes}")
public interface ConsultaCartao {

	@RequestMapping(method = RequestMethod.GET)
	CartaoResponse consulta(@RequestParam String idProposta);
}