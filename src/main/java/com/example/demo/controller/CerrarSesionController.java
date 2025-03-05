package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class CerrarSesionController {
	
	@GetMapping("/cerrarSesion")
	public String cerrarSesion(HttpSession session,HttpServletResponse response) {
		session.removeAttribute("username");
		Cookie cookieUser = new Cookie("username","");
		Cookie cookiePasswd = new Cookie("password", "");
		
		cookieUser.setMaxAge(0);
		cookiePasswd.setMaxAge(0);
		
		response.addCookie(cookieUser);
		response.addCookie(cookiePasswd);
		return "redirect:/";
	}
}
