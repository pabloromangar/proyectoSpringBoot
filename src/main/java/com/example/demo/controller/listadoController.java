package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import model.Nota;

@Controller
public class listadoController {

	public listadoController() {
	}

	@PostMapping("/listado")
	public String listarNotas(HttpSession ss, Model model) {
		
		ArrayList<Nota> notas = Nota.getNotas((String) ss.getAttribute("username"));
		
		if(notas!=null) {
			ss.setAttribute("notas", notas);
		}
		return "listado";
	}

}
