package br.com.zup.orangetalents.proposta.proposta.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orangetalents.proposta.commom.exception.ApiException;
import br.com.zup.orangetalents.proposta.proposta.dto.response.DetalhePropostaResponse;
import br.com.zup.orangetalents.proposta.proposta.model.Proposta;
import br.com.zup.orangetalents.proposta.proposta.repository.PropostaRepository;

@RestController
@RequestMapping
public class ConsultaPropostaController {

	private PropostaRepository propostaRepository;
	
	public ConsultaPropostaController(PropostaRepository propostaRepository) {
		this.propostaRepository = propostaRepository;
	}

	@GetMapping(value = "${proposta.detalhe.uri}")
	public ResponseEntity<DetalhePropostaResponse> consulta(@PathVariable("id") String id) {
		Proposta proposta = propostaRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Proposta não localizada."));
		
		return ResponseEntity.ok().body(DetalhePropostaResponse.build(proposta));
	}
	
}
