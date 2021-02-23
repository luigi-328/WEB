package com.group23.fooddelivery.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.dao.OrdineDAO;
import com.group23.fooddelivery.persistence.dao.jdbc.OrdineDAOJDBC;
import com.group23.fooddelivery.persistence.dao.jdbc.UtenteDAOJDBC;

@RestController
public class PasswordController {

	
	@PostMapping("cambiaPassword")
	public boolean pass(@RequestParam String password,HttpSession session) {
		String user = (String) session.getAttribute("usernameLogged");
		try {
			UtenteDAOJDBC utente = new UtenteDAOJDBC (DBManager.getDataSource());
			return utente.update_passw(user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
}
