package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.example.models.Facultad;
import com.example.services.EstudianteService;
import com.example.services.EstudianteServiceImpl;

@WebServlet("/AltaEstudianteController")
public class AltaEstudianteController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    public AltaEstudianteController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        EstudianteService estudianteService = new EstudianteServiceImpl();
        
        List<Facultad> facultades = estudianteService.getFacultades();
        
        request.setAttribute("facultades", facultades);
        
        request.getRequestDispatcher("views/formularioAltaEstudiante.jsp").forward(request, response);
    }
}