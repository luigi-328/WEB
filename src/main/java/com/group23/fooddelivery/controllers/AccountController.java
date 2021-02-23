package com.group23.fooddelivery.controllers;

import java.sql.SQLException;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.group23.fooddelivery.model.user.Indirizzo;
import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.jdbc.IndirizzoDAOJDBC;


@Controller
public class AccountController {

	@GetMapping("/accountInfo")	
	public String accountInfo() {
		return "AccountInfo";
	}
	
	
	@GetMapping("/accountAddres")	
	public String accountAddres(Model model,HttpSession session) {
		DBSource Dbs = DBManager.getDataSource();
		String user = (String) session.getAttribute("usernameLogged");
		List<Indirizzo> listaIndirizzi = new ArrayList<>();
		
		try {
			IndirizzoDAOJDBC indirizzoDB = new IndirizzoDAOJDBC(Dbs);
			listaIndirizzi = indirizzoDB.getIndirizzi(user);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		model.addAttribute("listaIndirizzi",listaIndirizzi);
		
		return "AccountAddres";
	}
	
	@GetMapping("/accountOrder")	
	public String accountOrder() {
		return "AccountOrder";
	}

}
