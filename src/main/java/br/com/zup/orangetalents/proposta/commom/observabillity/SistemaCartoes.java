package br.com.zup.orangetalents.proposta.commom.observabillity;

import java.net.Socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component(value = "sistemaCartoes")
public class SistemaCartoes implements HealthIndicator {

	@Value("${servico.cartoes}")
	private String URL;
	
	@Override
	public Health health() {
        try (Socket socket = new Socket(new java.net.URL(URL).getHost(), 8888)) {
        } catch (Exception e) {
            return Health.down().withException(e).build();
        }
        return Health.up().build();
	}

}
