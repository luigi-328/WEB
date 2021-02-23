package com.group23.fooddelivery.model.product;

import java.util.ArrayList;

public class Panino {
	private String nome;
	private String formato;
	private float prezzo;
	private String pathImage;
	private ArrayList<Ingrediente> ingredienti;
	private String descrizione;

	public Panino(String nome, String formato, float prezzo, ArrayList<Ingrediente> ingredienti,String pathImage) {
		super();
		this.nome = nome;
		this.formato = formato;
		this.prezzo = prezzo;
		this.ingredienti = ingredienti;
		this.pathImage = pathImage;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public ArrayList<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(ArrayList<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
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

	public String getPathImage() {
		return pathImage;
	}

	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}
	
	
}
