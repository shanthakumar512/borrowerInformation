package com.rabobank.borrowerinformation.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.rabobank.borrowerinformation.model.Address;


public class BorrowerRequest {
	@NotBlank
	private String borrowerFirstname;
	
	@NotBlank
	private String borrowerLastname;
	
	@Email
	private String borrowerEmail;
	
	/**
	 * @return the borrowerEmail
	 */
	public String getBorrowerEmail() {
		return borrowerEmail;
	}
	/**
	 * @param borrowerEmail the borrowerEmail to set
	 */
	public void setBorrowerEmail(String userEmail) {
		this.borrowerEmail = userEmail;
	}
	private Address propertyAddress;
	
	/**
	 * @return the borrowerFirstname
	 */
	public String getBorrowerFirstname() {
		return borrowerFirstname;
	}
	/**
	 * @param borrowerFirstname the borrowerFirstname to set
	 */
	public void setBorrowerFirstname(String userFirstname) {
		this.borrowerFirstname = userFirstname;
	}
	/**
	 * @return the borrowerLastname
	 */
	public String getBorrowerLastname() {
		return borrowerLastname;
	}
	/**
	 * @param borrowerLastname the borrowerLastname to set
	 */
	public void setBorrowerLastname(String userLastname) {
		this.borrowerLastname = userLastname;
	}
	public Address getPropertyAddress() {
		return propertyAddress;
	}
	public void setPropertyAddress(Address propertyAddress) {
		this.propertyAddress = propertyAddress;
	}
	
}
