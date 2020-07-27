package com.rabobank.userinformation.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(	name = "loan_users",
uniqueConstraints = { 
		@UniqueConstraint(columnNames = "userEmail")})
public class LoanUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 20)
	private String userFirstname;
	@NotBlank
	@Size(max = 20)
	private String userLastname;
	
	/**
	 * @return the propertyAddress
	 */
	public Address getPropertyAddress() {
		return propertyAddress;
	}
	/**
	 * @param propertyAddress the propertyAddress to set
	 */
	public void setPropertyAddress(Address propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	@NotBlank
	@Size(max = 50)
	private String userEmail;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn	
	@JsonManagedReference
	private Address propertyAddress; 
	
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
	
	LoanUser(){
	}
	
	/**
	 * @param userFirstname
	 * @param userLastname
	 * @param loanNumber
	 * @param addressLine1
	 * @param addressLine2
	 * @param addressLine3
	 * @param city
	 * @param state
	 * @param country
	 */
	public LoanUser( String userFirstname,  String userLastname,String userEmail) {
		this.userFirstname = userFirstname;
		this.userLastname = userLastname;	
		this.userEmail= userEmail;
	}


}
