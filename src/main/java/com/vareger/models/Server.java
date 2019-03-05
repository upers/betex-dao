package com.vareger.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="server")
public class Server {
	
	@Id
	private Integer id;
	
	@Column(name="is_busy")
	private boolean isBusy;
	
	@Column(name="local_ip_address")
	private String localIpAddress;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public String getLocalIpAddress() {
		return localIpAddress;
	}

	public void setLocalIpAddress(String localIpAddress) {
		this.localIpAddress = localIpAddress;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Server))
			return false;
		
		if (this.id == null)
			return false;
		
		Server server = (Server) o;
		Integer id = server.getId();
		if (id == null)
			return false;
		
		if (id.equals(this.id))
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public String toString() {
		return "Server [id=" + id + ", isBusy=" + isBusy + ", localIpAddress=" + localIpAddress + "]";
	}
}
