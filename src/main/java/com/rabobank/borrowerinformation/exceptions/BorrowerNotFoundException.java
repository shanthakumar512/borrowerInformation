/**
 * 
 */
package com.rabobank.borrowerinformation.exceptions;

/**
 * @author Admin
 *
 */
public class BorrowerNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8864327924681476742L;

	/**
	 * @param message
	 */
	public BorrowerNotFoundException(String message) {
		super(message);
	}

}
