package br.com.zup.orangetalents.proposta.proposta.dto.response;

import br.com.zup.orangetalents.proposta.proposta.model.StatusProposta;

public enum ResultadoSolicitacao {
	COM_RESTRICAO {
		public StatusProposta getStatus() {
			return StatusProposta.NAO_ELEGIVEL;
		}
	}, 
	
	SEM_RESTRICAO {
		public StatusProposta getStatus() {
			return StatusProposta.ELEGIVEL;
		}
	};
	
	public abstract StatusProposta getStatus();
}
