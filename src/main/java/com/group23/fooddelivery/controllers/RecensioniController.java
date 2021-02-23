package com.group23.fooddelivery.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.group23.fooddelivery.model.user.Recensione;
import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.dao.RecensioneDAO;
import com.group23.fooddelivery.persistence.dao.jdbc.RecensioneDAOJDBC;


@Controller
public class RecensioniController {

	
	 @GetMapping("/recensioni")
	 public String dammiRecensioni(Model model) {
		 List<Recensione> listaRecensioni = new ArrayList<Recensione>();
		 RecensioneDAO recensioneDAO;
		 try {
			 recensioneDAO = new RecensioneDAOJDBC(DBManager.getDataSource());
			 listaRecensioni = recensioneDAO.findAll();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 model.addAttribute("listaRecensioni", listaRecensioni);
		 
		 
		 return "recensioni";
	 }
	
}
