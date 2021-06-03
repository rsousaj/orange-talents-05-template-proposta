package br.com.zup.orangetalents.proposta.service;

import br.com.zup.orangetalents.proposta.dto.NovaPropostaRequest;
import br.com.zup.orangetalents.proposta.model.Proposta;

public interface CriacaoProposta {

	Proposta cria(NovaPropostaRequest novoProposta);
}
