package com.group23.fooddelivery.persistence.dao;

import java.util.List;
import com.group23.fooddelivery.model.product.Ingrediente;



public interface IngredientiDAO {
	
	public boolean save(Ingrediente ingrediente);  // Create
	public List<Ingrediente> findAll(); 
	public boolean update(Ingrediente old_ingr, Ingrediente new_ingr); //Update
	public boolean remove(Ingrediente ingrediente); //Delete		
}
