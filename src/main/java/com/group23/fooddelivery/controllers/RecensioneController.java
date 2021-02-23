package com.group23.fooddelivery.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group23.fooddelivery.model.sales.Ordine;
import com.group23.fooddelivery.model.user.Recensione;
import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.dao.OrdineDAO;
import com.group23.fooddelivery.persistence.dao.RecensioneDAO;
import com.group23.fooddelivery.persistence.dao.jdbc.OrdineDAOJDBC;
import com.group23.fooddelivery.persistence.dao.jdbc.RecensioneDAOJDBC;

@RestController
public class RecensioneController {

	 @PostMapping("inviaRecensione")
	    public boolean recensione(String text,int id,HttpSession session) throws SQLException {
		 	String usern = session.getAttribute("usernameLogged").toString();
		 	StringTokenizer tkr = new StringTokenizer(usern, "@");
		 	String recensore = tkr.nextToken();			
			OrdineDAO ordiniDAO = new OrdineDAOJDBC(DBManager.getDataSource());
			List<Ordine> ordini = null;
			ordini = ordiniDAO.findAll(usern);
			for(Ordine o : ordini ) {
				if(o.getId_ordine() == id) {
					
					if(o.getStato() == false) {
						return false;
					}
					
					
					
					Recensione recensione = new Recensione(text, Integer.toString(id), recensore);
					RecensioneDAO recensioneDAO = new RecensioneDAOJDBC(DBManager.getDataSource());
					return recensioneDAO.save(recensione);
					
				}
			}
			return true;
	 }
	
}