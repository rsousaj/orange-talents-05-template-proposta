package br.com.zup.orangetalents.proposta.cartao.controller;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orangetalents.proposta.cartao.dto.request.BloqueioRequest;
import br.com.zup.orangetalents.proposta.cartao.dto.response.ResultadoBloqueio;
import br.com.zup.orangetalents.proposta.cartao.model.Bloqueio;
import br.com.zup.orangetalents.proposta.cartao.model.Cartao;
import br.com.zup.orangetalents.proposta.cartao.service.BloqueiaCartao;
import br.com.zup.orangetalents.proposta.commom.exception.ApiException;
import feign.FeignException;

@RestController
@RequestMapping(value = "${proposta.cartao.uri}")
public class BloqueiaCartaoController {
	
	private final Logger logger = LoggerFactory.getLogger(BloqueiaCartaoController.class);
	
	private EntityManager entityManager;
	private BloqueiaCartao bloqueiaCartao;
	private TransactionTemplate transactionTemplate;

	public BloqueiaCartaoController(EntityManager entityManager,
			BloqueiaCartao bloqueiaCartao,
			PlatformTransactionManager transactionManager) {
		
		this.entityManager = entityManager;
		this.bloqueiaCartao = bloqueiaCartao;
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	@PostMapping(value = "${proposta.cartao.bloqueio.uri}")
	public ResponseEntity<?> bloqueia(@PathVariable("idCartao") String idCartao, HttpServletRequest httpRequest) {

		Cartao cartao = entityManager.find(Cartao.class, idCartao);
		
		if (cartao == null) {
			logger.error("Cartão {}... não encontrado", idCartao.substring(0, 8));
			throw new ApiException(HttpStatus.NOT_FOUND, "Cartão não encontrado.");
		}

		if (cartao.isBloqueado()) {
			logger.info("Tentativa de bloqueio de cartão {}... não foi processada: cartão já encontra-se bloqueado.", idCartao.substring(0, 8));
			return ResponseEntity.unprocessableEntity().body("O cartão já encontra-se bloqueado.");
		}

		Bloqueio novoBloqueio = geraBloqueio(cartao, httpRequest);
		cartao.bloqueia();
		transactionTemplate.executeWithoutResult((status) -> entityManager.persist(novoBloqueio));
		
		return ResponseEntity.ok().build();
	}

	private Bloqueio geraBloqueio(Cartao cartao, HttpServletRequest httpRequest) {
		try {
			ResultadoBloqueio resultado = bloqueiaCartao.bloqueia(cartao.getNumeroCartao(), BloqueioRequest.from("proposta"));
			
			String userAgent = httpRequest.getHeader("User-Agent");
			String ip = httpRequest.getRemoteAddr();
			return new Bloqueio(userAgent, ip, cartao);
			
		} catch (FeignException ex) {
			logger.error("Não foi possível processar o bloqueio do cartão ID: {}...", cartao.getId().substring(0, 8));
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível processar o bloqueio do cartão. Tente novamente mais tarde. Message: " + ex.getMessage());
		}
	}
}
