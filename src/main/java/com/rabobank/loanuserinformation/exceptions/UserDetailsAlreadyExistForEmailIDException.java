/**
 * 
 */
package com.rabobank.loanuserinformation.exceptions;

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
	}
}
