package com.group23.fooddelivery.controllers;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.group23.fooddelivery.model.product.*;
import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.dao.BevandaDAO;
import com.group23.fooddelivery.persistence.dao.MenuDAO;
import com.group23.fooddelivery.persistence.dao.PaninoDAO;
import com.group23.fooddelivery.persistence.dao.jdbc.BevandaDAOJDBC;
import com.group23.fooddelivery.persistence.dao.jdbc.MenuDAOJDBC;
import com.group23.fooddelivery.persistence.dao.jdbc.PaninoDAOJDBC;


@Controller
public class CatalogController {
	
	@GetMapping("/catalog")
	public String dammiPanini(Model model) {
		List<Panino> listaPanini = new ArrayList<Panino>();
		PaninoDAO paninoDao;
		try {
			paninoDao = new PaninoDAOJDBC(DBManager.getDataSource());
			listaPanini = paninoDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<Bevanda> listaBevande = new ArrayList<Bevanda>();
		BevandaDAO bevandaDao;
		try {
			bevandaDao = new BevandaDAOJDBC(DBManager.getDataSource());
			listaBevande = bevandaDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<Menu> listaMenu = new ArrayList<Menu>();
		MenuDAO menuDao;
		try {
			menuDao = new MenuDAOJDBC(DBManager.getDataSource());
			listaMenu = menuDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		model.addAttribute("listaPanini", listaPanini);
		model.addAttribute("listaMenu", listaMenu);
		model.addAttribute("listaBevande", listaBevande);
			
		return "catalog";
	}
	
	@GetMapping("/ricerca")
	public String dammiRicerca(@RequestParam(name="valueProduct",required=true)String ricerca,Model model) {
		
		List<Panino> listaPanini = new ArrayList<Panino>();
		PaninoDAO paninoDao;
		try {
			paninoDao = new PaninoDAOJDBC(DBManager.getDataSource());
			listaPanini = paninoDao.cerca(ricerca);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Bevanda> listaBevande = new ArrayList<Bevanda>();
		BevandaDAO bevandaDao;
		try {
			bevandaDao = new BevandaDAOJDBC(DBManager.getDataSource());
			listaBevande = bevandaDao.cerca(ricerca);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		List<Menu> listaMenu = new ArrayList<Menu>();
		try {
			MenuDAO menuDao = new MenuDAOJDBC(DBManager.getDataSource());
			listaMenu = menuDao.cerca(ricerca);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(listaPanini.size()!=0)
			model.addAttribute("listaPanini", listaPanini);
		if(listaMenu.size()!=0)
			model.addAttribute("listaMenu", listaMenu);
		if(listaBevande.size()!=0)
			model.addAttribute("listaBevande", listaBevande);
		
		return "catalog";
	}
	
	@GetMapping("/panini")
	public String ricercaPanini(Model model) {
		List<Panino> listaPanini = new ArrayList<Panino>();
	 
		try {
			PaninoDAO paninoDao = new PaninoDAOJDBC(DBManager.getDataSource());
			listaPanini = paninoDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		model.addAttribute("listaPanini", listaPanini);
	
			
		return "catalog";
	}
	
	@GetMapping("/menu")
	public String ricercaMenu(Model model) {
		List<Menu> listaMenu = new ArrayList<Menu>();
	 
		try {
			MenuDAO menuDao = new MenuDAOJDBC(DBManager.getDataSource());
			listaMenu = menuDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		model.addAttribute("listaMenu", listaMenu);
	
			
		return "catalog";
	}
	
	@GetMapping("/bevande")
	public String ricercaBevande(Model model) {
		List<Bevanda> listaBevande = new ArrayList<Bevanda>();
	 
		try {
			BevandaDAO menuDao = new BevandaDAOJDBC(DBManager.getDataSource());
			listaBevande = menuDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		model.addAttribute("listaBevande", listaBevande);
	
			
		return "catalog";
	}
}
