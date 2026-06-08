package com.example.controllers;

import java.io.IOException;

import com.example.models.EstudianteCompleto;
import com.example.services.EstudianteService;
import com.example.services.EstudianteServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DetalleEstudianteController")
public class DetalleEstudianteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DetalleEstudianteController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        EstudianteService estudianteService = new EstudianteServiceImpl();
        EstudianteCompleto estudianteDetalle = estudianteService.getEstudianteCompletoById(id);

        request.setAttribute("estudianteDetalle", estudianteDetalle);

        request.getRequestDispatcher("/views/detalleEstudiante.jsp").forward(request, response);
    }
}