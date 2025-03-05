package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Nota;

@Controller
public class editarController {

	public editarController() {}
	
	@GetMapping("/editar")
	public String editar(@RequestParam(name="id")int id, Model model) {
		model.addAttribute("nota", Nota.getNotaById(id));
		System.out.println(Nota.getNotaById(id));
		return "editarNota";
	}
	
}
