package com.group23.fooddelivery.persistence.dao;
import java.util.List;
import com.group23.fooddelivery.model.user.Indirizzo;

public interface IndirizzoDAO {
	
	public List<Indirizzo> getIndirizzi(String username);
	public boolean save(String username,Indirizzo indirizzo);  // Create
	public boolean remove(String username,Indirizzo indirizzo);
	public boolean update(String username,Indirizzo old,Indirizzo new_ind);
	public Indirizzo findByPrimaryKey(String username,String indirizzo, Integer cap);
}
