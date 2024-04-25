package br.com.dfdevforge.sisfinmaintenance.commons.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.dfdevforge.sisfinmaintenance.exceptions.EmailAlreadyRegisteredException;
import br.com.dfdevforge.sisfinmaintenance.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfinmaintenance.exceptions.UserNotFoundException;
import br.com.dfdevforge.sisfinmaintenance.exceptions.UserUnauthorizedException;

@ControllerAdvice
public class ResourceExceptionHandler {
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> httpNotFoundExceptionHandler(HttpStatusNotFound exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}

	@ExceptionHandler(UserUnauthorizedException.class)
	public ResponseEntity<String> httpUnauthorizedExceptionHandler(HttpStatusUnauthorized exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
	}

	@ExceptionHandler({RequiredFieldNotFoundException.class, EmailAlreadyRegisteredException.class})
	public ResponseEntity<String> httpInternalServerErrorExceptionHandler(HttpStatusInternalServerError exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
	}
}