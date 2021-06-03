package br.com.zup.orangetalents.proposta.controller;

import java.net.URI;

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
import br.com.zup.orangetalents.proposta.service.CriacaoProposta;

@RestController
@RequestMapping(value = "${proposta.novaproposta.uri}")
public class NovaPropostaController {
	
	private CriacaoProposta criacaoProposta;
	
	@Value("${proposta.detalhe.uri}")
	private String detalhePropostaUri;
	
	public NovaPropostaController(CriacaoProposta criacaoProposta) {
		this.criacaoProposta = criacaoProposta;
	}

	@PostMapping
	public ResponseEntity<?> cadastra(@RequestBody @Valid NovaPropostaRequest propostaRequest, UriComponentsBuilder uriBuilder) {
		Proposta proposta = criacaoProposta.cria(propostaRequest);
		
		URI uri = uriBuilder.path(detalhePropostaUri).build(proposta.getId());
		return ResponseEntity.created(uri).build();
	}
}
 