/**
 * 
 */
package com.rabobank.userInformation.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Admin
 *
 */
@Entity
@Table(	name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(max = 50)
	@NotBlank private String addressLine1;
	@Size(max = 50)
	private String addressLine2;
	
	@Size(max = 50)
	private String addressLine3;

	@OneToOne(mappedBy="propertyAddress", cascade=CascadeType.ALL)
	@JsonBackReference
	private LoanUser loanUser;
	
	public Address() {
		
	}
	/**
	 * @param addressLine1
	 * @param addressLine2
	 * @param addressLine3
	 * @param city
	 * @param state
	 * @param country
	 */
	public Address(@Size(max = 50) @NotBlank String addressLine1, @Size(max = 50) String addressLine2,
			@Size(max = 50) String addressLine3, @Size(max = 20) @NotBlank String city,
			@NotBlank @Size(max = 20) String state, @Size(max = 20) @NotBlank String country) {
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
		this.city = city;
		this.state = state;
		this.country = country;
	}
	@Size(max = 20)
	@NotBlank 
	
	private String city;
	@NotBlank
	@Size(max = 20)
	private String state;
	@Size(max = 20)
	@NotBlank 
	private String country;
	
	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}
	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}
	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	/**
	 * @return the addressLine3
	 */
	public String getAddressLine3() {
		return addressLine3;
	}
	/**
	 * @param addressLine3 the addressLine3 to set
	 */
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the loanUser
	 */
	public LoanUser getLoanUser() {
		return loanUser;
	}
	/**
	 * @param loanUser the loanUser to set
	 */
	public void setLoanUser(LoanUser loanUser) {
		this.loanUser = loanUser;
	}
		
	
	

	
}
