package com.group23.fooddelivery.persistence.dao;

import java.util.List;

import com.group23.fooddelivery.model.user.Recensione;

public interface RecensioneDAO {
	
	public boolean save(Recensione recensione);  // Create
	public Recensione findByPrimaryKey(String id);     // Retrieve
	public List<Recensione> findAll();
	public boolean update(Recensione old_recensione, Recensione new_recensione); //Update
	public boolean remove(Recensione recensione); //Delete

}
