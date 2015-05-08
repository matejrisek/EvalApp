package com.eval.ws.jpa;

import org.springframework.data.repository.CrudRepository;

import com.eval.ws.domain.IpSpam;

public interface IpSpamRepository extends CrudRepository<IpSpam, Long> {

	IpSpam findByIpAddress(String ipAddress);
	
}
