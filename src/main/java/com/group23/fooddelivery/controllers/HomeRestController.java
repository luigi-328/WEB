package com.group23.fooddelivery.controllers;

import java.sql.Date;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.group23.fooddelivery.model.product.*;
import com.group23.fooddelivery.model.sales.Ordine;
import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.BevandaDAO;
import com.group23.fooddelivery.persistence.dao.MenuDAO;
import com.group23.fooddelivery.persistence.dao.OrdineDAO;
import com.group23.fooddelivery.persistence.dao.PaninoDAO;
import com.group23.fooddelivery.persistence.dao.jdbc.BevandaDAOJDBC;
import com.group23.fooddelivery.persistence.dao.jdbc.MenuDAOJDBC;
import com.group23.fooddelivery.persistence.dao.jdbc.OrdineDAOJDBC;
import com.group23.fooddelivery.persistence.dao.jdbc.PaninoDAOJDBC;

import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRestController {
    
    private Ordine ordine; 
    
	 DateTime time = new DateTime();
	 Date date = Date.valueOf(time.toString("yyyy-MM-dd"));

    @PostMapping("getCurrentOrder")
    public String getCurrentJSON(HttpSession session) {
        if(session.getAttribute("usernameLogged") != null) {
            if(ordine == null) {
                ordine = new Ordine(session.getAttribute("usernameLogged").toString(), date, new HashMap<Menu, Integer>(), new HashMap<Bevanda, Integer>(), new HashMap<Panino, Integer>());
            }
            System.out.println("JSON:");
            System.out.println(ordine.getJSONString());
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonParser().parse(ordine.getJSONString()).getAsJsonObject();
            String json = gson.toJson(jsonObject);
 
            return json;
        }
        
        return "notlogged";
    }

    @PostMapping("logout")
	public void logout(HttpSession session) {
        session.invalidate();
        ordine = null;
	}

	@PostMapping("updateOrder")
	public void updateOrder(@RequestParam String json,HttpSession session) {
        DBSource source = DBManager.getDataSource();

        Gson gson = new Gson();
        JsonElement element = gson.fromJson(json, JsonElement.class);
        JsonObject object = element.getAsJsonObject();

        Map<Menu,Integer> menuList = new HashMap<Menu,Integer>();
        Map<Panino,Integer> paniniList = new HashMap<Panino,Integer>();
        Map<Bevanda,Integer> bevandeList = new HashMap<Bevanda,Integer>();

        if(object.has("menu")) {
            JsonArray menuJson = object.get("menu").getAsJsonArray();

            try {
                for(JsonElement el : menuJson) {
                    JsonObject obj = el.getAsJsonObject();
                    MenuDAO menuDAO = new MenuDAOJDBC(source);
                    System.out.println(obj.get("nome_menu").getAsString());
                    Menu menu = menuDAO.findByPrimaryKey(obj.get("nome_menu").getAsString(), obj.get("formato_menu").getAsString());
                    if(menu != null) {
                        Integer quantita = obj.get("quantita").getAsInt();
                        menuList.put(menu, quantita);
                        System.out.println(menu.getNome());
                    } else {
                        System.out.println("NULL");
                    }
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }

        if(object.has("panini")) {
            JsonArray paniniJson = object.get("panini").getAsJsonArray();

            try {
                for(JsonElement el : paniniJson) {
                    JsonObject obj = el.getAsJsonObject();
                    PaninoDAO paniniDAO = new PaninoDAOJDBC(source);
                    Panino panino = paniniDAO.findByPrimaryKey(obj.get("nome_panino").getAsString(), obj.get("formato_panino").getAsString());
                    if(panino != null) {
                        Integer quantita = obj.get("quantita").getAsInt();
                        paniniList.put(panino, quantita);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(object.has("bevande")) {
            JsonArray bevandeJson = object.get("bevande").getAsJsonArray();

            try {
                for(JsonElement el : bevandeJson) {
                    JsonObject obj = el.getAsJsonObject();
                    BevandaDAO bevandaDAO = new BevandaDAOJDBC(source);
                    Bevanda bevanda = bevandaDAO.findByPrimaryKey(obj.get("nome_bevanda").getAsString(), obj.get("formato_bevanda").getAsString());
                    if(bevanda != null) {
                        Integer quantita = obj.get("quantita").getAsInt();
                        bevandeList.put(bevanda, quantita);
                    }
            }
                ordine.setMenu(menuList);
                ordine.setBevande(bevandeList);
                ordine.setPanini(paniniList);
             
               
                
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

  
    }

    // CART

    @PostMapping("/removeProduct")
	public boolean removeProduct(@RequestParam String productName, @RequestParam String productFormat, @RequestParam String productType) {
        switch(productType) {
            case "menu":
                Map<Menu, Integer> currMenu = ordine.getMenu();
                Menu menuToDelete = null;

                for(Map.Entry<Menu, Integer> entry : currMenu.entrySet()) {
                    if(entry.getKey().getNome().equals(productName) && entry.getKey().getFormato().equals(productFormat)) {
                        menuToDelete = entry.getKey();
                    } 
                }

                if(currMenu.remove(menuToDelete) != null) {
                    return true;
                }

                return false;
            
            case "panino":
                Map<Panino, Integer> currPanini = ordine.getPanini();
                Panino paninoToDelete = null;

                for(Map.Entry<Panino, Integer> entry : currPanini.entrySet()) {
                    if(entry.getKey().getNome().equals(productName) && entry.getKey().getFormato().equals(productFormat)) {
                        paninoToDelete = entry.getKey();
                    } 
                }

                if(currPanini.remove(paninoToDelete) != null) {
                    return true;
                }

                return false;

            default:
                Map<Bevanda, Integer> currBevande = ordine.getBevande();
                Bevanda bevandaToDelete = null;

                for(Map.Entry<Bevanda, Integer> entry : currBevande.entrySet()) {
                    if(entry.getKey().getNome().equals(productName) && entry.getKey().getFormato().equals(productFormat)) {
                        bevandaToDelete = entry.getKey();
                    } 
                }

                if(currBevande.remove(bevandaToDelete) != null) {
                    return true;
                }

                return false;
        }
    }

    @PostMapping("/addQuantity")
    public boolean addQuantity(@RequestParam String productName, @RequestParam String productFormat, @RequestParam String productType) {
        switch(productType) {
            case "menu":
                Map<Menu, Integer> currMenu = ordine.getMenu();
                Menu menuToUpdate = null;

                for(Map.Entry<Menu, Integer> entry : currMenu.entrySet()) {
                    if(entry.getKey().getNome().equals(productName) && entry.getKey().getFormato().equals(productFormat)) {
                        menuToUpdate = entry.getKey();
                    } 
                }

                Integer prevMenuQuantity = currMenu.get(menuToUpdate);
                if(currMenu.put(menuToUpdate, prevMenuQuantity+1) != null) {
                    return true;
                }

                return false;
            
            case "panino":
                Map<Panino, Integer> currPanini = ordine.getPanini();
                Panino paninoToUpdate = null;

                for(Map.Entry<Panino, Integer> entry : currPanini.entrySet()) {
                    if(entry.getKey().getNome().equals(productName) && entry.getKey().getFormato().equals(productFormat)) {
                        paninoToUpdate = entry.getKey();
                    }
                }

                Integer prevPaninoQuantity = currPanini.get(paninoToUpdate);
                if(currPanini.put(paninoToUpdate, prevPaninoQuantity+1) != null) {
                    return true;
                }

                return false;

            default:
                Map<Bevanda, Integer> currBevande = ordine.getBevande();
                Bevanda bevandaToUpdate = null;

                for(Map.Entry<Bevanda, Integer> entry : currBevande.entrySet()) {
                    if(entry.getKey().getNome().equals(productName) && entry.getKey().getFormato().equals(productFormat)) {
                        bevandaToUpdate = entry.getKey();
                    }
                }

                Integer prevBevandaQuantity = currBevande.get(bevandaToUpdate);
                if(currBevande.put(bevandaToUpdate, prevBevandaQuantity+1) != null) {
                    return true;
                }

                return false;
        }
    }

    @PostMapping("/removeQuantity")
    public boolean removeQuantity(@RequestParam String productName, @RequestParam String productFormat, @RequestParam String productType) {
        switch(productType) {
            case "menu":
                Map<Menu, Integer> currMenu = ordine.getMenu();
                Menu menuToUpdate = null;

                for(Map.Entry<Menu, Integer> entry : currMenu.entrySet()) {
                    if(entry.getKey().getNome().equals(productName) && entry.getKey().getFormato().equals(productFormat)) {
                        menuToUpdate = entry.getKey();
                    } 
                }

                Integer prevMenuQuantity = currMenu.get(menuToUpdate);
                if(prevMenuQuantity-1 == 0) {
                    return removeProduct(productName, productFormat, productType);
                } else {
                    if(currMenu.put(menuToUpdate, prevMenuQuantity-1) != null) {
                        return true;
                    }
                }

                return false;
            
            case "panino":
                Map<Panino, Integer> currPanini = ordine.getPanini();
                Panino paninoToUpdate = null;

                for(Map.Entry<Panino, Integer> entry : currPanini.entrySet()) {
                    if(entry.getKey().getNome().equals(productName) && entry.getKey().getFormato().equals(productFormat)) {
                        paninoToUpdate = entry.getKey();
                    }
                }

                Integer prevPaninoQuantity = currPanini.get(paninoToUpdate);
                if(prevPaninoQuantity-1 == 0) {
                    return removeProduct(productName, productFormat, productType);
                } else {
                    if(currPanini.put(paninoToUpdate, prevPaninoQuantity-1) != null) {
                        return true;
                    }
                }

                return false;

            default:
                Map<Bevanda, Integer> currBevande = ordine.getBevande();
                Bevanda bevandaToUpdate = null;

                for(Map.Entry<Bevanda, Integer> entry : currBevande.entrySet()) {
                    if(entry.getKey().getNome().equals(productName) && entry.getKey().getFormato().equals(productFormat)) {
                        bevandaToUpdate = entry.getKey();
                    }
                }

                Integer prevBevandaQuantity = currBevande.get(bevandaToUpdate);
                if(prevBevandaQuantity-1 == 0) {
                    return removeProduct(productName, productFormat, productType);
                } else {
                    if(currBevande.put(bevandaToUpdate, prevBevandaQuantity-1) != null) {
                        return true;
                    }
                }

                return false;
        }
    }
    
	    @PostMapping("saveOrder")
	    public void saveOrder(@RequestBody String indirizzo, HttpSession session ) {
		 	      
	            try {
	            	 Gson gson = new Gson();
	                 JsonElement element = gson.fromJson(indirizzo, JsonElement.class);
	            	 JsonObject obj = element.getAsJsonObject();
	            	 
	            	 Float totale = obj.get("price").getAsFloat();
	            	 String ind = obj.get("indirizzo").getAsString();
	            	 
		            	ordine.setStato(false);
		            	ordine.setIndirizzo(ind);
		            	ordine.setTotale(totale);
		            	
					OrdineDAO ordinedao = new OrdineDAOJDBC(DBManager.getDataSource());
					ordinedao.save(ordine);
					ordine = null;
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            

	
	    }
}