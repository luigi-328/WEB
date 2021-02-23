package com.group23.fooddelivery.controllers;

import java.sql.SQLException;


import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.group23.fooddelivery.model.user.Indirizzo;
import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.jdbc.IndirizzoDAOJDBC;

@RestController
public class AddresController {
	
	Gson gson = new Gson();
	
	@PostMapping("rimuoviIndirizzo")
	public boolean rimuoviIndirizzo(@RequestParam String indirizzo,HttpSession session) {
	
		DBSource Dbs = DBManager.getDataSource();
		String user = (String) session.getAttribute("usernameLogged");
		Indirizzo in = new Indirizzo(user,indirizzo,null);	
		try {
			IndirizzoDAOJDBC indirizzoDB = new IndirizzoDAOJDBC(Dbs);
			return indirizzoDB.remove(user, in);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
