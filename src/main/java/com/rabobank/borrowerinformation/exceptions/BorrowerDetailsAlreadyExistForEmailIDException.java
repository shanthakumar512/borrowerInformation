/**
 * 
 */
package com.rabobank.borrowerinformation.exceptions;

/**
 * @author Admin
 *
 */
public class BorrowerDetailsAlreadyExistForEmailIDException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5044511313422451666L;

	/**
	 * @param message
	 */
	public BorrowerDetailsAlreadyExistForEmailIDException(String message) {
		super(message);
	}
}
