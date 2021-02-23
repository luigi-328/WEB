package com.group23.fooddelivery.persistence.dao;

import java.util.List;
import com.group23.fooddelivery.model.sales.Ordine;


public interface OrdineDAO {
	public boolean save(Ordine ordine);  // Create
	public Ordine findByPrimaryKey(Integer id_ordine,String username);     // Retrieve
	public List<Ordine> findAll(String username);       
	public boolean update(Ordine old_ordine,Ordine new_ordine); //Update
	public boolean remove(Ordine ordine); //Delete	
	public  List<Ordine> getOrdiniDaConsegnare();
	public boolean updateStatoOrdine(Integer id_ordine);
	public Integer getIdOrdine(String username); // ultimo ordine effettuato (se presente)

	
}