package br.com.zup.orangetalents.proposta.cartao.controller;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Optionals;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orangetalents.proposta.cartao.dto.request.AvisoViagemRequest;
import br.com.zup.orangetalents.proposta.cartao.dto.response.AvisoViagemResponse;
import br.com.zup.orangetalents.proposta.cartao.metrics.MetricasCartao;
import br.com.zup.orangetalents.proposta.cartao.model.AvisoViagem;
import br.com.zup.orangetalents.proposta.cartao.model.Cartao;
import br.com.zup.orangetalents.proposta.cartao.repository.AvisoViagemRepository;
import br.com.zup.orangetalents.proposta.cartao.repository.CartaoRepository;
import br.com.zup.orangetalents.proposta.cartao.service.AvisoViagemClient;
import br.com.zup.orangetalents.proposta.commom.exception.ApiException;
import feign.FeignException;

@RestController
@RequestMapping(value = "${proposta.cartao.uri}")
public class NovoAvisoViagemController {

	private final Logger logger = LoggerFactory.getLogger(NovoAvisoViagemController.class);

	private AvisoViagemClient avisoViagemClient;
	private CartaoRepository cartaoRepository;
	private AvisoViagemRepository avisoViagemRepository;
	private MetricasCartao metricasCartao;

	public NovoAvisoViagemController(AvisoViagemClient avisoViagemClient, CartaoRepository cartaoRepository,
			AvisoViagemRepository avisoViagemRepository, MetricasCartao metricasCartao) {
		
		this.avisoViagemClient = avisoViagemClient;
		this.cartaoRepository = cartaoRepository;
		this.avisoViagemRepository = avisoViagemRepository;
		this.metricasCartao = metricasCartao;
	}

	@PostMapping(value = "${proposta.cartao.viagem.uri}")
	public ResponseEntity<?> cadastra(@PathVariable("idCartao") String idCartao,
			@RequestBody @Valid AvisoViagemRequest avisoRequest, HttpServletRequest httpRequest) {
		Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow(() -> {
			logger.error("Cartão {}... não encontrado", idCartao.substring(0, 8));
			return new ApiException(HttpStatus.NOT_FOUND, "Não foi possível proceder com a requisição.");
		});

		Optional<AvisoViagem> avisoViagem = geraAvisoViagem(cartao, avisoRequest, httpRequest);
		
		if (avisoViagem.isEmpty()) {
			logger.error("Erro ao gerar aviso viagem para o cartão {}...", cartao.getId().substring(0, 8));
			metricasCartao.incrementaAvisoViagemComErro();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível processar a requisição de aviso viagem. Tente novamente mais tarde.");
		}


		avisoViagemRepository.save(avisoViagem.get());
		metricasCartao.incrementaAvisoViagemComSucesso();

		return ResponseEntity.ok().build();
	}

	private Optional<AvisoViagem> geraAvisoViagem(Cartao cartao, AvisoViagemRequest avisoRequest,
			HttpServletRequest httpRequest) {
		try {
			AvisoViagemResponse response = avisoViagemClient.cria(cartao.getNumeroCartao(), avisoRequest.toNotificacaoRequest());

			if (response.getResultado().toUpperCase().equals("CRIADO")) {
				String ip = httpRequest.getRemoteAddr();
				String userAgent = httpRequest.getHeader("User-Agent");

				return Optional.of(avisoRequest.toModel(cartao, ip, userAgent));
			} else {
				return Optional.empty();
			}
		} catch (FeignException ex) {
			logger.error("Não foi possível gerar o aviso viagem para o cargão {}... Causa: {}", cartao.getId().substring(0, 8), ex.getMessage());
			return Optional.empty();
		}
	}
}
