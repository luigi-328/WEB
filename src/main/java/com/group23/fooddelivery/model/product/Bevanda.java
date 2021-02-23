package com.group23.fooddelivery.model.product;

public class Bevanda {
	
	private String nome;
	private String formato;
	private float prezzo;
	private String path_image;
	
		
	public Bevanda(String nome, String formato, float prezzo, String path_image) {
		super();
		this.nome = nome;
		this.formato = formato;
		this.prezzo = prezzo;
		this.path_image = path_image;
	}
	public String getPath_image() {
		return path_image;
	}
	public void setPath_image(String path_image) {
		this.path_image = path_image;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public float getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public String getNameWithoutSpace() {
		return nome.replaceAll("\\s+","");
	}
	
	
}
