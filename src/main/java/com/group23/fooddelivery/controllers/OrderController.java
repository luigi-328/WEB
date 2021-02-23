package com.group23.fooddelivery.controllers;

import java.sql.SQLException;

import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.group23.fooddelivery.model.sales.Ordine;
import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.dao.OrdineDAO;
import com.group23.fooddelivery.persistence.dao.jdbc.OrdineDAOJDBC;

@RestController
public class OrderController {

		@PostMapping("getOrdiniEffettuati")
		public String sendOrder(HttpSession session) throws SQLException {
		 	Gson gson = new Gson();
			String user = (String) session.getAttribute("usernameLogged");
			OrdineDAO ordinidao = new OrdineDAOJDBC(DBManager.getDataSource());
			List<Ordine> ordini = null;
			ordini = ordinidao.findAll(user);
			String json =  "{\"ordini\": [";
			int count = 0;
			for(Ordine o : ordini) {
				
				System.out.println(o.getJSONStringOrder());
					 json+=o.getJSONStringOrder();
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
		
	@PostMapping("annullaOrdine")	
	public void annulla(Integer productId,HttpSession session) {
		System.out.println("sono qui");
		System.out.println(productId);
		
		String user = (String) session.getAttribute("usernameLogged");
		Ordine or = new Ordine();
		or.setId_ordine(productId);
		or.setUsername(user);
		try {
			OrdineDAOJDBC ordinedao = new OrdineDAOJDBC(DBManager.getDataSource());
			ordinedao.remove(or);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
		
}
