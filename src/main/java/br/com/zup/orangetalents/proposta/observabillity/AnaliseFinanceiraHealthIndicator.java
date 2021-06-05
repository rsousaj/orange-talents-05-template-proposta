package br.com.zup.orangetalents.proposta.observabillity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component(value = "analiseFinanceira")
public class AnaliseFinanceiraHealthIndicator implements HealthIndicator {

	private WebClient webClient;
	private final String analiseFinanceiraHealthEndpoint;
	
	public AnaliseFinanceiraHealthIndicator(WebClient.Builder builder, @Value("${servico.analise.health}") String analiseFinanceiraHealthEndpoint) {		
		webClient = builder.baseUrl(analiseFinanceiraHealthEndpoint).build();
		this.analiseFinanceiraHealthEndpoint = analiseFinanceiraHealthEndpoint;
	}

	
	@Override
	public Health health() {
		Map<String, String> details = new HashMap<>();
		details.put("service", "analisaFinanceira");
		details.put("endpoint", analiseFinanceiraHealthEndpoint);
		
		try {
			HttpStatus status = webClient.get().retrieve()
					.toBodilessEntity()
					.block()
					.getStatusCode();
			
			if (status.is2xxSuccessful()) {
				return Health.up().withDetails(details).build();
			}
			
			details.put("reason", status.getReasonPhrase().toString());
			return Health.down().withDetails(details).build();
		} catch (Exception ex) {
			details.put("reason", ex.getMessage());
			return Health.down().withDetails(details).build();
		}
	}
}
