package br.com.zup.orangetalents.proposta.cartao.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.zup.orangetalents.proposta.cartao.dto.request.AssociaCarteiraRequest;
import br.com.zup.orangetalents.proposta.cartao.dto.request.BloqueioRequest;
import br.com.zup.orangetalents.proposta.cartao.dto.request.NotificacaoAvisoViagemRequest;
import br.com.zup.orangetalents.proposta.cartao.dto.response.AssociaCarteiraResponse;
import br.com.zup.orangetalents.proposta.cartao.dto.response.AvisoViagemResponse;
import br.com.zup.orangetalents.proposta.cartao.dto.response.ResultadoBloqueio;
import br.com.zup.orangetalents.proposta.proposta.dto.response.CartaoResponse;

@FeignClient(name = "avisaViagem", url = "${servico.cartoes}")
public interface CartoesClient {

	@GetMapping
	CartaoResponse consulta(@RequestParam String idProposta);
	
	@PostMapping(path = "${servico.cartoes.carteiras}")
	public AssociaCarteiraResponse associaCarteira(@PathVariable("id") String numeroCartao, @RequestBody AssociaCarteiraRequest associaCarteiraRequest);
	
	@PostMapping(path = "${servico.cartoes.bloqueio}")
	public ResultadoBloqueio bloqueiaCartao(@PathVariable("id") String idCartao, @RequestBody BloqueioRequest bloqueioRequest);
	
	@PostMapping(path = "${servico.cartoes.avisos}")
	public AvisoViagemResponse criaAvisoViagem(@PathVariable("id") String numeroCartao, @RequestBody NotificacaoAvisoViagemRequest avisoViagemRequest);
}
