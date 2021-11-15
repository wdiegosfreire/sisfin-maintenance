package br.com.dfdevforge.sisfinmaintenance.exceptions;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.sisfinmaintenance.commons.exceptions.HttpStatusNotFound;

public class UserNotFoundException extends BaseException implements HttpStatusNotFound {
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("User Not Fount", "The email entered was not found in the database.");
	}
}