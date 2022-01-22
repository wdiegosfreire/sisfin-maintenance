package br.com.dfdevforge.sisfinmaintenance.exceptions;

import java.util.List;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.sisfinmaintenance.commons.exceptions.HttpStatusInternalServerError;

public class EmailAlreadyRegisteredException extends BaseException implements HttpStatusInternalServerError {
	private static final long serialVersionUID = 1L;

	public EmailAlreadyRegisteredException() {
		super("Email Already Registered", "The email informed already registered on database.");
	}

	public EmailAlreadyRegisteredException(String summary, List<String> messageList) {
		super(summary, messageList);
	}
}