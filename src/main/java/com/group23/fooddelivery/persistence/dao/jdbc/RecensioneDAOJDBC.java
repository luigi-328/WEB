package com.group23.fooddelivery.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group23.fooddelivery.model.user.Recensione;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.RecensioneDAO;

public class RecensioneDAOJDBC implements RecensioneDAO{
	
	private DBSource dbSource;
	private Connection connection;
	
	public RecensioneDAOJDBC(DBSource dbSource) throws SQLException {
		this.dbSource = dbSource;
		this.connection = this.dbSource.getConnection();
	}

	@Override
	public boolean save(Recensione recensione) {
		if(connection == null)
			return false;
		
		if(exists(recensione.getId()))
			return false;
		
		try {
			String queryUpdate = "INSERT INTO recensione (text, id, nome) values(?, ?, ?)";
			
			PreparedStatement st = connection.prepareStatement(queryUpdate);
			st.setString(1, recensione.getText());
			st.setString(2, recensione.getId());
			st.setString(3, recensione.getNome());
			
			st.executeUpdate();
			st.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return true;
	}



	@Override
	public Recensione findByPrimaryKey(String id) {
		
		Recensione recensione = null;
		
		String queryUpdate = "Select *  from recensione where id=?";
		
		try {
			PreparedStatement st = connection.prepareStatement(queryUpdate);
			st.setString(1, id);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next())
				recensione = new Recensione(rs.getString("text"), id, rs.getString("nome"));
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return recensione;
	}

	@Override
	public List<Recensione> findAll() {
		List<Recensione> recensioni = new ArrayList<Recensione>();
		
		String queryUpdate = "select * from recensione";
		
		try {
			PreparedStatement st = connection.prepareStatement(queryUpdate);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Recensione recensione = new Recensione(rs.getString("text"), rs.getString("id"), rs.getString("nome"));
				recensioni.add(recensione);			
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return recensioni;
	}
	
	@Override
	public boolean update(Recensione old_recensione, Recensione new_recensione) {
		
		if(connection == null)
			return false;
		
		String query = "UPDATE recensione set text=?, id=?, nome=? where id=?";
		
		try {
			PreparedStatement st = connection.prepareStatement(query);
			st.setString(1, new_recensione.getText());
			st.setString(2, new_recensione.getId());
			st.setString(3, new_recensione.getNome());
			st.setString(4, old_recensione.getId());
			
			st.executeUpdate();
			st.close();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

	@Override
	public boolean remove(Recensione recensione) {
		if(connection == null)
			return false;
		
		String query = "DELETE FROM recensione WHERE id=?";
		
		try {
			PreparedStatement st = connection.prepareStatement(query);
			st.setString(1, recensione.getId());
			st.executeUpdate();
			
			st.close();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	private boolean exists(String id) {
		if(connection == null)
			return false;
		
		try {
			String query = "SELECT * FROM recensione WHERE id=?";
			PreparedStatement st = connection.prepareStatement(query);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
			boolean result = rs.next();
			st.close();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}



}
