package br.com.zup.orangetalents.proposta.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import br.com.zup.orangetalents.proposta.dto.NovaPropostaRequest;
import br.com.zup.orangetalents.proposta.dto.ResultadoAnalise;
import br.com.zup.orangetalents.proposta.dto.SolicitacaoAnaliseRequest;
import br.com.zup.orangetalents.proposta.exception.ApiException;
import br.com.zup.orangetalents.proposta.model.Proposta;
import br.com.zup.orangetalents.proposta.model.StatusProposta;
import br.com.zup.orangetalents.proposta.repository.PropostaRepository;
import feign.FeignException.UnprocessableEntity;

@Component
public class CriacaoPropostaImpl implements CriacaoProposta {
	
	private PropostaRepository propostaRepository;
	private AnaliseCredito analiseCredito;
	private TransactionTemplate transactionTemplate;
	
	public CriacaoPropostaImpl(PropostaRepository propostaRepository, 
			AnaliseCredito analiseCredito,
			PlatformTransactionManager transactionManager) {
		
		this.propostaRepository = propostaRepository;
		this.analiseCredito = analiseCredito;
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}
	
	@Override
	public Proposta cria(NovaPropostaRequest propostaRequest) {
		if (verificaSeJaExiste(propostaRequest.getDocumento())) {
			throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe proposta em análise para o CPF/CNPJ informado.");
		}
		
		Proposta proposta = (Proposta) transactionTemplate.execute((status) -> propostaRepository.save(propostaRequest.toModel()));
		
		try {
			ResultadoAnalise resultadoAnalise = analiseCredito.solictaAnalise(SolicitacaoAnaliseRequest.build(proposta));
			proposta.setStatus(resultadoAnalise.getResultadoSolicitacao().getStatus());		
		} catch (UnprocessableEntity ex) {
			proposta.setNaoElegivel();
		}
		
		transactionTemplate.execute((status) -> propostaRepository.save(proposta));
		
		return proposta;
	}
	
	public boolean verificaSeJaExiste(String documento) {
		return propostaRepository.findByDocumento(documento).isPresent();
	}
}
