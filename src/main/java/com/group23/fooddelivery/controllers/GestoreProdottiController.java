package com.group23.fooddelivery.controllers;

import java.sql.SQLException;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.group23.fooddelivery.model.product.*;

import com.group23.fooddelivery.persistence.DBManager;
import com.group23.fooddelivery.persistence.DBSource;
import com.group23.fooddelivery.persistence.dao.BevandaDAO;
import com.group23.fooddelivery.persistence.dao.MenuDAO;
import com.group23.fooddelivery.persistence.dao.PaninoDAO;
import com.group23.fooddelivery.persistence.dao.jdbc.BevandaDAOJDBC;
import com.group23.fooddelivery.persistence.dao.jdbc.MenuDAOJDBC;
import com.group23.fooddelivery.persistence.dao.jdbc.PaninoDAOJDBC;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GestoreProdottiController {

@PostMapping("addProductToDB")
public boolean addProductToDB(@RequestParam String json) {
    boolean aggiunto = false;

    System.out.println(json);

    DBSource source = DBManager.getDataSource();
    Gson gson = new Gson();
    JsonElement element = gson.fromJson(json, JsonElement.class);
    JsonObject object = element.getAsJsonObject();

    String tipo = object.get("tipo").getAsString();

    if(tipo.equals("panino")) {
        String nome = object.get("nome").getAsString();
        String formato = object.get("formato").getAsString();
        Float prezzo = object.get("prezzo").getAsFloat();
        String immagine = object.get("immagine").getAsString();
        String descrizione = object.get("descrizione").getAsString();
        JsonArray ingredientiJson = object.get("ingredienti").getAsJsonArray();
        ArrayList<Ingrediente> ingredienti = new ArrayList<Ingrediente>();

        for(JsonElement el:ingredientiJson) {
            Ingrediente ing = new Ingrediente(el.getAsString());
            ingredienti.add(ing);
        }

        Panino panino = new Panino(nome, formato, prezzo, ingredienti, immagine);
        panino.setDescrizione(descrizione);
        try {
            PaninoDAO paninoDAO = new PaninoDAOJDBC(source);
            if(paninoDAO.save(panino)) {
                aggiunto = true;
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    } else if(tipo.equals("bevanda")) {
        String nome = object.get("nome").getAsString();
        String formato = object.get("formato").getAsString();
        Float prezzo = object.get("prezzo").getAsFloat();
        String immagine = object.get("immagine").getAsString();

        Bevanda bevanda = new Bevanda(nome, formato, prezzo, immagine);
        try {
            BevandaDAO bevandaDAO = new BevandaDAOJDBC(source);
            if(bevandaDAO.save(bevanda)) {
                aggiunto = true;
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } else if(tipo.equals("menu")) {
        String nome = object.get("nome").getAsString();
        String formato = object.get("formato").getAsString();
        Float prezzo = object.get("prezzo").getAsFloat();
        String immagine = object.get("immagine").getAsString();
        String nome_panino = object.get("nome_panino").getAsString();
        String formato_panino = object.get("formato_panino").getAsString();
        String nome_bevanda = object.get("nome_bevanda").getAsString();
        String formato_bevanda = object.get("formato_bevanda").getAsString();
        String descrizione = object.get("descrizione").getAsString();
        Panino panino;
        Bevanda bevanda;

        try {
            PaninoDAO paninoDAO = new PaninoDAOJDBC(source);
            panino = paninoDAO.findByPrimaryKey(nome_panino, formato_panino);

            System.out.println(panino.getNome());
            BevandaDAO bevandaDAO = new BevandaDAOJDBC(source);
            bevanda = bevandaDAO.findByPrimaryKey(nome_bevanda, formato_bevanda);

            System.out.println(bevanda.getNome());
            Menu menu = new Menu(nome, formato, panino, bevanda, prezzo, immagine);
            menu.setDescrizione(descrizione);
            MenuDAO menuDAO = new MenuDAOJDBC(source);
            if(menuDAO.save(menu)) {
                aggiunto = true;
            }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    return aggiunto;
}

@PostMapping("removeProductFromDB")
public boolean removeProductFromDb(@RequestParam String json) {
    boolean rimosso = false;
    System.out.println(json);

    DBSource source = DBManager.getDataSource();
    Gson gson = new Gson();
    JsonElement element = gson.fromJson(json, JsonElement.class);
    JsonObject object = element.getAsJsonObject();

    String tipo = object.get("tipo").getAsString();

    if(tipo.equals("panino")) {
        String nome = object.get("nome").getAsString();
        String formato = object.get("formato").getAsString();

        try {
            PaninoDAO paninoDAO = new PaninoDAOJDBC(source);
            Panino panino = paninoDAO.findByPrimaryKey(nome, formato);
            if(paninoDAO.remove(panino)) {
                rimosso = true;
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    } else if(tipo.equals("bevanda")) {
        String nome = object.get("nome").getAsString();
        String formato = object.get("formato").getAsString();

        try {
            BevandaDAO bevandaDAO = new BevandaDAOJDBC(source);
            Bevanda bevanda = bevandaDAO.findByPrimaryKey(nome, formato);
            if(bevandaDAO.remove(bevanda)) {
                rimosso = true;
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } else if(tipo.equals("menu")) {
        String nome = object.get("nome").getAsString();
        String formato = object.get("formato").getAsString();

        try {
            MenuDAO menuDAO = new MenuDAOJDBC(source);
            if(menuDAO.remove(nome, formato)) {
                rimosso = true;
            }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    return rimosso;
}
    
}
