package br.com.zup.orangetalents.proposta.proposta.service;

import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.zup.orangetalents.proposta.cartao.repository.CartaoRepository;
import br.com.zup.orangetalents.proposta.proposta.dto.response.CartaoResponse;
import br.com.zup.orangetalents.proposta.proposta.model.Proposta;
import br.com.zup.orangetalents.proposta.proposta.repository.PropostaRepository;
import feign.FeignException;
import feign.RetryableException;

@Component
public class AssociadorCartaoProposta {

	private final Logger logger = LoggerFactory.getLogger(AssociadorCartaoProposta.class);
	
	private ConsultaCartao consultaCartao;
	private PropostaRepository propostaRepository;
	private CartaoRepository cartaoRepository;
	
	public AssociadorCartaoProposta(ConsultaCartao consultaCartao, 
			PropostaRepository propostaRepository,
			CartaoRepository cartaoRepository) {
		
		this.consultaCartao = consultaCartao;
		this.propostaRepository = propostaRepository;
		this.cartaoRepository = cartaoRepository;
	}

	@Scheduled(fixedDelayString = "${servico.cartoes.periodicidade}")
	public void executa() {
		List<Proposta> propostasAguardandoCartao = propostaRepository.findByStatusIsElegivelAndCartaoIsNull();
		
		propostasAguardandoCartao.forEach(this::executa);
	}
	
	public void executa(Proposta proposta) {
		try {
			CartaoResponse cartaoResponse = consultaCartao.consulta(proposta.getId().toString());
			cartaoRepository.save(cartaoResponse.toModel(proposta));

		} catch (RetryableException ex) {
			logger.error("Ocorreu um erro ao tentar consultar o Serviço de Cartões: {}", ex.getMessage());
		} catch (FeignException ex) {
			logger.error("Não foi possível localizar cartão para a proposta {}", proposta.getId());
		}
	}
}
