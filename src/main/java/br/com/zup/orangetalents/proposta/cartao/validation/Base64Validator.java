package br.com.zup.orangetalents.proposta.cartao.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

public class Base64Validator implements ConstraintValidator<Base64, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			byte[] decoded = getDecoder().decode(value);
			String encoded = getEncoder().encodeToString(decoded);
			
			if (!value.equals(encoded)) {
				return false;
			}
		} catch (IllegalArgumentException ex) {
			return false;
		}
		
		return true;
	}
}
