package br.com.dfdevforge.sisfinmaintenance.exceptions;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.sisfinmaintenance.commons.exceptions.HttpStatusUnauthorized;

public class SessionExpiredException extends BaseException implements HttpStatusUnauthorized {
	private static final long serialVersionUID = 1L;

	public SessionExpiredException() {
		super("Session Expired", "The session has expired. Please do login again to access the application.");
	}
}