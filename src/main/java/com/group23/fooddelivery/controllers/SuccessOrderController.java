package com.group23.fooddelivery.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.dao.OrdineDAO;
import com.group23.fooddelivery.persistence.dao.jdbc.OrdineDAOJDBC;


@Controller
public class SuccessOrderController {
	
	@PostMapping("home")
	public String home(HttpSession session) throws SQLException{
			return "index";		
	}
	
	@PostMapping("support")
	public String help(HttpSession session) throws SQLException{
			return "contact-us";		
	}
	
	
	
}
