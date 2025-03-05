package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;


@Controller
public class IniciarSesionController {
	public IniciarSesionController() {
	}

	@GetMapping("/iniciarSesion")
	public String redireccion(Model model) {
		return "iniciarSesion";
	}

	@PostMapping("/iniciarSesion")
	public String iniciarSesion(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password, HttpSession session, Model model, HttpServletResponse response) {

		Usuario usuario = Usuario.getUsuarioByUsername(username);
		
		if (usuario != null && usuario.getPassword().equals(password)) {
			session.setAttribute("username", username);
			Cookie cookieUser = new Cookie("username",username);
			Cookie cookiePasswd = new Cookie("password", usuario.getPassword());
			
			cookieUser.setMaxAge(60*60*24*365);
			cookiePasswd.setMaxAge(60*60*24*365);

			response.addCookie(cookieUser);
			response.addCookie(cookiePasswd);
			
			return "redirect:/";
		}

		model.addAttribute("error", "Usuario y/o contraseña incorrectos");
		return "iniciarSesion";

	}
}
