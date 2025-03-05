package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import model.Nota;

@Controller
public class editarNotaController {
	
	public editarNotaController() {}
	
	@GetMapping("/editarN")
	public String editarNota(@RequestParam(name="id")int id,@RequestParam(name="titulo") String titulo, @RequestParam(name="nota") String nota, Model model,HttpSession ss) {
		Nota editNota = new Nota(id, (String) ss.getAttribute("username"), titulo, nota, LocalDateTime.now());
		editNota.update();
		ss.setAttribute("notas", Nota.getNotas((String) ss.getAttribute("username")));
		return "/listado";
	}
}
