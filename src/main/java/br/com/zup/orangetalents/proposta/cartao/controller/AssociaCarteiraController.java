package br.com.zup.orangetalents.proposta.cartao.controller;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.orangetalents.proposta.cartao.dto.request.AssociaCarteiraRequest;
import br.com.zup.orangetalents.proposta.cartao.dto.response.AssociaCarteiraResponse;
import br.com.zup.orangetalents.proposta.cartao.metrics.MetricasCartao;
import br.com.zup.orangetalents.proposta.cartao.model.Cartao;
import br.com.zup.orangetalents.proposta.cartao.model.Carteira;
import br.com.zup.orangetalents.proposta.cartao.repository.CartaoRepository;
import br.com.zup.orangetalents.proposta.cartao.repository.CarteiraRepository;
import br.com.zup.orangetalents.proposta.cartao.service.CartoesClient;
import br.com.zup.orangetalents.proposta.commom.exception.ApiException;
import feign.FeignException;
import io.opentracing.Tracer;

@RestController
@RequestMapping(value = "${proposta.cartao.uri}")
public class AssociaCarteiraController {

	private final Logger logger = LoggerFactory.getLogger(AssociaCarteiraController.class);

	private final CarteiraRepository carteiraRepository;
	private final CartaoRepository cartaoRepository;
	private final CartoesClient cartoes;
	private final MetricasCartao metricas;
	private final Tracer tracer;

	@Value("${proposta.cartao.carteirashabilitadas}")
	private Set<String> carteirasHabilitadas;

	public AssociaCarteiraController(CarteiraRepository carteiraRepository, 
			CartaoRepository cartaoRepository,
			CartoesClient cartoes, 
			MetricasCartao metricas,
			Tracer tracer) {
		
		this.carteiraRepository = carteiraRepository;
		this.cartaoRepository = cartaoRepository;
		this.cartoes = cartoes;
		this.metricas = metricas;
		this.tracer = tracer;
	}

	@PostMapping(path = "${proposta.cartao.carteira.uri}")
	public ResponseEntity<?> associa(@PathVariable String idCartao, @PathVariable("carteira") String carteira,
			@RequestBody @NotNull @NotBlank @Email String email, UriComponentsBuilder uriBuilder) {

		if (!existeCarteiraDigital(carteira)) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Carteira inexistente!");
		}

		Cartao cartao = cartaoRepository.findById(idCartao)
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Não foi possível processar a requisição."));

		Optional<Carteira> possivelCarteira = carteiraRepository.findByCartao_IdAndNomeCarteira(idCartao, carteira);

		if (possivelCarteira.isPresent()) {
			return ResponseEntity.unprocessableEntity().body("Este cartão já foi associada a Carteira.");
		}

		if (!associaCarteiraDigital(cartao, carteira, email)) {
			metricas.incrementaAssociaCartaoComErro();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Não foi possível fazer a associação. Tente novamente mais tarde.");
		}

		carteiraRepository.save(new Carteira(carteira, cartao));
		metricas.incrementaAssociaCartaoComSucesso();
		
		URI uri = uriBuilder.path("/api/cartoes/{idCartao}/carteiras/{carteira}").build().toUri();
		return ResponseEntity.created(uri).build();
	}

	private boolean associaCarteiraDigital(Cartao cartao, String carteira, String email) {
		tracer.activeSpan().setTag("carteira", carteira);
		tracer.activeSpan().setBaggageItem("cartao-id", cartao.getId());
		try {
			AssociaCarteiraResponse response = cartoes.associaCarteira(cartao.getNumeroCartao(),
					AssociaCarteiraRequest.from(email, carteira));

			if (response.getResultado().toUpperCase().equals("ASSOCIADA")) {
				return true;
			}
		} catch (FeignException ex) {
			logger.error("Não foi possível associar a o cartao de id {}... com a carteira {}",
					cartao.getId().substring(0, 8), carteira);
			return false;
		}
		return false;
	}

	private boolean existeCarteiraDigital(String carteiraProcurada) {
		Assert.state(carteirasHabilitadas != null, "Não existem carteiras habilitadas!");

		return carteirasHabilitadas.stream()
				.anyMatch(carteira -> carteira.toUpperCase().equals(carteiraProcurada.toUpperCase()));
	}
}
