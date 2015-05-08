package com.eval.ws.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IpSpam {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String ipAddress;
	long lastAccessTime = System.currentTimeMillis();

	protected IpSpam() {
	};

	public IpSpam(String ipAddress) {
		super();
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	@Override
	public String toString() {
		return String.format("[%s, %s]", ipAddress, lastAccessTime);
	}
}
