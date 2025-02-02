package br.com.zup.orangetalents.proposta.cartao.controller;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orangetalents.proposta.cartao.dto.request.BloqueioRequest;
import br.com.zup.orangetalents.proposta.cartao.dto.response.ResultadoBloqueio;
import br.com.zup.orangetalents.proposta.cartao.metrics.MetricasCartao;
import br.com.zup.orangetalents.proposta.cartao.model.Bloqueio;
import br.com.zup.orangetalents.proposta.cartao.model.Cartao;
import br.com.zup.orangetalents.proposta.cartao.service.CartoesClient;
import br.com.zup.orangetalents.proposta.commom.exception.ApiException;
import feign.FeignException;
import io.opentracing.Tracer;

@RestController
@RequestMapping(value = "${proposta.cartao.uri}")
public class BloqueiaCartaoController {
	
	private final Logger logger = LoggerFactory.getLogger(BloqueiaCartaoController.class);
	
	private EntityManager entityManager;
	private CartoesClient cartoes;
	private MetricasCartao metricasCartao;
	private TransactionTemplate transactionTemplate;
	private Tracer tracer;

	public BloqueiaCartaoController(EntityManager entityManager, 
			CartoesClient cartoesClient,
			MetricasCartao metricasCartao, 
			TransactionTemplate transactionTemplate,
			Tracer tracer) {
		
		this.entityManager = entityManager;
		this.cartoes = cartoesClient;
		this.metricasCartao = metricasCartao;
		this.transactionTemplate = transactionTemplate;
		this.tracer = tracer;
	}

	@PostMapping(value = "${proposta.cartao.bloqueio.uri}")
	public ResponseEntity<?> bloqueia(@PathVariable String idCartao, HttpServletRequest httpRequest) {

		Cartao cartao = entityManager.find(Cartao.class, idCartao);
		
		if (cartao == null) {
			logger.error("Cartão {}... não encontrado", idCartao);
			throw new ApiException(HttpStatus.NOT_FOUND, "Cartão não encontrado.");
		}

		if (cartao.isBloqueado()) {
			logger.info("Tentativa de bloqueio de cartão {}... não foi processada: cartão já encontra-se bloqueado.", idCartao);
			return ResponseEntity.unprocessableEntity().body("O cartão já encontra-se bloqueado.");
		}

		Bloqueio novoBloqueio = geraBloqueio(cartao, httpRequest)
				.orElseThrow(() -> new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível processar o bloqueio do cartão. Tente novamente mais tarde."));
		
		cartao.bloqueia();
		transactionTemplate.executeWithoutResult((status) -> entityManager.persist(novoBloqueio));
		metricasCartao.incrementaCartaoBloqueadoComSucesso();
		
		return ResponseEntity.ok().build();
	}

	private Optional<Bloqueio> geraBloqueio(Cartao cartao, HttpServletRequest httpRequest) {
		tracer.activeSpan().setBaggageItem("cartao-id", cartao.getId());
		try {
			ResultadoBloqueio resultado = cartoes.bloqueiaCartao(cartao.getNumeroCartao(), BloqueioRequest.from("proposta"));

			String userAgent = httpRequest.getHeader("User-Agent");
			String ip = httpRequest.getRemoteAddr();
			
			return Optional.of(new Bloqueio(userAgent, ip, cartao));
		} catch (FeignException ex) {	
			metricasCartao.incrementaErroAoBloquearCartao();
			logger.error("Não foi possível processar o bloqueio do cartão ID: {}...", cartao.getId());
			
			return Optional.empty();
		}
	}
}
