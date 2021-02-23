package com.group23.fooddelivery.model.user;

public class Utente {
	private String username;
	private String password;
	private Double contatto_telefonico;
	
	public Utente(String username, String password, Double contatto_telefonico) {
		super();
		this.username = username;
		this.password = password;
		this.contatto_telefonico = contatto_telefonico;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Double getContatto_telefonico() {
		return contatto_telefonico;
	}
	public void setContatto_telefonico(Double contatto_telefonico) {
		this.contatto_telefonico = contatto_telefonico;
	}
	
	
	
}
