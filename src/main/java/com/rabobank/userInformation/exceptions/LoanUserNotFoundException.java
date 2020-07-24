/**
 * 
 */
package com.rabobank.userInformation.exceptions;

/**
 * @author Admin
 *
 */
public class LoanUserNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8864327924681476742L;

	/**
	 * @param message
	 */
	public LoanUserNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public LoanUserNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LoanUserNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public LoanUserNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
