package com.group23.fooddelivery.persistence.dao.jdbc;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group23.fooddelivery.model.user.Indirizzo;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.IndirizzoDAO;

public class IndirizzoDAOJDBC implements IndirizzoDAO{
	
	private DBSource dbSource;
	private Connection conn;
	
	public IndirizzoDAOJDBC(DBSource dbS) throws SQLException {
		dbSource = dbS;
		conn = dbSource.getConnection();
	}

	@Override
	public List<Indirizzo> getIndirizzi(String username) {
		
			if(conn == null)
				return null;
			
			List<Indirizzo> indirizzi = new ArrayList<Indirizzo>();
			
			try {
				String query = "select * from indirizzo where username=?";
				PreparedStatement st = conn.prepareStatement(query);
				st.setString(1, username);
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					String ind = rs.getString("indirizzo"); 
					Integer cap = rs.getInt("cap"); 
					if(ind != null && cap != null)
						indirizzi.add(new Indirizzo(username, ind, cap));
				}
						
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return indirizzi;
		}

	@Override
	public boolean save(String username,Indirizzo indirizzo) {
		if(conn == null)
			return false;	
		
		if(checkIndirizzo(username,indirizzo))   // indirizzo già registrato
			return false;

		try {
			String query = "INSERT INTO indirizzo (username,indirizzo,cap) VALUES (?,?,?)";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			
	
			stmt.setString(1, indirizzo.getUsername());
			stmt.setString(2, indirizzo.getIndirizzo());
			stmt.setInt(3, indirizzo.getCap());
			
			stmt.executeUpdate();
			stmt.close();
		
			return true;
	
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	private boolean checkIndirizzo(String username,Indirizzo indirizzo) {
		
		if(conn == null)
			return false;
		try {
			String query = "SELECT * FROM indirizzo WHERE Username=? AND indirizzo=? AND cap=?";
			PreparedStatement stmt = null;
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, indirizzo.getIndirizzo());
			stmt.setInt(3, indirizzo.getCap());
			
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
	public boolean remove(String username, Indirizzo indirizzo) {
		
		if(conn == null)
			return false;
		
		String query = "DELETE FROM indirizzo WHERE username=? AND indirizzo=?";
	
		try {
			PreparedStatement stmt = null;
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, indirizzo.getIndirizzo());
			
			stmt.executeUpdate();
			stmt.close();
			return true;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return false;
		
	 
	}

	@Override
	public boolean update(String username, Indirizzo old_ind,Indirizzo new_ind) {
		
		 if(conn == null)
				return false;
			
			String query = "UPDATE indirizzo set indirizzo=?,cap=? where username=? AND indirizzo=?";
			

			try {
				PreparedStatement stmt =  conn.prepareStatement(query);
				stmt.setString(1, new_ind.getIndirizzo());
				stmt.setInt(2, new_ind.getCap());
				stmt.setString(3, username);
				stmt.setString(4, old_ind.getIndirizzo());
				
				
				stmt.executeUpdate();
				stmt.close();
				return true;
				
			} catch (SQLException e) {
					e.printStackTrace();
			}
			
		
		return false;
	}

	@Override
	public Indirizzo findByPrimaryKey(String username, String indirizzo,Integer cap) {
		
		if(conn == null)
			return null;
		try {
			String query = "SELECT * FROM indirizzo WHERE Username=? AND indirizzo=? AND cap=?";
			PreparedStatement stmt = null;
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, indirizzo);
			stmt.setInt(3, cap);
			
			ResultSet res = stmt.executeQuery();
			if( res.next()) {
				stmt.close();
				return new Indirizzo(username, indirizzo, cap);
			} 
		}
	
		catch (SQLException e) {
			e.printStackTrace();	
		}
		return null;
	}

	
}
