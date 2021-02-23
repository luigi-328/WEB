package com.group23.fooddelivery.controllers;

import java.sql.SQLException;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.group23.fooddelivery.model.sales.Ordine;
import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.dao.OrdineDAO;
import com.group23.fooddelivery.persistence.dao.jdbc.OrdineDAOJDBC;

@RestController
public class GestoreConsegneController {
	
	 @PostMapping("getOrdiniDaConsegnareJSON")
	    public String sendOrdersJSON() throws SQLException {
		 Gson gson = new Gson();
			
		List<Ordine> ordini = null;

		OrdineDAO ordinidao = new OrdineDAOJDBC(DBManager.getDataSource());
				
		ordini = ordinidao.getOrdiniDaConsegnare(); 
		String json =  "{\"ordini\": [";
		
		int count = 0;
		for(Ordine o : ordini) {
			
			System.out.println(o.getJSONString());
				 json+=o.getJSONString();
				 if(count+1 != ordini.size())
					 json+=",";
				 count++;
							
		}
		 json+="]}";
		System.out.println(json);
		if(ordini.size()!=0) {
			JsonObject jsonOb = new JsonParser().parse(json).getAsJsonObject();
			json = gson.toJson(jsonOb);
			System.out.println(json);
		}
		  System.out.println(json);
		 return json;
		
	 }	
	 
	 @PostMapping("/confermaConsegna")
	 public void confirm(@RequestParam Integer productId) {
		try {
			 OrdineDAO ordinedao = new OrdineDAOJDBC(DBManager.getDataSource());
			 ordinedao.updateStatoOrdine(productId);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	
	
	
	
}
