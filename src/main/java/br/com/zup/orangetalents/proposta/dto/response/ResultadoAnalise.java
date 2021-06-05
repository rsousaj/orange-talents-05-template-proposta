package br.com.zup.orangetalents.proposta.dto.response;

public class ResultadoAnalise {

	private String documento;
	private String nome;
	private ResultadoSolicitacao resultadoSolicitacao;
	private String idProposta;
	
	public ResultadoAnalise(String documento, String nome, ResultadoSolicitacao resultadoSolicitacao,
			String idProposta) {
		super();
		this.documento = documento;
		this.nome = nome;
		this.resultadoSolicitacao = resultadoSolicitacao;
		this.idProposta = idProposta;
	}
	
	public ResultadoSolicitacao getResultadoSolicitacao() {
		return this.resultadoSolicitacao;
	}
}