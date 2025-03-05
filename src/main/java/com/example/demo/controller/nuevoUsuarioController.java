package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Usuario;
@Controller
public class nuevoUsuarioController {

	public nuevoUsuarioController() {}

	@GetMapping("/nuevoUsuario")
	public String redireccion(Model model) {
		return "nuevoUsuario";
	}
	
	@PostMapping("/nuevoUsuario")
	public String nuevoUsuario(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password, Model model) {

		if(Usuario.getUsuarioByUsername(username)!=null) {
				model.addAttribute("error","Nombre de usuario ya existente");
				return "nuevoUsuario";
		}
			
			Usuario usuario = new Usuario(username, password);
			usuario.insert();

		return "redirect:/";
	}

}
