package br.com.zup.orangetalents.proposta.proposta.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import br.com.zup.orangetalents.proposta.proposta.model.Proposta;
import br.com.zup.orangetalents.proposta.proposta.validation.CPForCNPJ;

public class NovaPropostaRequest {

	private static final String SALT = "bdb2e0ff375d16cb";
	
	private @NotBlank @CPForCNPJ String documento;
	private @NotBlank String email;
	private @NotBlank String nome;
	private @NotBlank String endereco;
	private @NotNull @Positive BigDecimal salario;
	
	public NovaPropostaRequest(@NotBlank String documento, @NotBlank String email, @NotBlank String nome,
			@NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
		super();
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}

	public Proposta toModel() {
		return new Proposta(this.getDocumento(), email, nome, endereco, salario);
	}

	public String getDocumento() {
		return this.documento;
//		Faz a cripto
//		return this.criptografa(documento);
	}
	
	public String criptografa(String documento) {
		TextEncryptor encryptor = Encryptors.queryableText("documento", SALT);
		return encryptor.encrypt(documento);
	}
}
