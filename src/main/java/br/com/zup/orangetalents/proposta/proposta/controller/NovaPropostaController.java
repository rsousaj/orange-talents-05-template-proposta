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
import br.com.zup.orangetalents.proposta.proposta.dto.request.SolicitacaoAnaliseRequest;
import br.com.zup.orangetalents.proposta.proposta.dto.response.ResultadoAnalise;
import br.com.zup.orangetalents.proposta.proposta.model.Proposta;
import br.com.zup.orangetalents.proposta.proposta.repository.PropostaRepository;
import br.com.zup.orangetalents.proposta.proposta.service.AnaliseCredito;
import feign.FeignException;
import feign.FeignException.UnprocessableEntity;

@RestController
@RequestMapping(value = "${proposta.proposta.uri}")
public class NovaPropostaController {
	
	private final Logger logger = LoggerFactory.getLogger(NovaPropostaController.class);
	
	private PropostaRepository propostaRepository;
	private AnaliseCredito analiseCredito;
	
	@Value("${proposta.detalhe.uri}")
	private String detalhePropostaUri;
	
	public NovaPropostaController(PropostaRepository propostaRepository, AnaliseCredito analiseCredito) {
		this.propostaRepository = propostaRepository;
		this.analiseCredito = analiseCredito;
	}
	
	@PostMapping
	public ResponseEntity<?> cadastra(@RequestBody @Valid NovaPropostaRequest propostaRequest, UriComponentsBuilder uriBuilder) {	
		Proposta proposta = criaProposta(propostaRequest);
		
		analisaProposta(proposta);
		propostaRepository.save(proposta);
		
		URI uri = uriBuilder.path(detalhePropostaUri).build(proposta.getId().toString());	
		return ResponseEntity.created(uri).build();
	}
	
	private Proposta criaProposta(NovaPropostaRequest propostaRequest) {
		 Optional<Proposta> proposta = propostaRepository.findByDocumento(propostaRequest.getDocumento());
		 
		 if (proposta.isPresent()) {
			 throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe proposta em análise para o CPF/CNPJ informado.");
		 }
		
		 return propostaRepository.save(propostaRequest.toModel());
	}
	
	private void analisaProposta(Proposta proposta) {
		try {
			ResultadoAnalise resultadoAnalise = analiseCredito.solictaAnalise(SolicitacaoAnaliseRequest.build(proposta));
			proposta.setStatus(resultadoAnalise.getResultadoSolicitacao().getStatus());		
		} catch (UnprocessableEntity ex) {
			proposta.setNaoElegivel();
		} catch (FeignException ex) {
			logger.error("Ocorreu um erro ao fazer solicitação de análise de credito: {}", ex.getMessage());
		}
	}
}