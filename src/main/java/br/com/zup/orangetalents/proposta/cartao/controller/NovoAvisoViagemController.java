package br.com.zup.orangetalents.proposta.cartao.controller;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orangetalents.proposta.cartao.dto.request.AvisoViagemRequest;
import br.com.zup.orangetalents.proposta.cartao.model.AvisoViagem;
import br.com.zup.orangetalents.proposta.cartao.model.Cartao;
import br.com.zup.orangetalents.proposta.commom.exception.ApiException;

@RestController
@RequestMapping(value = "${proposta.cartao.uri}")
public class NovoAvisoViagemController {
	
	private final Logger logger = LoggerFactory.getLogger(NovoAvisoViagemController.class);
	private EntityManager entityManager;

	public NovoAvisoViagemController(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@PostMapping(value = "${proposta.cartao.viagem.uri}")
	@Transactional
	public ResponseEntity<?> cadastra(@PathVariable("idCartao") String idCartao, @RequestBody @Valid AvisoViagemRequest avisoRequest, HttpServletRequest httpRequest) {
		Cartao cartao = entityManager.find(Cartao.class, idCartao);
		
		if (cartao == null) {
			logger.error("Cartão {}... não encontrado", idCartao.substring(0, 8));
			throw new ApiException(HttpStatus.NOT_FOUND, "Cartão não encontrado.");
		}
		
		String ip = httpRequest.getRemoteAddr();
		String userAgent = httpRequest.getHeader("User-Agent");
		AvisoViagem avisoViagem = avisoRequest.toModel(cartao, ip, userAgent);
		entityManager.persist(avisoViagem);
		
		return ResponseEntity.ok().build();
	}
}
