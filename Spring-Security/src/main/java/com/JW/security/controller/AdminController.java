package com.JW.security.controller;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
	@GetMapping("/main.html")
	public String main(ModelMap modelMap){
		String username = (String) modelMap.get("username");
		System.out.println(username);
		modelMap.addAttribute("username",username);
		return "main";
	}
	
	@RequestMapping("/to/no/auth/page.html")
	public String toNoAuthPage() {
		return "no_auth";
	}

}
