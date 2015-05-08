package com.eval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.eval.ws.domain.Customer;

@Service
public class EmailSenderService {

	@Autowired
	JavaMailSender mailSender;

	public void sendCustomerInfo(Customer customer) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(customer.getEmail());
		mailMessage.setSubject("Customer " + customer.getFirstName());
		StringBuilder sb = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		sb.append("First name: " + customer.getFirstName() + newLine);
		sb.append("Last name: " + customer.getLastName() + newLine);
		sb.append("Email: " + customer.getEmail() + newLine);
		if (customer.getCompany() != null) {
			sb.append("Company: " + customer.getCompany() + newLine);
			sb.append("Address: " + customer.getAddress() + newLine);
			sb.append("Phone: " + customer.getPhone() + newLine);
		}
		mailMessage.setText(sb.toString());
		System.out.println("Sending mail to: " + customer.getEmail());
		try {
			mailSender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
