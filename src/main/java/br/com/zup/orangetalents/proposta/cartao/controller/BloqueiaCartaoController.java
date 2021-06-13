package br.com.zup.orangetalents.proposta.cartao.controller;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orangetalents.proposta.cartao.model.Bloqueio;
import br.com.zup.orangetalents.proposta.cartao.model.Cartao;
import br.com.zup.orangetalents.proposta.cartao.repository.CartaoRepository;
import br.com.zup.orangetalents.proposta.commom.exception.ApiException;

@RestController
@RequestMapping(value = "${proposta.cartao.uri}")
public class BloqueiaCartaoController {

	private CartaoRepository cartaoRepository;
	private EntityManager entityManager;

	public BloqueiaCartaoController(CartaoRepository cartaoRepository, EntityManager entityManager) {
		this.cartaoRepository = cartaoRepository;
		this.entityManager = entityManager;
	}

	@PostMapping(value = "${proposta.cartao.bloqueio.uri}")
	@Transactional
	public ResponseEntity<?> bloqueia(@PathVariable("idCartao") String idCartao, HttpServletRequest httpRequest) {

		Cartao cartao = cartaoRepository.findById(idCartao)
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Cartão não encontrado."));

		if (cartao.isBloqueado()) {
			return ResponseEntity.unprocessableEntity().body("O cartão já encontra-se bloqueado.");
		}

		String userAgent = httpRequest.getHeader("User-Agent");
		String ip = httpRequest.getRemoteAddr();
		Bloqueio novoBloqueio = new Bloqueio(userAgent, ip, cartao);
		entityManager.persist(novoBloqueio);
		
		return ResponseEntity.ok().build();
	}
}
