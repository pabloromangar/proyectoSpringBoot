package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.Nota;

@Controller
public class IndexController {

	public IndexController() {
	}

	@GetMapping("/")
	public String comprobarSesion(HttpSession ss, Model model, HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
		if(ss.getAttribute("username")!=null) {
			ss.setAttribute("notas", Nota.getNotas((String) ss.getAttribute("username")));
			return "listado";
		}else if(cookies!=null) {
			for(Cookie cookie : cookies) {
				if("password".equals(cookie.getName())){
					model.addAttribute("password",cookie.getValue());
				}
				
				if("username".equals(cookie.getName())) {
					model.addAttribute("username",cookie.getValue());
				}
			}
			return "iniciarSesion";
		}
		return "iniciarSesion";
	}
}
