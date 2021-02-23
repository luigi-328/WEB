package com.group23.fooddelivery.persistence.dao;

import java.util.List;
import com.group23.fooddelivery.model.product.Panino;

public interface PaninoDAO {
	
	public boolean save(Panino panino);  // Create
	public Panino findByPrimaryKey(String nome_panino,String formato);     // Retrieve
	public List<Panino> findAll();       
	public boolean update(Panino old_panino,Panino new_panino); //Update
	public boolean remove(Panino panino); //Delete	
	public boolean saveIngredienti(Panino new_panino);
	public List<Panino> cerca(String s);
	
}
