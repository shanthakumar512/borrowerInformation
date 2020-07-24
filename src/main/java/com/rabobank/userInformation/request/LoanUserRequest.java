package com.rabobank.userInformation.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.rabobank.userInformation.model.Address;


public class LoanUserRequest {
	@NotBlank
	private String userFirstname;
	
	@NotBlank
	private String userLastname;
	
	@Email
	private String userEmail;
	
	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}
	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	private Address propertyAddress;
	
	/**
	 * @return the userFirstname
	 */
	public String getUserFirstname() {
		return userFirstname;
	}
	/**
	 * @param userFirstname the userFirstname to set
	 */
	public void setUserFirstname(String userFirstname) {
		this.userFirstname = userFirstname;
	}
	/**
	 * @return the userLastname
	 */
	public String getUserLastname() {
		return userLastname;
	}
	/**
	 * @param userLastname the userLastname to set
	 */
	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}
	public Address getPropertyAddress() {
		return propertyAddress;
	}
	public void setPropertyAddress(Address propertyAddress) {
		this.propertyAddress = propertyAddress;
	}
	
}
