package com.group23.fooddelivery.model.sales;

import java.sql.Date;

import java.util.Map;

import com.group23.fooddelivery.model.product.Bevanda;
import com.group23.fooddelivery.model.product.Ingrediente;
import com.group23.fooddelivery.model.product.Menu;
import com.group23.fooddelivery.model.product.Panino;

public class Ordine {
	
	private Integer id_ordine;
	private String username;  
	private Date data_ordine;
	private float totale;
	private String indirizzo;
	private boolean stato;

	
	private Map<Menu,Integer> menu;
	private Map<Bevanda,Integer> bevande;
	private Map<Panino,Integer> panini;
	
	public Ordine() {
		
	}
	
	public Ordine(String username, Date data_ordine, Map<Menu,Integer> menu,
			Map<Bevanda,Integer> bevande, Map<Panino,Integer> panini) {
		super();
		id_ordine = 0;
		this.username = username;
		this.data_ordine = data_ordine;
		totale = calcolaTotale(menu,bevande,panini);
		this.menu = menu;
		this.bevande = bevande;
		this.panini = panini;
		indirizzo = "";
		stato = false; // non consegnato
		
	}
	
	
	public String getIndirizzo() {
		return indirizzo;
	}


	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	public void setTotale(float totale) {
		this.totale = totale;
	}


	private float calcolaTotale(Map<Menu,Integer> menu, Map<Bevanda,Integer> bevande, Map<Panino,Integer> panini) {

		float totale = 2.5f;
		
		 
		 for ( Map.Entry<Menu, Integer> entry : menu.entrySet()) {
			    Menu key = entry.getKey();
			    Integer value = entry.getValue();
			    totale+=key.getPrezzo()*value;
		 }
		 for ( Map.Entry<Bevanda, Integer> entry : bevande.entrySet()) {
			    Bevanda key = entry.getKey();
			    Integer value = entry.getValue();
			    totale+=key.getPrezzo()*value;
		 }
		 for ( Map.Entry<Panino, Integer> entry : panini.entrySet()) {
			 	Panino key = entry.getKey();
			    Integer value = entry.getValue();
			    totale+=key.getPrezzo()*value;
		 }
				
		return totale;
	}

	private int calcolaQuantitaProdotti() {
		int count = 0;

		for ( Map.Entry<Menu, Integer> entry : menu.entrySet()) {
			Integer quantita = entry.getValue();
			count+=quantita;
		} 
		 
	 	for ( Map.Entry<Bevanda, Integer> entry : bevande.entrySet()) {
			Integer quantita = entry.getValue();
			count+=quantita;
		}
		 
	 	for ( Map.Entry<Panino, Integer> entry : panini.entrySet()) {
			Integer quantita = entry.getValue();
			count+=quantita;
	 	}

		return count;
	}

	public Map<Menu,Integer> getMenu() {
		return menu;
	}
	public void setMenu(Map<Menu,Integer> menu) {
		this.menu = menu;
	}
	public Map<Bevanda,Integer> getBevande() {
		return bevande;
	}
	public void setBevande(Map<Bevanda,Integer> bevande) {
		this.bevande = bevande;
	}
	public Map<Panino,Integer> getPanini() {
		return panini;
	}
	public void setPanini(Map<Panino,Integer> panini) {
		this.panini = panini;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getId_ordine() {
		return id_ordine;
	}
	public void setId_ordine(Integer id_ordine) {
		this.id_ordine = id_ordine;
	}
	
	public Date getData_ordine() {
		return data_ordine;
	}
	public void setData_ordine(Date data_ordine) {
		this.data_ordine = data_ordine;
	}
	public float getTotale() {
		return totale;
	}
	public void setTotale(Float totale) {
		this.totale = totale;
	}

	public String getJSONString() {
		String json = "{";
		json += "\"id\": " + id_ordine + ",";
		json += "\"username\": \"" + username + "\",";
		json += "\"indirizzo\": \"" + indirizzo + "\",";
		json += "\"totale\": " + calcolaTotale(menu, bevande, panini)+ ",";
		json += "\"quantitaProdotti\": " + calcolaQuantitaProdotti()+ ",";
		json += "\"menu\":[";

		if(menu.size() == 0) {
			json+="],";
		} else {
			int menuCounter = 0;

			for(Map.Entry<Menu,Integer> entry : menu.entrySet()) {
				json+="{\"nome_menu\": \"" + entry.getKey().getNome() + "\",";
				json+="\"formato_menu\": \"" + entry.getKey().getFormato() + "\",";
				json+="\"immagine_menu\": \"" + entry.getKey().getPath_image() + "\",";
				json+="\"nome_panino\": \"" + entry.getKey().getPanino().getNome() + "\",";
				json+="\"formato_panino\": \"" + entry.getKey().getPanino().getFormato() + "\",";
				json+="\"nome_bevanda\": \"" + entry.getKey().getBevanda().getNome() + "\",";
				json+="\"formato_bevanda\": \"" + entry.getKey().getBevanda().getFormato() + "\",";
				json+="\"prezzo\": " + entry.getKey().getPrezzo() + ",";
				json+="\"quantita\": " + entry.getValue() + "}";

				if(menuCounter+1 == menu.size()) {
					json+="],";
				} else {
					json+=",";
				}

				menuCounter++;
			}
		}

		json += "\"panini\":[";

		if(panini.size() == 0) {
			json+="],";
		} else {
			int paniniCounter = 0;

			for(Map.Entry<Panino,Integer> entry : panini.entrySet()) {
				json+="{\"nome_panino\": \"" + entry.getKey().getNome() + "\",";
				json+="\"formato_panino\": \"" + entry.getKey().getFormato() + "\",";
				json+="\"immagine_panino\": \"" + entry.getKey().getPathImage() + "\",";
				json+="\"ingredienti\":[";
				int ingredientsCounter = 0;
				
				if(entry.getKey().getIngredienti().size() == 0) {
					json+="],";
				} else {
					for(Ingrediente ingrediente : entry.getKey().getIngredienti()) {
						json+="\"" + ingrediente.getNome_ingrediente() + "\"";
		
						if(ingredientsCounter+1 == entry.getKey().getIngredienti().size()) {
							json+="],";
						} else {
							json+=",";
						}
		
						ingredientsCounter++;
					}
				}
	
				json+="\"prezzo\": " + entry.getKey().getPrezzo() + ",";
				json+="\"quantita\": " + entry.getValue() + "}";
	
				if(paniniCounter+1 == panini.size()) {
					json+="],";
				} else {
					json+=",";
				}
	
				paniniCounter++;
			}
		}

		json += "\"bevande\":[";

		if(bevande.size()==0) {
			json+="]";
		} else {
			int bevandaCounter = 0;

			for(Map.Entry<Bevanda,Integer> entry : bevande.entrySet()) {
				json+="{\"nome_bevanda\": \"" + entry.getKey().getNome() + "\",";
				json+="\"formato_bevanda\": \"" + entry.getKey().getFormato() + "\",";
				json+="\"immagine_bevanda\": \"" + entry.getKey().getPath_image() + "\",";
				json+="\"prezzo\": " + entry.getKey().getPrezzo() + ",";
				json+="\"quantita\": " + entry.getValue() + "}";
	
				if(bevandaCounter+1 == bevande.size()) {
					json+="]";
				} else {
					json+=",";
				}
	
				bevandaCounter++;
			}
		}

		json += "}";

		return json;
	}


	public boolean getStato() {
		return stato;
	}


	public void setStato(boolean stato) {
		this.stato = stato;
	}
	
	public String getJSONStringOrder() {
		String json = "{";
		json += "\"id\": " + id_ordine + ",";
		json += "\"username\": \"" + username + "\",";
		json+="\"fase\": \"" + getStato()+ "\",";
		json += "\"indirizzo\": \"" + indirizzo + "\",";
		json += "\"totale\": " + calcolaTotale(menu, bevande, panini)+ ",";
		json += "\"quantitaProdotti\": " + calcolaQuantitaProdotti()+ ",";
		json += "\"menu\":[";

		if(menu.size() == 0) {
			json+="],";
		} else {
			int menuCounter = 0;

			for(Map.Entry<Menu,Integer> entry : menu.entrySet()) {
				json+="{\"nome_menu\": \"" + entry.getKey().getNome() + "\",";
				json+="\"formato_menu\": \"" + entry.getKey().getFormato() + "\",";
				json+="\"nome_panino\": \"" + entry.getKey().getPanino().getNome() + "\",";
				json+="\"formato_panino\": \"" + entry.getKey().getPanino().getFormato() + "\",";
				json+="\"nome_bevanda\": \"" + entry.getKey().getBevanda().getNome() + "\",";
				json+="\"formato_bevanda\": \"" + entry.getKey().getBevanda().getFormato() + "\",";
				json+="\"prezzo\": " + entry.getKey().getPrezzo() + ",";
				json+="\"quantita\": " + entry.getValue() + "}";

				if(menuCounter+1 == menu.size()) {
					json+="],";
				} else {
					json+=",";
				}

				menuCounter++;
			}
		}

		json += "\"panini\":[";

		if(panini.size() == 0) {
			json+="],";
		} else {
			int paniniCounter = 0;

			for(Map.Entry<Panino,Integer> entry : panini.entrySet()) {
				json+="{\"nome_panino\": \"" + entry.getKey().getNome() + "\",";
				json+="\"formato_panino\": \"" + entry.getKey().getFormato() + "\",";
				json+="\"ingredienti\":[";
				int ingredientsCounter = 0;
				
				if(entry.getKey().getIngredienti().size() == 0) {
					json+="],";
				} else {
					for(Ingrediente ingrediente : entry.getKey().getIngredienti()) {
						json+="\"" + ingrediente.getNome_ingrediente() + "\"";
		
						if(ingredientsCounter+1 == entry.getKey().getIngredienti().size()) {
							json+="],";
						} else {
							json+=",";
						}
		
						ingredientsCounter++;
					}
				}
	
				json+="\"prezzo\": " + entry.getKey().getPrezzo() + ",";
				json+="\"quantita\": " + entry.getValue() + "}";
	
				if(paniniCounter+1 == panini.size()) {
					json+="],";
				} else {
					json+=",";
				}
	
				paniniCounter++;
			}
		}

		json += "\"bevande\":[";

		if(bevande.size()==0) {
			json+="]";
		} else {
			int bevandaCounter = 0;

			for(Map.Entry<Bevanda,Integer> entry : bevande.entrySet()) {
				json+="{\"nome_bevanda\": \"" + entry.getKey().getNome() + "\",";
				json+="\"formato_bevanda\": \"" + entry.getKey().getFormato() + "\",";
				json+="\"prezzo\": " + entry.getKey().getPrezzo() + ",";
				json+="\"quantita\": " + entry.getValue() + "}";
	
				if(bevandaCounter+1 == bevande.size()) {
					json+="]";
				} else {
					json+=",";
				}
	
				bevandaCounter++;
			}
		}

		json += "}";

		return json;
	}



}
