package com.group23.fooddelivery.persistence.dao;

import java.util.List;

import com.group23.fooddelivery.model.product.Bevanda;
public interface BevandaDAO {

	public boolean save(Bevanda bevanda);  // Create
	public Bevanda findByPrimaryKey(String nome_bevanda,String formato);     // Retrieve
	public List<Bevanda> findAll();       
	public boolean update(Bevanda old_bevanda, Bevanda new_bevanda); //Update
	public boolean remove(Bevanda bevanda); //Delete	
	public List<Bevanda> cerca(String s);

}
