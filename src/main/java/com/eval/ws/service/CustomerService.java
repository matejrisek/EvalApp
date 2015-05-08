package com.eval.ws.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.eval.ws.domain.Customer;
import com.eval.ws.domain.json.CustomerJSON;

@Service
public class CustomerService {

	/**
	 * Fetch additional info based on customer first name, last name and email.
	 * If service returns values, they are added to customer object.
	 * 
	 * @param customer
	 * @return true - if there was additional info false - if there wasn't
	 *         additional info
	 */
	public boolean fetchExternalInfo(Customer customer) {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> getVariables = new HashMap<>();
		getVariables.put("name", customer.getFirstName() + " " + customer.getLastName());
		getVariables.put("email", customer.getEmail());
		CustomerJSON customers[] = restTemplate.getForObject(createUriWithGetParams(customer), CustomerJSON[].class);

		// Set additional set of fields
		if (customers.length > 0) {
			CustomerJSON cj = customers[0];
			customer.setAddress(String.format("%s, %s %s", cj.getAddress().getStreet(), cj.getAddress().getZipcode(),
					cj.getAddress().getCity()));
			customer.setCompany(cj.getCompany().getName());
			customer.setPhone(cj.getPhone());
			return true;
		}

		return false;
	}

	private URI createUriWithGetParams(Customer customer) {
		URI targetUrl = UriComponentsBuilder.fromUriString("http://jsonplaceholder.typicode.com").path("/users")
				.queryParam("name", customer.getFirstName() + " " + customer.getLastName())
				.queryParam("email", customer.getEmail()).build().toUri();
		return targetUrl;
	}
}
