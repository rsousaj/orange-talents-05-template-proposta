package br.com.zup.orangetalents.proposta.proposta.validation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Converter
public class CriptografadorCampo implements AttributeConverter<String, String> {

	private final TextEncryptor encryptor;
	
	public CriptografadorCampo(@Value("${database.criptografia.secret}") String secret,
			@Value("${database.criptografia.salt}") String salt) {
		
		this.encryptor = Encryptors.queryableText(secret, salt);
	}
	
	@Override
	public String convertToDatabaseColumn(String attribute) {
		return encryptor.encrypt(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return encryptor.decrypt(dbData);
	}
}
