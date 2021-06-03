package br.com.zup.orangetalents.proposta.controller;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.orangetalents.proposta.dto.NovaPropostaRequest;
import br.com.zup.orangetalents.proposta.model.Proposta;

@RestController
@RequestMapping(value = "${proposta.novaproposta.uri}")
public class NovaPropostaController {
	
	private EntityManager entityManager;
	
	@Value("${proposta.detalhe.uri}")
	private String detalhePropostaUri;
	
	public NovaPropostaController(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastra(@RequestBody @Valid NovaPropostaRequest propostaRequest, UriComponentsBuilder uriBuilder) {
		Proposta proposta = propostaRequest.toModel();
		entityManager.persist(proposta);
		
		URI uri = uriBuilder.path(detalhePropostaUri).build(proposta.getId());
		return ResponseEntity.created(uri).build();
	}
}
 