package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import model.Nota;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import jakarta.servlet.http.HttpSession;

@Controller
public class FiltrarNotaController {

    @GetMapping("/filtrar")
    public String listarNotas(
            HttpSession ss,
            @RequestParam(name = "filtro", required = false, defaultValue = "todos") String filtro, 
            Model model) {

        // Verificar si hay un usuario en sesión
        String username = (String) ss.getAttribute("username");
        if (username == null || username.isEmpty()) {
            return "redirect:/login"; // Redirigir a login si no hay sesión iniciada
        }

        // Calcular la fecha de inicio según el filtro seleccionado
        LocalDateTime fechaFiltro = null;
        switch (filtro) {
            case "ultima_hora":
                fechaFiltro = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
                break;
            case "ultimo_dia":
                fechaFiltro = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
                break;
            case "ultima_semana":
                fechaFiltro = LocalDateTime.now().minus(1, ChronoUnit.WEEKS);
                break;
            case "ultimo_mes":
                fechaFiltro = LocalDateTime.now().minus(1, ChronoUnit.MONTHS);
                break;
            case "todos":
                fechaFiltro = null; // Para obtener todas las notas
                break;
            default:
                fechaFiltro = null;
                break;
        }

        // Obtener las notas filtradas
        ArrayList<Nota> notas = (fechaFiltro == null) ? Nota.getNotas(username) : Nota.getNotasDesde(username, fechaFiltro);
        if (notas == null) {
            notas = new ArrayList<>(); // Evitar NullPointerException
        }

        // Agregar las notas al modelo y a la sesión
        ss.setAttribute("notas", notas);
        model.addAttribute("notas", notas);
        System.out.println("Notas almacenadas en sesión: " + ss.getAttribute("notas"));

        return "listado"; // Retornar la vista "listado.html"
    }
}
