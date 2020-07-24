/**
 * 
 */
package com.rabobank.userInformation.exceptions;

/**
 * @author Admin
 *
 */
public class UserDetailsAlreadyExistForEmailIDException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5044511313422451666L;

	/**
	 * @param message
	 */
	public UserDetailsAlreadyExistForEmailIDException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UserDetailsAlreadyExistForEmailIDException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UserDetailsAlreadyExistForEmailIDException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UserDetailsAlreadyExistForEmailIDException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
