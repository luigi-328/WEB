package com.group23.fooddelivery.persistence.dao;

import com.group23.fooddelivery.model.user.Utente;


public interface UtenteDAO {
	
	public boolean register(String username, String password);
	public Utente findByPrimaryKey(String username);    
	
	public boolean update_passw(String username,String newPassword); //Update
	public void delete_account(String username); //Delete	
	public boolean login(String username,String password);
	
	
	
	
}
