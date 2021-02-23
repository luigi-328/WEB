package com.group23.fooddelivery.persistence.dao.jdbc;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group23.fooddelivery.model.product.Ingrediente;
import com.group23.fooddelivery.model.product.Panino;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.PaninoDAO;

public class PaninoDAOJDBC implements PaninoDAO{

	private DBSource dbSource;
	private Connection conn;
	
	public PaninoDAOJDBC(DBSource dbS) throws SQLException {
		dbSource = dbS;
		conn = dbSource.getConnection();
	}
	
	@Override
	public boolean save(Panino panino) {
		if(conn == null)
			return false;
		
		if(exists(panino.getNome(), panino.getFormato()))
			return false;
		
		try {
			String queryUpdate = "INSERT INTO panino (nome_panino,path_image,formato,prezzo,descrizione) values(?, ?, ?, ?, ?)";
			
			PreparedStatement st = conn.prepareStatement(queryUpdate);
			st.setString(1, panino.getNome());
			st.setString(2, panino.getPathImage());
			st.setString(3, panino.getFormato());
			st.setDouble(4, panino.getPrezzo());
			st.setString(5, panino.getDescrizione());
		
			st.executeUpdate();	
			saveIngredienti(panino);
			
			st.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public Panino findByPrimaryKey(String nome_panino,String formato) {
		
		Panino panino = null;
		ArrayList<Ingrediente> ingredienti = cercaIngredienti(nome_panino);
		

		String queryUpdate = "Select *  from panino where nome_panino=? AND formato=?";
		
		try {
			PreparedStatement st = conn.prepareStatement(queryUpdate);
			st.setString(1, nome_panino);
			st.setString(2, formato);
			
			ResultSet rs = st.executeQuery();
			
			if (rs.next()){
				panino = new Panino(nome_panino, formato, rs.getInt("prezzo"), ingredienti, 
						rs.getString("path_image"));
			}
				
				
			rs.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return panino;
	}
			
			
	public ArrayList<Ingrediente> cercaIngredienti(String nome_panino){
		
		ArrayList<Ingrediente> ingr = new ArrayList<Ingrediente>();
		String queryUpdate = "Select *  from ha_ingredienti where nome_panino=?";
		try {
			PreparedStatement st = conn.prepareStatement(queryUpdate);
			st.setString(1, nome_panino);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				ingr.add(new Ingrediente(rs.getString("nome_ingrediente")));
			}
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ingr;
	}
	
	
	@Override
	public List<Panino> findAll() {
		
		List<Panino> panini = new ArrayList<Panino>();
		
		String queryUpdate = "select * from panino";
		
		try {
				PreparedStatement st = conn.prepareStatement(queryUpdate);
				ResultSet rs = st.executeQuery();
				
				while (rs.next()) {
					ArrayList<Ingrediente> ingredienti = cercaIngredienti(rs.getString("nome_panino"));
					Panino p = new Panino(rs.getString("nome_panino"),rs.getString("formato"), rs.getFloat("prezzo"), 
							ingredienti, rs.getString("path_image"));
					p.setDescrizione(rs.getString("descrizione"));
					panini.add(p);
				}
				rs.close();
			}
		catch (Exception e) {
			e.printStackTrace();
		}
	
		return panini;
	}

	@Override
	public boolean update(Panino old_panino,Panino new_panino) {
		if(conn == null)
			return false;
		
		String query = "UPDATE panino set nome_panino=?,"
				+ "path_image=?,formato=?,prezzo=?,descrizione=? where nome_panino=? AND formato=?";

		try {
			PreparedStatement stmt =  conn.prepareStatement(query);
			stmt.setString(1, new_panino.getNome());
			stmt.setString(2, new_panino.getPathImage());
			stmt.setString(3, new_panino.getFormato());
			stmt.setFloat(4, new_panino.getPrezzo());
			stmt.setString (5, new_panino.getDescrizione());
			stmt.setString(6, old_panino.getNome());
			stmt.setString(7, old_panino.getFormato());
			
			updateIngredienti(old_panino,new_panino);
			
			stmt.executeUpdate();
			stmt.close();
			return true;
			
		} catch (SQLException e) {
				e.printStackTrace();
		}
		
	
	return false;
		
	}
	
	
	private boolean updateIngredienti(Panino old_panino,Panino new_panino){
		
		if(conn == null)
			return false;
		
		if(!rimuovi_ingredienti(old_panino))
			return false;
			
		if(saveIngredienti(new_panino))
			return true;
		
		return false;
				
			
	}
	
	public boolean saveIngredienti(Panino new_panino) {
		
		if(conn == null)
			return false;
		
		ArrayList<Ingrediente> ingredienti = new_panino.getIngredienti();
		System.out.println(ingredienti);
		for(Ingrediente it: ingredienti) {
			try {
				inserisciIngrediente(it.getNome_ingrediente());
				String queryUpdate = "INSERT INTO ha_ingredienti(nome_panino,formato_panino,nome_ingrediente) values(?, ?, ?)";
				
				PreparedStatement st = conn.prepareStatement(queryUpdate);
				st.setString(1, new_panino.getNome());
				st.setString(2, new_panino.getFormato());
				st.setString(3, it.getNome_ingrediente());
			
				st.executeUpdate();	
				st.close();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		
		}
		return true;
		
	}

	private boolean inserisciIngrediente(String nome_ingrediente) {
		try {
		
			String queryUpdate = "INSERT INTO ingrediente(nome_ingrediente) values(?)";
			
			PreparedStatement st = conn.prepareStatement(queryUpdate);
			st.setString(1, nome_ingrediente);
		
			
			st.executeUpdate();	
			st.close();
			return true;
			
		} catch (SQLException e) {
			return false;
		}
		
	}

	@Override
	public boolean remove(Panino panino) {
		if(conn == null)
			return false;
		
		String query = "DELETE FROM panino WHERE nome_panino=? AND formato=?";
	
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			rimuovi_ingredienti(panino);
			stmt.setString(1, panino.getNome());
			stmt.setString(2, panino.getFormato());
			stmt.executeUpdate();
		

			stmt.close();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
		
	}
	
	private boolean exists(String nome_panino,String formato) {
		
		if(conn == null)
			return false;
		try {
			String query = "SELECT * FROM panino WHERE nome_panino=? AND formato=?;";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, nome_panino);
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

	private  boolean rimuovi_ingredienti(Panino old_panino) {
		if(conn == null)
			return false;
		
		String query = "DELETE FROM ha_ingredienti WHERE nome_panino=? AND formato_panino=?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, old_panino.getNome());
			stmt.setString(2, old_panino.getFormato());
			System.out.println(old_panino.getNome());
			stmt.executeUpdate();
			stmt.close();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Panino> cerca(String s) {
		
		List<Panino> panini = new ArrayList<Panino>();
		
		String sub = null;
		if(s.length()>3)
			sub = s.substring(0,3);
		
		String queryUpdate = "SELECT * FROM panino WHERE nome_panino ILIKE '%"+s
				+"%' or nome_panino ILIKE '%"
				+sub+"%' ORDER BY nome_panino DESC;";
		
		
		try {
				PreparedStatement st = conn.prepareStatement(queryUpdate);
				ResultSet rs = st.executeQuery();
				
				while (rs.next()) {
					ArrayList<Ingrediente> ingredienti = cercaIngredienti(rs.getString("nome_panino"));
					panini.add(new Panino(rs.getString("nome_panino"),rs.getString("formato"), rs.getFloat("prezzo"), 
							ingredienti, rs.getString("path_image")));
				}
				rs.close();
			}
		catch (Exception e) {
			e.printStackTrace();
		}
	
		return panini;
	}


}