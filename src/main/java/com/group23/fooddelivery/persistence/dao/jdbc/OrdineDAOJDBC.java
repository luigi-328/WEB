package com.group23.fooddelivery.persistence.dao.jdbc;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.group23.fooddelivery.model.product.Bevanda;
import com.group23.fooddelivery.model.product.Menu;
import com.group23.fooddelivery.model.product.Panino;
import com.group23.fooddelivery.model.sales.Ordine;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.BevandaDAO;
import com.group23.fooddelivery.persistence.dao.MenuDAO;
import com.group23.fooddelivery.persistence.dao.OrdineDAO;
import com.group23.fooddelivery.persistence.dao.PaninoDAO;

public class OrdineDAOJDBC implements OrdineDAO{

	private DBSource dbSource;
	private Connection conn;
	
	private BevandaDAO bevandaDAO;
	private PaninoDAO paninoDAO;
	private MenuDAO menuDAO;
	public Integer id_ordine;
	
	public OrdineDAOJDBC(DBSource dbS) throws SQLException {
		dbSource = dbS;
		conn = dbSource.getConnection();
		
		bevandaDAO = new BevandaDAOJDBC(dbS);
		menuDAO = new MenuDAOJDBC(dbS);
		paninoDAO = new PaninoDAOJDBC(dbS);
		
	}
	
	
	@Override
	public boolean save(Ordine ordine) {
		if(conn == null)
			return false;	
		
			
		try {
			String query = "INSERT INTO ordine (username,data_ordine,totale,stato,indirizzo_consegna) VALUES (?,?,?,?,?)";
			
			PreparedStatement stmt = conn.prepareStatement(query);
					
			
			stmt.setString(1, ordine.getUsername());
			stmt.setDate(2, ordine.getData_ordine());
			stmt.setFloat(3, ordine.getTotale());
			stmt.setBoolean(4, ordine.getStato());
			stmt.setString(5, ordine.getIndirizzo());
		

			stmt.executeUpdate();
			
			stmt.close();
			// trovo l'id dell'ordine appena aggiunto
			String query2 = "SELECT id_ordine FROM ordine where username=? order by id_ordine desc limit 1;";
			PreparedStatement stmt2 = conn.prepareStatement(query2);
			
			stmt2.setString(1, ordine.getUsername());
			
			ResultSet result = stmt2.executeQuery();
			if(result.next()) {
				ordine.setId_ordine(result.getInt("id_ordine"));
				id_ordine = result.getInt("id_ordine");
				stmt2.close();
			
			}
			///
			
			savePaniniAcquistati(ordine.getPanini(),ordine);
			saveBevandeAcquistate(ordine.getBevande(),ordine);
			saveMenuAcquistati(ordine.getMenu(),ordine);
		
			return true;
	
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}



	private boolean saveMenuAcquistati(Map<Menu,Integer> menu,Ordine ordine) {

		if(conn == null)
			return false;	
		
		Integer id_ordine = ordine.getId_ordine();
		String username = ordine.getUsername();
		
		try {
			String query = "INSERT INTO contiene_menu (quantita , id_ordine ,formato_menu , nome_menu ,username) VALUES (?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			
			for ( Map.Entry<Menu, Integer> entry : ordine.getMenu().entrySet()) {
				
				Integer quantita = entry.getValue();
				String nome_menu = entry.getKey().getNome();
				String formato_menu = entry.getKey().getFormato();
			
				
		
				stmt.setInt(1, quantita);
				stmt.setInt(2, id_ordine);
				stmt.setString(3, formato_menu);
				stmt.setString(4, nome_menu);
				stmt.setString(5, username);
				
	
				stmt.executeUpdate();
				
				
			 }
			stmt.close();
			return true;
	
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}


	private boolean saveBevandeAcquistate(Map<Bevanda,Integer> bevande,Ordine ordine) {
		if(conn == null)
			return false;	
		
		Integer id_ordine = ordine.getId_ordine();
		String username = ordine.getUsername();
		
		try {
			String query = "INSERT INTO contiene_bevande (quantita ,id_ordine ,nome_bevanda, username, formato) VALUES (?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			
			for ( Map.Entry<Bevanda, Integer> entry : ordine.getBevande().entrySet()) {
				
				Integer quantita = entry.getValue();
				String nome_bevanda = entry.getKey().getNome();
				String formato_bevanda = entry.getKey().getFormato();
			
				
		
				stmt.setInt(1, quantita);
				stmt.setInt(2, id_ordine);
				stmt.setString(3, nome_bevanda);
				stmt.setString(4, username);
				stmt.setString(5, formato_bevanda);
				
		
				stmt.executeUpdate();
				
				
			 }
			stmt.close();
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	
		
	}


	private boolean savePaniniAcquistati(Map<Panino,Integer> panini,Ordine ordine) {
			if(conn == null)
				return false;	
			
			Integer id_ordine = ordine.getId_ordine();
			String username = ordine.getUsername();
			
			try {
				String query = "INSERT INTO contiene_panini (quantita ,id_ordine ,nome_panino, username, formato) VALUES (?,?,?,?,?)";
				PreparedStatement stmt = conn.prepareStatement(query);
				
				for ( Map.Entry<Panino, Integer> entry : ordine.getPanini().entrySet()) {
					
					Integer quantita = entry.getValue();
					String nome_panino = entry.getKey().getNome();
					String formato_panino = entry.getKey().getFormato();
				
						
					stmt.setInt(1, quantita);
					stmt.setInt(2, id_ordine);
					stmt.setString(3, nome_panino);
					stmt.setString(4, username);
					stmt.setString(5, formato_panino);
					
			
					stmt.executeUpdate();
					
					
				 }
				stmt.close();
				return true;
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		
	}


	@Override
	public Ordine findByPrimaryKey(Integer id_ordine, String username) {
		Ordine ordine = null;
		

		String queryUpdate = "Select *  from ordine where id_ordine=? AND username=?";
		
		try {
			PreparedStatement st = conn.prepareStatement(queryUpdate);
			st.setInt(1, id_ordine);
			st.setString(2, username);
			
			ResultSet rs = st.executeQuery();
			
			if (rs.next()){
								
				ordine = new Ordine(username,
						rs.getDate("data_ordine"),cercaMenu(id_ordine,username),
						cercaBevande(id_ordine,username), cercaPanini(id_ordine,username));
				ordine.setId_ordine(id_ordine);
			}
				
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ordine;
	}

	private Map<Bevanda,Integer>  cercaBevande(Integer id_ordine, String username) {
		 Map<Bevanda,Integer> bevande = new HashMap<Bevanda, Integer>();
		

		String queryUpdate = "Select *  from contiene_bevande where id_ordine=? AND username=?";
		
		try {
			PreparedStatement st = conn.prepareStatement(queryUpdate);
			st.setInt(1, id_ordine);
			st.setString(2, username);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()){
				String nome_bevanda = rs.getString("nome_bevanda");
				String formato = rs.getString("formato");
				Bevanda b = bevandaDAO.findByPrimaryKey(nome_bevanda, formato);
				bevande.put(b,rs.getInt("quantita"));			
			}
				
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return bevande;
	}


	private Map<Menu,Integer> cercaMenu(Integer id_ordine, String username) {
		 Map<Menu,Integer> menu = new HashMap<Menu, Integer>();
			

			String queryUpdate = "Select *  from contiene_menu where id_ordine=? AND username=?";
			
			try {
				PreparedStatement st = conn.prepareStatement(queryUpdate);
				st.setInt(1, id_ordine);
				st.setString(2, username);
				
				ResultSet rs = st.executeQuery();
				
				while(rs.next()){
					String nome_menu = rs.getString("nome_menu");
					String formato = rs.getString("formato_menu");
					Menu b = menuDAO.findByPrimaryKey(nome_menu, formato);
					menu.put(b,rs.getInt("quantita"));			
				}
					
				rs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return menu;
		
	}


	private Map<Panino,Integer>  cercaPanini(Integer id_ordine, String username) {
		 Map<Panino,Integer> panini = new HashMap<Panino, Integer>();
			

			String queryUpdate = "Select *  from contiene_panini where id_ordine=? AND username=?";
			
			try {
				PreparedStatement st = conn.prepareStatement(queryUpdate);
				st.setInt(1, id_ordine);
				st.setString(2, username);
				
				ResultSet rs = st.executeQuery();
				
				while(rs.next()){
					String nome_panino = rs.getString("nome_panino");
					String formato = rs.getString("formato");
					Panino p = paninoDAO.findByPrimaryKey(nome_panino, formato);
					panini.put(p,rs.getInt("quantita"));			
				}
					
				rs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return panini;
	}


	@Override
	public List<Ordine> findAll(String username) {
		List<Ordine> ordini = new ArrayList<Ordine>();
		try {
			String query = " Select * from ordine where username=?";
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1,username);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()){
								
				Integer id_ordine= rs.getInt("id_ordine");
				Date data_ordine = rs.getDate("data_ordine");
				
				Ordine ordine = new Ordine(username,
						data_ordine,cercaMenu(id_ordine,username),
						cercaBevande(id_ordine,username), cercaPanini(id_ordine,username));
					ordine.setId_ordine(id_ordine);
					ordine.setIndirizzo(rs.getString("indirizzo_consegna"));
					ordine.setStato(rs.getBoolean("stato"));
				ordini.add(ordine);
				
			}
				
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ordini;
	}

	@Override
	public boolean update(Ordine old_ordine,Ordine new_ordine) {
		if(conn == null)
			return false;
		
		String query = "UPDATE ordine set id_ordine=?,username=?,data_ordine=?,totale=? where id_ordine=? AND username=?";

		try {
			PreparedStatement stmt =  conn.prepareStatement(query);
			stmt.setInt(1, new_ordine.getId_ordine());
			stmt.setString(2, new_ordine.getUsername());
			stmt.setDate(3, new_ordine.getData_ordine());
			stmt.setFloat(4, new_ordine.getTotale());
			
			stmt.setInt(5, old_ordine.getId_ordine());
			stmt.setString(6, old_ordine.getUsername());
			updateProdotti( old_ordine, new_ordine);
			
			
			stmt.executeUpdate();
			stmt.close();
			return true;
			
		} catch (SQLException e) {
				e.printStackTrace();
		}
		
	
	return false;
	
		
	}

	private boolean updateProdotti(Ordine old_ordine, Ordine new_ordine) {
			if(conn == null)
				return false;
			
			if(!rimuovi_prodotti(old_ordine))
				return false;
			
			if(savePaniniAcquistati(new_ordine.getPanini(),new_ordine)
			| saveBevandeAcquistate(new_ordine.getBevande(),new_ordine)
			| saveMenuAcquistati(new_ordine.getMenu(),new_ordine))
				return true;
			
			return false;		
			
	}
	
	
	private  boolean rimuovi_prodotti(Ordine old_ordine) { //i prodotti all'interno dell'ordine
		if(conn == null)
			return false;
		
		String query_bevande = "DELETE FROM contiene_bevande WHERE id_ordine=? AND username=?";
		String query_menu = "DELETE FROM contiene_menu WHERE id_ordine=? AND username=?";
		String query_panini = "DELETE FROM contiene_panini WHERE id_ordine=? AND username=?";
	
		try {
			PreparedStatement stmt1 =  conn.prepareStatement(query_bevande);
			PreparedStatement stmt2 =  conn.prepareStatement(query_menu);
			PreparedStatement stmt3 =  conn.prepareStatement(query_panini);
			
			stmt1.setInt(1, old_ordine.getId_ordine());
			stmt2.setInt(1, old_ordine.getId_ordine());
			stmt3.setInt(1, old_ordine.getId_ordine());
			
			stmt1.setString(2, old_ordine.getUsername());
			stmt2.setString(2, old_ordine.getUsername());
			stmt3.setString(2, old_ordine.getUsername());
			
			stmt1.executeUpdate();
			stmt2.executeUpdate();
			stmt3.executeUpdate();
			
			stmt1.close();
			stmt2.close();
			stmt3.close();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}


	@Override
	public boolean remove(Ordine ordine) {
		if(conn == null)
			return false;
		
		rimuovi_prodotti(ordine);
		
		String query = "DELETE FROM ordine WHERE id_ordine=? AND username=?";
	
		try {
			PreparedStatement stmt = null;
			
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, ordine.getId_ordine());
			stmt.setString(2, ordine.getUsername());
			
			stmt.executeUpdate();
			stmt.close();
			
			return true;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return false;
	}


	@Override
	public List<Ordine> getOrdiniDaConsegnare() {
		List<Ordine> ordini = new ArrayList<Ordine>();
		try {
			String query = " Select * from ordine where stato=?";
			PreparedStatement st = conn.prepareStatement(query);
			st.setBoolean(1, false);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()){
				Integer id_ordine= rs.getInt("id_ordine");
				String username = rs.getString("username");
				Date data_ordine = rs.getDate("data_ordine");
				Ordine o = new Ordine(username,data_ordine,
						cercaMenu(id_ordine,username),cercaBevande(id_ordine,username),cercaPanini(id_ordine,username));
						o.setId_ordine(id_ordine);
				o.setIndirizzo(rs.getString("indirizzo_consegna"));
				
				
				
				ordini.add(o);
			}
				
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ordini;
	}
	
	@Override
	public boolean updateStatoOrdine(Integer id_ordine){
		
		if(conn == null)
			return false;
		
		String query = "UPDATE ordine set stato=? where id_ordine=?";

		try {
			PreparedStatement stmt =  conn.prepareStatement(query);
			stmt.setBoolean(1, true);
			stmt.setInt(2, id_ordine);
			
			
			stmt.executeUpdate();
			stmt.close();
			return true;
			
		} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("error");
		}
		return false;		
	}


	@Override
	public Integer getIdOrdine(String username) {
		Integer id = null;
		
		String query2 = "SELECT id_ordine FROM ordine where username=? order by id_ordine desc limit 1";
		try {
			
			PreparedStatement stmt2 = conn.prepareStatement(query2);
			stmt2.setString(1, username);
			
			ResultSet result = stmt2.executeQuery();
			if(result.next()) {
				id = result.getInt("id_ordine");
				stmt2.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	
}