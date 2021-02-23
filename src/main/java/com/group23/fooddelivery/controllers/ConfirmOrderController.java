package com.group23.fooddelivery.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConfirmOrderController {
	
	
	@PostMapping("success")
	public String goToSuccessPage() {
		return "successOrder";
	}
	
}
