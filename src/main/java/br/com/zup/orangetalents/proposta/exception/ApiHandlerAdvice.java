package br.com.zup.orangetalents.proposta.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiHandlerAdvice extends ResponseEntityExceptionHandler {

	private MessageSource messageSource;

	public ApiHandlerAdvice(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> erros = new ArrayList<>();
		
		ex.getBindingResult().getGlobalErrors().forEach(globalError -> {
			erros.add(globalError.getDefaultMessage());
		});

		ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
			String campo = fieldError.getField();
			String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

			erros.add(String.format("%s: %s", campo, mensagem));
		});

		return ResponseEntity.status(status).body(new ErroDto(erros));
	}
	
	@ExceptionHandler(ApiException.class)
	protected ResponseEntity<ErroDto> handleApiException(ApiException ex) {
		List<String> mensagens = new ArrayList<>();
		mensagens.add(ex.getMessage());
		
		return ResponseEntity.status(ex.getStatus()).body(new ErroDto(mensagens));
	}
}
