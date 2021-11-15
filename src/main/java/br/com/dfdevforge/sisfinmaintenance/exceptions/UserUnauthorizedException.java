package br.com.dfdevforge.sisfinmaintenance.exceptions;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.sisfinmaintenance.commons.exceptions.HttpStatusUnauthorized;

public class UserUnauthorizedException extends BaseException implements HttpStatusUnauthorized {
	private static final long serialVersionUID = 1L;

	public UserUnauthorizedException() {
		super("User Unauthorized", "User credentials are incorrect. Please review your credentials and try again.");
	}
}