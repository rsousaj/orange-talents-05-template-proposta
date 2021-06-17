package br.com.zup.orangetalents.proposta.proposta.validation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;

@Converter
public class CriptografadorCampo implements AttributeConverter<String, String> {
	
	@Value("${database.criptografia.secret}") 
	private String secret;
	
	@Value("${database.criptografia.salt}") 
	private String salt;
	
	@Override
	public String convertToDatabaseColumn(String attribute) {
		return Encryptors.queryableText(secret, salt).encrypt(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return Encryptors.queryableText(secret, salt).decrypt(dbData);
	}
}
