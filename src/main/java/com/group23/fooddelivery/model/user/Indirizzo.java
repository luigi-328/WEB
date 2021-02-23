package com.group23.fooddelivery.model.user;

public class Indirizzo {

	private String user;
	private String indirizzo;
	private Integer cap;
	
	public Indirizzo(String user, String indirizzo,Integer cap) {
		super();
		this.user = user;
		this.indirizzo = indirizzo;
		this.cap = cap;
	}

	public String getUsername() {
		return user;
	}

	public void setUsername(String user) {
		this.user = user;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public Integer getCap() {
		return cap;
	}

	public void setCap(Integer cap) {
		this.cap = cap;
	}
	
	public String getJSONString() {
		String json = "{";
		json += "\"username\": \"" + user + "\",";
		json += "\"indirizzo\": \"" + indirizzo + "\",";
		json += "\"cap\": " + cap + "}";

		
		return json;
	}
	
	
	
}
