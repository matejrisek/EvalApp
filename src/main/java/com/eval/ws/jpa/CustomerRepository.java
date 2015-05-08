package com.eval.ws.jpa;

import org.springframework.data.repository.CrudRepository;

import com.eval.ws.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{

	Customer findByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);

}
