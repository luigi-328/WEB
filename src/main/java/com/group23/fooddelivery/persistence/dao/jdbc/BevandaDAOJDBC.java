package com.group23.fooddelivery.persistence.dao.jdbc;

import java.sql.Connection;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group23.fooddelivery.model.product.Bevanda;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.BevandaDAO;

public class BevandaDAOJDBC implements BevandaDAO {

	private DBSource dbSource;
	private Connection conn;
	
	public BevandaDAOJDBC(DBSource dbS) throws SQLException {
		dbSource = dbS;
		conn = dbSource.getConnection();
	}
	
	@Override
	public boolean save(Bevanda bevanda) {
		if(conn == null)
			return false;
		if(exists(bevanda.getNome(), bevanda.getFormato()))
			return false;
		
		try {
			
			String queryUpdate = "INSERT INTO bevanda values(?, ?, ?, ?)";
			
			PreparedStatement st = conn.prepareStatement(queryUpdate);
			st.setString(1, bevanda.getNome());
			st.setString(2, bevanda.getFormato());
			st.setDouble(3, bevanda.getPrezzo());
			st.setString(4, bevanda.getPath_image());
			
			st.executeUpdate();	
			st.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public Bevanda findByPrimaryKey(String nome_bevanda,String formato) {
				
		Bevanda bevanda = null;
		String queryUpdate = "Select * from bevanda where nome_bevanda=? AND formato=?";
		
		try {
			PreparedStatement st = conn.prepareStatement(queryUpdate);
			st.setString(1, nome_bevanda);
			st.setString(2, formato);
						
			ResultSet rs = st.executeQuery();
			
			if (rs.next())
			{
				bevanda = new Bevanda(nome_bevanda, rs.getString("formato"), rs.getFloat("prezzo"),
						rs.getString("path_image"));
				return bevanda;
			}

			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return bevanda;
				
	}
	
	

	@Override
	public List<Bevanda> findAll() {
		List<Bevanda> bevande = new ArrayList<Bevanda>();
		
		String queryUpdate = "select * from bevanda";
		
		try {
				PreparedStatement st = conn.prepareStatement(queryUpdate);
				ResultSet rs = st.executeQuery();
				
				while (rs.next()) {
					
					bevande.add(new Bevanda(rs.getString("nome_bevanda"),rs.getString("formato"), rs.getFloat("prezzo"), 
					rs.getString("path_image")));
				}
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return bevande;
	
	}

	@Override
	public boolean update(Bevanda old_bevanda, Bevanda new_bevanda) {
		 if(conn == null)
				return false;
			
			String query = "UPDATE bevanda set nome_bevanda=?,prezzo=?,"
					+ "path_image=?,formato=? where nome_bevanda=? "
					+ "AND formato=?";

			try {
				PreparedStatement stmt =  conn.prepareStatement(query);
				stmt.setString(1, new_bevanda.getNome());
				stmt.setFloat(2, new_bevanda.getPrezzo());
				stmt.setString(3, new_bevanda.getPath_image());
				stmt.setString(2, new_bevanda.getFormato());
				
				stmt.setString(4, old_bevanda.getNome());
				stmt.setString(5, old_bevanda.getFormato());
				
				stmt.executeUpdate();
				stmt.close();
				return true;
				
			} catch (SQLException e) {
					e.printStackTrace();
			}
			
		
		return false;
		
	}

	@Override
	public boolean remove(Bevanda bevanda) {
		if(conn == null)
			return false;
		
		String query = "DELETE FROM bevanda WHERE nome_bevanda=? AND formato=?";
	
		try {
			PreparedStatement stmt = null;
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, bevanda.getNome());
			stmt.setString(2, bevanda.getFormato());
			
			stmt.executeUpdate();
			stmt.close();
			return true;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return false;
		
	 
		
	}
	private boolean exists(String nome_bevanda,String formato) {
		
		if(conn == null)
			return false;
		try {
			String query = "SELECT * FROM bevanda WHERE nome_bevanda=? AND formato=?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, nome_bevanda);
			stmt.setString(2, formato);
			
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
	public List<Bevanda> cerca(String s) {
		
		List<Bevanda> bevande = new ArrayList<Bevanda>();
	
		String sub = null;
		if(s.length()>3)
			sub = s.substring(0,3);
		
		String queryUpdate = "SELECT * FROM bevanda WHERE nome_bevanda ILIKE '%"+s
				+"%' or nome_bevanda ILIKE '%"
				+sub+"%' ORDER BY nome_bevanda DESC;";
			
		try {
				PreparedStatement st = conn.prepareStatement(queryUpdate);
				ResultSet rs = st.executeQuery();
				
				while (rs.next()) {
					
					bevande.add(new Bevanda(rs.getString("nome_bevanda"),rs.getString("formato"), rs.getFloat("prezzo"), 
					rs.getString("path_image")));
				}
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return bevande;
	
	}
}
