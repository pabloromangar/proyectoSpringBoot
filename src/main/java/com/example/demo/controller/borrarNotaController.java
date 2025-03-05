package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import model.Nota;

@Controller
public class borrarNotaController {

	public borrarNotaController() {
		
	}
	
	@GetMapping("/borrarNota")
	public String borrarNota(@RequestParam(name="id")int id, HttpSession ss) {
		Nota nota = new Nota(id, (String) ss.getAttribute("username"), "", "", null);
		nota.delete();
		ss.setAttribute("notas", Nota.getNotas((String) ss.getAttribute("username")));
		return "listado";
	}
}
