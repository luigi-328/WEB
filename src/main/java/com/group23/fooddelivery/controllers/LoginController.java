package com.group23.fooddelivery.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.jdbc.UtenteDAOJDBC;

@RestController
public class LoginController {
	
	String username = null;
	
	@PostMapping("loginAccount")
	public boolean login(HttpSession session, @RequestParam String username, @RequestParam String password) {
		boolean accesso = false;
		DBSource Dbs = DBManager.getDataSource();
		try {
			UtenteDAOJDBC utente = new UtenteDAOJDBC(Dbs);
			if(utente.login(username, password)) {
				session.setAttribute("usernameLogged",username);
				accesso = true;
				this.username = username;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		return accesso;
	}
}
