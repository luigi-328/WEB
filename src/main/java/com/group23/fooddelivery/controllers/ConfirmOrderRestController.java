package com.group23.fooddelivery.controllers;
import java.sql.SQLException;


import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.group23.fooddelivery.model.user.Indirizzo;
import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.*;
import com.group23.fooddelivery.persistence.dao.jdbc.*;


@RestController
public class ConfirmOrderRestController {
	
	private Gson gson = new Gson();
    
	 @PostMapping("/aggiungiIndirizzo")
		public boolean aggiungiIndirizzo(@RequestBody String indirizzo, HttpSession session) throws SQLException {
		 
	        JsonElement el = gson.fromJson(indirizzo, JsonElement.class);
	        JsonObject jsonObj = el.getAsJsonObject();
	        String usern = session.getAttribute("usernameLogged").toString();
	        String address = jsonObj.get("nome_indirizzo").getAsString();
	        Integer cap = jsonObj.get("cap").getAsInt();
	        
		 	Indirizzo ind = null;
		 	ind = new Indirizzo(usern,address,cap);
		 	DBSource s = DBManager.getDataSource();
			IndirizzoDAO indirizzi = new IndirizzoDAOJDBC(s);
			return  indirizzi.save(usern, ind);
				
			
	    }
	 // restituisce tutti gli indirizzi
	 
	 @PostMapping("getIndirizziJSON")
	    public String sendAddressesJSON(HttpSession session) throws SQLException {
		 Gson gson = new Gson();
			
		 List<Indirizzo> allAddress = null;

		IndirizzoDAO indirizzi = new IndirizzoDAOJDBC(DBManager.getDataSource());
		allAddress = indirizzi.getIndirizzi(session.getAttribute("usernameLogged").toString()); 
		
		if(allAddress.size() > 0) {
			String json =  "{\"indirizzi\": [";
		
		int count = 0;
		for(Indirizzo ind : allAddress) {
			
			System.out.println(ind.getJSONString());
				 json+=ind.getJSONString();
				 if(count+1 == allAddress.size()) {json+="]}";}
				 else
					 json+=",";
				 count++;
							
		}
		System.out.println(json);
		JsonObject jsonOb = new JsonParser().parse(json).getAsJsonObject();
		json = gson.toJson(jsonOb);
		//System.out.println(json);
		  
		 return json;
		} else {
			return "null";
		}
		
	 }
	 
	 @PostMapping("getId")
		public String getId(HttpSession session) {
			Integer id = null;
			
			try {
				OrdineDAO ordine = new OrdineDAOJDBC(DBManager.getDataSource());
				id = ordine.getIdOrdine(session.getAttribute("usernameLogged").toString());
				System.out.println(id);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			return id.toString();
		}
	 
	 
}