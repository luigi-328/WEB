package com.group23.fooddelivery.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    
	@GetMapping("/")	
	public String home() {
		return "index";
	}

	@GetMapping("indexadmin")	
	public String indexAdmin() {
		return "indexadmin";
	}

	@GetMapping("gestoreConsegne")	
	public String indexConsegne() {
		return "gestoreConsegne";
	}
	
	@PostMapping("login")
	public String login() {	
		return "accesso";
	}

	@GetMapping("signup")
	public String signup() {	
		return "Registrati";
	}
	
	@GetMapping("chisiamo")
	public String chisiamo() {	
		return "chisiamo";
	}

	@PostMapping("cart")
	public String goToCart() {
		return "cart";
	}

	@PostMapping("catalog")
	public String goToCatalog() {
		return "catalog";
	}

	@PostMapping("contact-us")
	public String goToContactUs() {
		return "contact-us";
	}

	@GetMapping("contact-us")
	public String getContactUs() {
		return "contact-us";
	}

	@PostMapping("confirm_order")
    public String confirmOrder() {
    	return "confirmOrder";
	}

}
