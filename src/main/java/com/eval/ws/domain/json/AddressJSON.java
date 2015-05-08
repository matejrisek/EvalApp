package com.eval.ws.domain.json;

public class AddressJSON {

	private String street;
	private String suite;
	private String city;
	private String zipcode;
	private GeoJSON geo;

	public String getStreet() {
		return street;
	}

	public String getSuite() {
		return suite;
	}

	public String getCity() {
		return city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public GeoJSON getGeo() {
		return geo;
	}

}