package com.group23.fooddelivery.persistence.dao.jdbc;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group23.fooddelivery.model.product.Ingrediente;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.IngredientiDAO;

public class IngredientiDAOJDBC implements IngredientiDAO {

	private DBSource dbSource;
	private Connection conn;
	
	public IngredientiDAOJDBC(DBSource dbS) throws SQLException {
		dbSource = dbS;
		conn = dbSource.getConnection();
	}
	
	@Override
	public boolean save(Ingrediente ingrediente) {
		if(conn == null)
			return false;	
		
		if(exists(ingrediente.getNome_ingrediente()))   // ingrediente già presente
			return false;

		try {
			String query = "INSERT INTO ingrediente (nome_ingrediente) VALUES (?)";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			
			stmt.setString(1, ingrediente.getNome_ingrediente());
		
			stmt.executeUpdate();
			stmt.close();
		
			return true;
	
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	private boolean exists(String nome_ingrediente) {
		
		if(conn == null)
			return false;
		try {
			String query = "SELECT * FROM ingrediente WHERE nome_ingrediente=?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, nome_ingrediente);
						
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
	public List<Ingrediente> findAll() {
		if(conn == null)
		return null;
	
	List<Ingrediente> ingredienti = new ArrayList<Ingrediente>();
	
	try {
		
		String query = "select * from ingrediente";
		PreparedStatement st = conn.prepareStatement(query);

		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			String ind = rs.getString("nome_ingrediente"); 
			if(ind != null)
				ingredienti.add(new Ingrediente( rs.getString("nome_ingrediente")));
			
		}
				
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return ingredienti;

	}

	@Override
	public boolean update(Ingrediente old_ingr,Ingrediente new_ingr) {

		 if(conn == null)
				return false;
			
			String query = "UPDATE ingrediente set nome_ingrediente=? where nome_ingrediente=?";

			try {
				PreparedStatement stmt =  conn.prepareStatement(query);
				stmt.setString(1, old_ingr.getNome_ingrediente());
				stmt.setString(2, new_ingr.getNome_ingrediente());
				
				stmt.executeUpdate();
				stmt.close();
				return true;
				
			} catch (SQLException e) {
					e.printStackTrace();
			}
			
		
		return false;
		
	}

	@Override
	public boolean remove(Ingrediente ingrediente) {
		if(conn == null)
			return false;
		
		String query = "DELETE * FROM ingrediente WHERE nome_ingrediente=?";	
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, ingrediente.getNome_ingrediente());
						
			stmt.executeUpdate();
			stmt.close();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
}
