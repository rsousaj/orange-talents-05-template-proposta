package br.com.zup.orangetalents.proposta.proposta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.zup.orangetalents.proposta.proposta.dto.request.SolicitacaoAnaliseRequest;
import br.com.zup.orangetalents.proposta.proposta.dto.response.ResultadoAnalise;
import br.com.zup.orangetalents.proposta.proposta.model.Proposta;
import feign.FeignException;
import feign.FeignException.UnprocessableEntity;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class AnalisadorPropostaImpl implements AnalisadorProposta {

	private final Logger logger = LoggerFactory.getLogger(AnalisadorPropostaImpl.class);
	
	private AnaliseCreditoClient analiseCredito;
	private Counter contadorAnaliseCreditoElegivel;
	private Counter contadorAnaliseCreditoNaoElegivel;
	private Counter contadorAnaliseCreditoFalha;

	public AnalisadorPropostaImpl(AnaliseCreditoClient analiseCredito, MeterRegistry meterRegistry) {
		this.analiseCredito = analiseCredito;
		inicializaMetricas(meterRegistry);
	}

	public void analisar(Proposta proposta) {
		try {
			ResultadoAnalise resultadoAnalise = analiseCredito.solicitaAnalise(SolicitacaoAnaliseRequest.build(proposta));
			proposta.setStatus(resultadoAnalise.getResultadoSolicitacao().getStatus());
			contadorAnaliseCreditoElegivel.increment();
		} catch (UnprocessableEntity ex) {
			proposta.setNaoElegivel();
			contadorAnaliseCreditoNaoElegivel.increment();
		} catch (FeignException ex) {
			proposta.setNaoElegivel();
			contadorAnaliseCreditoFalha.increment();
			logger.error("Ocorreu um erro ao fazer solicitação de análise de credito: {}", ex.getMessage());
		}
	}
	
	private void inicializaMetricas(MeterRegistry meterRegistry) {
		this.contadorAnaliseCreditoElegivel = Counter.builder("analise.credito")
				.tag("estado", "elegivel")
				.register(meterRegistry);
		
		this.contadorAnaliseCreditoNaoElegivel = Counter.builder("analise.credito")
				.tag("estado", "nao_elegivel")
				.register(meterRegistry);
		
		this.contadorAnaliseCreditoFalha = Counter.builder("analise.credito")
				.tag("estado", "falhou")
				.register(meterRegistry);
	}
}
