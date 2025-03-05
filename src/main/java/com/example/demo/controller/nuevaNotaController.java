package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import model.Nota;

@Controller
public class nuevaNotaController {
	
	public nuevaNotaController() {}

	@GetMapping("/nuevaNota")
	public String crearNota(@RequestParam(name="titulo") String titulo, @RequestParam(name="nota") String nota, HttpSession session, Model model) {
		Nota nuevaNota = new Nota(null, (String) session.getAttribute("username"), titulo, nota, LocalDateTime.now());
		nuevaNota.insert();
		ArrayList<Nota> notas = Nota.getNotas((String) session.getAttribute("username"));
	    session.setAttribute("notas", notas);
		return "listado";
	}
}
	

