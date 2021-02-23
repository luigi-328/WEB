package com.group23.fooddelivery.controllers;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group23.fooddelivery.model.user.EmailSender;

import java.io.FileNotFoundException;

@RestController
public class EmailController {
	
	@PostMapping("emailSender")
	public String emailSender(String oggetto, String testo, String email) {
		System.out.println(testo);
		System.out.println(oggetto);
		System.out.println(email);
		try {
			EmailSender.sendMessage(email, oggetto, testo, null, false);
			return "SUCCESS";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

}
