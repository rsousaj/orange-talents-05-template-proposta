package br.com.zup.orangetalents.proposta.proposta.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.orangetalents.proposta.commom.exception.ApiException;
import br.com.zup.orangetalents.proposta.proposta.dto.request.NovaPropostaRequest;
import br.com.zup.orangetalents.proposta.proposta.model.Proposta;
import br.com.zup.orangetalents.proposta.proposta.repository.PropostaRepository;
import br.com.zup.orangetalents.proposta.proposta.service.AnalisadorProposta;

@RestController
@RequestMapping(value = "${proposta.proposta.uri}")
public class NovaPropostaController {
	
	private final Logger logger = LoggerFactory.getLogger(NovaPropostaController.class);
	
	private PropostaRepository propostaRepository;
	private AnalisadorProposta analisadorProposta;
	
	@Value("${proposta.detalhe.uri}")
	private String detalhePropostaUri;
	
	public NovaPropostaController(PropostaRepository propostaRepository, AnalisadorProposta analisadorProposta) {
		this.propostaRepository = propostaRepository;
		this.analisadorProposta = analisadorProposta;
	}
	
	@PostMapping
	public ResponseEntity<?> cadastra(@RequestBody @Valid NovaPropostaRequest propostaRequest, UriComponentsBuilder uriBuilder) {	
		Proposta proposta = criaProposta(propostaRequest);
		
		analisadorProposta.analisar(proposta);
		propostaRepository.save(proposta);
		
		URI uri = uriBuilder.path(detalhePropostaUri).build(proposta.getId().toString());	
		return ResponseEntity.created(uri).build();
	}
	
	private Proposta criaProposta(NovaPropostaRequest propostaRequest) {
		 Optional<Proposta> possivelProposta = propostaRepository.findByDocumento(propostaRequest.getDocumento());
		 
		 if (possivelProposta.isPresent()) {
			 throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe proposta em análise para o CPF/CNPJ informado.");
		 }
		
		 Proposta proposta = propostaRepository.save(propostaRequest.toModel());
		 logger.info("Proposta {} criada com sucesso.", proposta.getId());
		 return proposta;
	}
}