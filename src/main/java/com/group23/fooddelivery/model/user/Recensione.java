package com.group23.fooddelivery.model.user;


public class Recensione {
	String text;
	String id;
	String nome;
	
	public Recensione(String text, String id, String nome) {
		this.text = text;
		this.id = id;
		this.nome = nome;
	}
	
	public String getText() {
		return text;
	}
	
	public String getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
