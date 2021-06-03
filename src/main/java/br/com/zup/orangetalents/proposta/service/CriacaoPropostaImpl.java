package br.com.zup.orangetalents.proposta.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.zup.orangetalents.proposta.dto.NovaPropostaRequest;
import br.com.zup.orangetalents.proposta.exception.ApiException;
import br.com.zup.orangetalents.proposta.model.Proposta;
import br.com.zup.orangetalents.proposta.repository.PropostaRepository;

@Component
public class CriacaoPropostaImpl implements CriacaoProposta {
	
	private PropostaRepository propostaRepository;
	
	public CriacaoPropostaImpl(PropostaRepository propostaRepository) {
		this.propostaRepository = propostaRepository;
	}

	@Override
	public Proposta cria(NovaPropostaRequest propostaRequest) {
		if (verificaSeJaExiste(propostaRequest.getDocumento())) {
			throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe proposta em análise para o CPF/CNPJ informado.");
		}
		
		return propostaRepository.save(propostaRequest.toModel());
	}
	
	public boolean verificaSeJaExiste(String documento) {
		return propostaRepository.findByDocumento(documento).isPresent();
	}
}
