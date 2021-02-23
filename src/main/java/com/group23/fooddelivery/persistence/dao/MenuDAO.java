package com.group23.fooddelivery.persistence.dao;

import java.util.List;
import com.group23.fooddelivery.model.product.Menu;

public interface MenuDAO {
	
	public boolean save(Menu menu);  // Create
	public Menu findByPrimaryKey(String nome_menu,String formato);     
	public List<Menu> findAll();       
	public boolean update(Menu old_m,Menu new_m); //Update
	public boolean remove(String nome_menu, String formato);
	public List<Menu> cerca(String s);



}
