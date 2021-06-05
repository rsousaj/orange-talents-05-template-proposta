package br.com.zup.orangetalents.proposta.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.orangetalents.proposta.dto.request.SolicitacaoAnaliseRequest;
import br.com.zup.orangetalents.proposta.dto.response.ResultadoAnalise;

@FeignClient(name = "analiseCredito", url = "${servico.analise}")
public interface AnaliseCredito {

	@RequestMapping(value = "${servico.analise.solicitacao}", method = RequestMethod.POST)
	public ResultadoAnalise solictaAnalise(@RequestBody SolicitacaoAnaliseRequest solicitaoAnalise);
}
