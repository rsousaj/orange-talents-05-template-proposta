package br.com.zup.orangetalents.proposta.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.zup.orangetalents.proposta.dto.CartaoResponse;
import br.com.zup.orangetalents.proposta.model.Proposta;
import br.com.zup.orangetalents.proposta.repository.PropostaRepository;
import feign.FeignException;
import feign.RetryableException;

@Component
public class AssociadorCartaoProposta {

	private final Logger logger = LoggerFactory.getLogger(AssociadorCartaoProposta.class);
	
	private ConsultaCartao consultaCartao;
	private PropostaRepository propostaRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public AssociadorCartaoProposta(ConsultaCartao consultaCartao, PropostaRepository propostaRepository) {
		this.consultaCartao = consultaCartao;
		this.propostaRepository = propostaRepository;
	}

	@Scheduled(fixedDelayString = "${servico.cartoes.periodicidade}")
	@Transactional
	public void executa() {
		List<Proposta> propostasAguardandoCartao = propostaRepository.findByStatusIsElegivelAndCartaoIsNull();
		
		propostasAguardandoCartao.forEach(this::executa);
	}
	
	public void executa(Proposta proposta) {
		try {
			CartaoResponse cartaoResponse = consultaCartao.consulta(proposta.getId());
			System.out.println(cartaoResponse);
			entityManager.persist(cartaoResponse.toModel(proposta));
			
		} catch (RetryableException ex) {
			logger.error("Ocorreu um erro ao tentar consultar o Serviço de Cartões: {}", ex.getMessage());
		} catch (FeignException ex) {
			logger.error("Não foi possível localizar cartão para a proposta {}", proposta.getId());
		}
	}
}
