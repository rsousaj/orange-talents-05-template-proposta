package br.com.zup.orangetalents.proposta.cartao.metrics;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class MetricasCartao {

	private Counter contadorCartaoBloqueadoComSucesso;
	private Counter contadorCartaoErroBloquear;
	private Counter contadorCartaoAvisoViagemComSucesso;
	private Counter contadorCartaoErroAvisoViagem;

	public MetricasCartao(MeterRegistry meterRegistry) {
		inicializaMetrics(meterRegistry);
	}
	
	public void incrementaCartaoBloqueadoComSucesso() {
		this.contadorCartaoBloqueadoComSucesso.increment();
	}
	
	public void incrementaErroAoBloquearCartao() {
		this.contadorCartaoErroBloquear.increment();
	}
	
	public void incrementaAvisoViagemComSucesso() {
		this.contadorCartaoAvisoViagemComSucesso.increment();
	}
	
	public void incrementaAvisoViagemComErro() {
		this.contadorCartaoErroAvisoViagem.increment();
	}
	
	private void inicializaMetrics(MeterRegistry meterRegistry) {
		this.contadorCartaoBloqueadoComSucesso = Counter.builder("bloqueio.cartao")
				.tag("estado", "sucesso")
				.register(meterRegistry);
		
		this.contadorCartaoErroBloquear = Counter.builder("bloqueio.cartao")
				.tag("estado", "erro")
				.register(meterRegistry);
		
		this.contadorCartaoAvisoViagemComSucesso = Counter.builder("aviso.viagem")
				.tag("estado", "sucesso")
				.register(meterRegistry);
		
		this.contadorCartaoErroAvisoViagem = Counter.builder("aviso.viagem")
				.tag("estado", "erro")
				.register(meterRegistry);
	}
}
