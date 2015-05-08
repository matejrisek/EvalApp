package com.eval.ws.controller;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eval.service.EmailSenderService;
import com.eval.ws.Message;
import com.eval.ws.domain.Customer;
import com.eval.ws.domain.IpSpam;
import com.eval.ws.jpa.CustomerRepository;
import com.eval.ws.jpa.IpSpamRepository;
import com.eval.ws.service.CustomerService;

@RestController
public class CustomerController {

	private static final long HOUR = 1000 * 60 * 60;

	@Autowired
	IpSpamRepository entryRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerService customerService;

	@Autowired
	EmailSenderService emailSender;

	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public Message createUser(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName, @RequestParam(value = "email") String email,
			HttpServletRequest request) {

		// First check if the request is spam
		if (isSpam(request.getRemoteAddr())) {
			return new Message("Can't send more than one request per hour!");
		}

		Customer customer = customerRepository.findByFirstNameAndLastNameAndEmail(firstName, lastName, email);
		if (customer == null) {
			customer = new Customer(firstName, lastName, email);
			System.out.println("Creating new user " + customer);
			customerService.fetchExternalInfo(customer);
			customerRepository.save(customer);
		}

		// Send mail
		emailSender.sendCustomerInfo(customer);
		return new Message("e-mail successfully sent!");
	}

	/**
	 * Check if request already has been made from this IP address
	 * 
	 * @param ipAddress
	 * @return true - request already been made false - there wasn't request
	 *         from this IP address in the last hour
	 */
	@Transactional
	private boolean isSpam(String ipAddress) {
		// Check if request already has been made from same IP address
		IpSpam existingEntry = entryRepository.findByIpAddress(ipAddress);
		if (existingEntry == null || System.currentTimeMillis() - existingEntry.getLastAccessTime() > HOUR) {
			// Update last access time
			if (existingEntry != null) {
				existingEntry.setLastAccessTime(System.currentTimeMillis());
			}
			IpSpam entry = existingEntry == null ? new IpSpam(ipAddress) : existingEntry;
			entryRepository.save(entry);
		} else {
			return true;
		}
		return false;
	}
}
