package br.com.zup.orangetalents.proposta.cartao.controller;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.orangetalents.proposta.cartao.dto.BiometriaRequest;
import br.com.zup.orangetalents.proposta.cartao.model.Biometria;
import br.com.zup.orangetalents.proposta.cartao.model.Cartao;
import br.com.zup.orangetalents.proposta.cartao.repository.CartaoRepository;
import br.com.zup.orangetalents.proposta.commom.exception.ApiException;

@RestController
@RequestMapping(value = "${proposta.cartao.uri}")
public class NovaBiometriaController {

	private CartaoRepository cartaoRepository;
	private EntityManager entityManager;
	
	public NovaBiometriaController(CartaoRepository cartaoRepository, EntityManager entityManager) {
		this.cartaoRepository = cartaoRepository;
		this.entityManager = entityManager;
	}

	@PostMapping(value = "${proposta.cartao.biometria.uri}")
	@Transactional
	public ResponseEntity<?> cadastraBiometria(
			@PathVariable("idCartao") String idCartao,
			@RequestBody @Valid BiometriaRequest biometriaRequest,
			UriComponentsBuilder uriBuilder) {
		
		Cartao cartao = cartaoRepository.findById(idCartao)
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Esse cartão não existe."));
		
		Biometria biometria = biometriaRequest.toModel(cartao);
		entityManager.persist(biometria);
		
		URI uri = uriBuilder.path("/api/cartoes/{idCartao}/biometrias/{id}").build(idCartao, biometria.getId());
		return ResponseEntity.created(uri).build();
	}
}
