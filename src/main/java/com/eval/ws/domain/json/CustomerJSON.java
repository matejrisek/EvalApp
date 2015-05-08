package com.eval.ws.domain.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerJSON {

	private String name;
	private String username;
	private String email;
	private AddressJSON address;
	private String phone;
	private String website;
	private CompanyJSON company;
	
	public String getName() {
		return name;
	}
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public AddressJSON getAddress() {
		return address;
	}
	public String getPhone() {
		return phone;
	}
	public String getWebsite() {
		return website;
	}
	public CompanyJSON getCompany() {
		return company;
	}
	
}