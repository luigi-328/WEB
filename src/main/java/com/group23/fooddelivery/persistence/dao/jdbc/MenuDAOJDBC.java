package com.group23.fooddelivery.persistence.dao.jdbc;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group23.fooddelivery.model.product.Bevanda;
import com.group23.fooddelivery.model.product.Menu;
import com.group23.fooddelivery.model.product.Panino;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.MenuDAO;

public class MenuDAOJDBC implements MenuDAO{

	private DBSource dbSource;
	private Connection conn;
	
	public MenuDAOJDBC(DBSource dbS) throws SQLException {
		dbSource = dbS;
		conn = dbSource.getConnection();
	}

	@Override
	public List<Menu> findAll() {
		
			if(conn == null)
				return null;
			
			List<Menu> menu = new ArrayList<Menu>();
			
			try {
				String query = "select * from menu";
				PreparedStatement st = conn.prepareStatement(query);
							
				
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					Panino p = cercaPanino(rs.getString("nome_menu"),rs.getString("formato"));
					Bevanda b = cercaBibita(rs.getString("nome_menu"),rs.getString("formato"));
					Menu m = new Menu(rs.getString("nome_menu"),rs.getString("formato"),p,b,
						 rs.getFloat("prezzo"),rs.getString("path_image"));
					m.setDescrizione(rs.getString("descrizione"));
					
					menu.add(m);
				}
				rs.close();
						
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return menu;
		}

	private Bevanda cercaBibita(String nome_menu, String formato_menu) {
		
		Bevanda b = null;
		String queryUpdate = "Select nome_bevanda,formato_bevanda from contenuto_del_menu "
				+ "where nome_menu=? AND formato_menu=?";
		try {
			PreparedStatement st = conn.prepareStatement(queryUpdate);
			st.setString(1, nome_menu);
			st.setString(2, formato_menu);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				b = new Bevanda(rs.getString("nome_bevanda"), rs.getString("formato_bevanda"),
						0, null);
			}
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b;
	}

	private Panino cercaPanino(String nome_menu, String formato_menu) {
		Panino p = null;
		
		String queryUpdate = "Select nome_panino,formato_panino from contenuto_del_menu "
				+ "where nome_menu=? AND formato_menu=?";
		try {
			PreparedStatement st = conn.prepareStatement(queryUpdate);
			st.setString(1, nome_menu);
			st.setString(2, formato_menu);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				p = new Panino(rs.getString("nome_panino"), rs.getString("formato_panino"),
						0, null, null);
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("cerca panino menu");
			e.printStackTrace();
		}
		
		return p;	
	}
	

	@Override
	public boolean save(Menu menu) {
		if(conn == null)
			return false;	
		
		if(exists(menu))  // menu già esistente
			return false;

		try {
			String query = "INSERT INTO menu (nome_menu,formato,prezzo,path_image,descrizione) VALUES (?,?,?,?,?)";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			
	
			stmt.setString(1, menu.getNome());
			stmt.setString(2, menu.getFormato());
			stmt.setFloat(3, menu.getPrezzo());
			stmt.setString(4, menu.getPath_image());
			stmt.setString(5, menu.getDescrizione());
			
			stmt.executeUpdate();
			saveContenutoMenu(menu);
			stmt.close();
		
			return true;
	
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	private boolean saveContenutoMenu(Menu menu) {
		
		try {
			String query = "INSERT INTO contenuto_del_menu (nome_menu,formato_menu,nome_panino,formato_panino,nome_bevanda,formato_bevanda) "
					+ "VALUES (?,?,?,?,?,?)";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			
	
			stmt.setString(1, menu.getNome());
			stmt.setString(2, menu.getFormato());
			stmt.setString(3, menu.getPanino().getNome());
			stmt.setString(4,menu.getPanino().getFormato());
			stmt.setString(5,menu.getBevanda().getNome());
			stmt.setString(6,menu.getBevanda().getFormato());
		
	
			stmt.executeUpdate();
			
			stmt.close();
		
			return true;
	
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	private boolean exists(Menu menu) {
		if(conn == null)
			return false;
		try {
			String query = "SELECT * FROM menu WHERE nome_menu=? AND formato=?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, menu.getNome());
			stmt.setString(2, menu.getFormato());
			
			ResultSet res = stmt.executeQuery();
			boolean result = res.next();
			stmt.close();
			
			return result;
		} 
	
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		
		}
	}

	
	@Override
	public boolean remove(String nome_menu, String formato) {
		
		if(conn == null)
			return false;
		
		String query = "DELETE FROM menu WHERE nome_menu=? AND formato=?";
	
		try {
			removeContenutoMenu(nome_menu,formato);
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, nome_menu);
			stmt.setString(2, formato);
			
			stmt.executeUpdate();
			
		
			
			stmt.close();
			return true;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return false;
		
	 
	}

	private boolean removeContenutoMenu(String nome_menu, String formato) {
		
		
		String query = "DELETE FROM contenuto_del_menu WHERE nome_menu=? AND formato_menu=?";
	
		try {
			PreparedStatement stmt = null;
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, nome_menu);
			stmt.setString(2, formato);
			
			stmt.executeUpdate();
			
			
			stmt.close();
			return true;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean update(Menu old_m,Menu new_m) {
		
		 if(conn == null)
				return false;
			
			String query = "UPDATE menu set nome_menu=?,formato=?,prezzo=?,path_image=?,descrizione=?"
					+ "where nome_menu=? AND formato_menu=?";
			

			try {
				
				PreparedStatement stmt =  conn.prepareStatement(query);
				stmt.setString(1, new_m.getNome());
				stmt.setString(2, new_m.getFormato());
				stmt.setFloat(3, new_m.getPrezzo());
				stmt.setString(4, new_m.getPath_image());
				stmt.setString(5, new_m.getDescrizione());
				stmt.setString(6, old_m.getNome());
				stmt.setString(7, old_m.getFormato());

				updateContenutoMenu(old_m,new_m);
				
				stmt.executeUpdate();
				stmt.close();
				return true;
				
			} catch (SQLException e) {
					e.printStackTrace();
			}		
		return false;
	}

	
	
	private boolean updateContenutoMenu(Menu old_m, Menu new_m) {
		
		if(conn == null)
			return false;
		
		String query = "UPDATE contenuto_del_menu SET nome_menu=?,formato_menu=?,nome_panino=?,formato_panino=?,"
				+ "nome_bevanda=?,formato_bevanda=?, where nome_menu=? AND formato_menu=?";

		try {
			PreparedStatement stmt =  conn.prepareStatement(query);
			stmt.setString(1, new_m.getNome());
			stmt.setString(2, new_m.getFormato());
			stmt.setString(3, new_m.getPanino().getNome());
			stmt.setString(4, new_m.getPanino().getFormato());
			stmt.setString(5, new_m.getBevanda().getNome());
			stmt.setString(6, new_m.getBevanda().getFormato());
			stmt.setString(7, old_m.getNome());
			stmt.setString(8, old_m.getFormato());
			
			return true;
			
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return false;
	}
	

	@Override
	public Menu findByPrimaryKey(String nome_menu, String formato) {
		Menu menu = null;
		

		String queryUpdate = "Select *  from menu where nome_menu=? AND formato=?";
		
		try {
			PreparedStatement st = conn.prepareStatement(queryUpdate);
			st.setString(1, nome_menu);
			st.setString(2, formato);
			
			ResultSet rs = st.executeQuery();
			
			if (rs.next()){
				Panino p = cercaPanino(nome_menu, formato);
				Bevanda b = cercaBibita(nome_menu,formato);
				
				menu = new Menu(nome_menu, formato,p,b, rs.getInt("prezzo"), 
						rs.getString("path_image"));
			}
				
				
			rs.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return menu;
	
	}
	
	
	public List<Menu> cerca(String s){

		if(conn == null)
			return null;
		
		List<Menu> menu = new ArrayList<Menu>();
		
		try {
			String sub = null;
			
			if(s.length()>3)
				sub = s.substring(0,3);
			String queryUpdate = "SELECT * FROM menu WHERE nome_menu ILIKE '%"+s
					+"%' or nome_menu ILIKE '%"
					+sub+"%' ORDER BY nome_menu DESC;";
			
			PreparedStatement st = conn.prepareStatement(queryUpdate);
						
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Panino p = cercaPanino(rs.getString("nome_menu"),rs.getString("formato"));
				Bevanda b = cercaBibita(rs.getString("nome_menu"),rs.getString("formato"));
				Menu m = new Menu(rs.getString("nome_menu"),rs.getString("formato"),p,b,
					 rs.getFloat("prezzo"),rs.getString("path_image"));
				
				menu.add(m);
			}
			rs.close();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return menu;
	}
	

}
