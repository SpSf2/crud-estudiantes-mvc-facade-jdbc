package com.example.controllers;

import java.io.IOException;
import java.util.List;

import com.example.models.EstudianteCompleto;
import com.example.models.Facultad;
import com.example.services.EstudianteService;
import com.example.services.EstudianteServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ModificarEstudianteController
 */
@WebServlet("/ModificarEstudianteController")
public class ModificarEstudianteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModificarEstudianteController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

		EstudianteService estudianteService = new EstudianteServiceImpl();

		EstudianteCompleto estudianteEditar = estudianteService.getEstudianteCompletoById(id);
		List<Facultad> facultades = estudianteService.getFacultades();

		request.setAttribute("estudianteEditar", estudianteEditar);
		request.setAttribute("facultades", facultades);

		request.getRequestDispatcher("/views/formularioAltaEstudiante.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
