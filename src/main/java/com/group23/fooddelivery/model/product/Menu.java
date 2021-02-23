package com.group23.fooddelivery.model.product;

public class Menu {
	
	private String nome;
	private String formato;
	private Panino panino;
	private Bevanda bevanda;
	private float prezzo;
	private String path_image;
	private String descrizione;
	
	public Menu(String nome, String formato, Panino panino, Bevanda bevanda, float prezzo,String path_image) {
		super();
		this.nome = nome;
		this.formato = formato;
		this.panino = panino;
		this.bevanda = bevanda;
		this.prezzo = prezzo;
		this.path_image = path_image;
	}
	
	public Panino getPanino() {
		return panino;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setPanino(Panino panino) {
		this.panino = panino;
	}


	public String getPath_image() {
		return path_image;
	}

	public void setPath_image(String path_image) {
		this.path_image = path_image;
	}

	public Bevanda getBevanda() {
		return bevanda;
	}


	public void setBevanda(Bevanda bevanda) {
		this.bevanda = bevanda;
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
