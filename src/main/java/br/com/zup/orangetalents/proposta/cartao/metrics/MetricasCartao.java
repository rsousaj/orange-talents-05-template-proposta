package br.com.zup.orangetalents.proposta.cartao.metrics;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class MetricasCartao {

	private Counter contadorCartaoBloqueadoComSucesso;
	private Counter contadorCartaoErroBloquear;

	public MetricasCartao(MeterRegistry meterRegistry) {
		inicializaMetrics(meterRegistry);
	}
	
	public void incrementaCartaoBloqueadoComSucesso() {
		this.contadorCartaoBloqueadoComSucesso.increment();
	}
	
	public void incrementaErroAoBloquearCartao() {
		this.contadorCartaoErroBloquear.increment();
	}
	
	private void inicializaMetrics(MeterRegistry meterRegistry) {
		this.contadorCartaoBloqueadoComSucesso = Counter.builder("bloqueio.cartao")
				.tag("estado", "sucesso")
				.register(meterRegistry);
		
		this.contadorCartaoErroBloquear = Counter.builder("bloqueio.cartao")
				.tag("estado", "erro")
				.register(meterRegistry);
	}
}
