package com.group23.fooddelivery.persistence.dao.jdbc;

import java.sql.Connection;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.group23.fooddelivery.model.user.Utente;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.UtenteDAO;


public class UtenteDAOJDBC implements UtenteDAO {

	private DBSource dbSource;
	private Connection conn;
	
	public UtenteDAOJDBC(DBSource dbS) throws SQLException {
		dbSource = dbS;
		conn = dbSource.getConnection();
	}
	
	@Override
	public boolean register(String username, String password) {

		if(conn == null)
			return false;	
		
		if(checkUsername(username))   // utente già registrato
			return false;

		try {
			String query = "INSERT INTO Utente (username,password) VALUES (?,?)";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			
			String hashPass = null;
			hashPass = BCrypt.hashpw(password,BCrypt.gensalt(12));
			
			
			stmt.setString(1, username);
			stmt.setString(2, hashPass);
			
			stmt.executeUpdate();
			stmt.close();
		
			return true;
	
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
		
	}

	@Override
	public Utente findByPrimaryKey(String username) {

		Utente utente = null;
		
		try {
			
			Connection conn = dbSource.getConnection();
			String query = "select * from Utente where username=?";
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				String user = rs.getString(1);
				String pass = rs.getString(2);
				Double tel = rs.getDouble(3);
				
				utente = new Utente(user,pass,tel);
			}
			st.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return utente;
	}

	
	@Override
	public boolean login(String username,String password)
	{
		if(conn == null)
			return false;
		
		if(checkUsername(username) && checkPassword(username,password)) {
			return true;
		}

		return false;
		
	}
	
	@Override
	public boolean update_passw(String username,String newPassword) {
		
		if(conn == null)
			return false;

		if(checkUsername(username))
		{
			String query = "UPDATE Utente SET Password=? WHERE Username=? ";
			try {
					PreparedStatement stmt = conn.prepareStatement(query);
					stmt.setString(1, BCrypt.hashpw(newPassword,BCrypt.gensalt(12)));
					stmt.setString(2,username);
					stmt.executeUpdate();
					stmt.close();
					
					return true;
			} 
			catch (SQLException e) {
				e.printStackTrace();;
			}
		}
		
		return false;
					
		
	}

	@Override
	public void delete_account(String username) {/* in sospeso*/}

	private boolean checkPassword(String username, String password) {
		
		if(conn == null)
			return false;
		
		String query = "SELECT Password FROM Utente WHERE Username=?;";
		
		try {
					
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setString(1, username);
				
				ResultSet rs = stmt.executeQuery();
				
				if (rs.next()) {
					String password_hash = rs.getString(1);
					if(password_hash == null)
						return false;
					
					stmt.close();
				
				return BCrypt.checkpw(password, password_hash);
				
				}

		} 
		
		catch (SQLException e) {
			System.out.println("qui");
			e.printStackTrace();
		}
		
		return false;
		
	
	}

	private boolean checkUsername(String username) {
		
		if(conn == null)
			return false;
		try {
			String query = "SELECT Username FROM Utente WHERE Username=?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
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

	

	
	
	
}
