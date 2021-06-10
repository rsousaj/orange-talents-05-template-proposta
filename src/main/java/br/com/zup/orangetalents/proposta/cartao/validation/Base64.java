package br.com.zup.orangetalents.proposta.cartao.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = Base64Validator.class)
public @interface Base64 {

	String message() default "{javax.validation.constraints.Base64.message}";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
