package com.group23.fooddelivery.controllers;


import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.jdbc.UtenteDAOJDBC;


@Controller
public class RegistraAccount {

	@PostMapping("registrazioneAccount")
	public String registra(@RequestParam String username,@RequestParam String password,HttpSession session) {
		DBSource Dbs = DBManager.getDataSource();
		try {
			UtenteDAOJDBC utente = new UtenteDAOJDBC(Dbs);
			utente.register(username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		session.setAttribute("usernameLogged",username);
		return "index";
	}

}
