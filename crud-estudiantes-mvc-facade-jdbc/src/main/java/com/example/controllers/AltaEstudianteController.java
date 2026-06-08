package com.example.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Estudiante;
import com.example.models.Facultad;
import com.example.models.Genero;
import com.example.services.EstudianteService;
import com.example.services.EstudianteServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AltaEstudianteController")
public class AltaEstudianteController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AltaEstudianteController() {
		super();
	}

	private EstudianteService estudianteService = new EstudianteServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		EstudianteService estudianteService = new EstudianteServiceImpl();

		List<Facultad> facultades = estudianteService.getFacultades();

		request.setAttribute("facultades", facultades);

		request.getRequestDispatcher("/views/formularioAltaEstudiante.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id");
		String nombre = request.getParameter("nombre");
		String primerApellido = request.getParameter("primerApellido");
		String segundoApellido = request.getParameter("segundoApellido");
		Genero genero = Genero.valueOf(request.getParameter("genero"));
		LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("fechaNacimiento"));

		String becaTexto = request.getParameter("beca");
		Double beca = null;
		if (becaTexto != null && !becaTexto.trim().isEmpty()) {
			beca = Double.parseDouble(becaTexto);
		}

		int totalAsignaturas = Integer.parseInt(request.getParameter("totalAsignaturas"));
		int idFacultad = Integer.parseInt(request.getParameter("facultad"));

		String[] telefonosArray = request.getParameterValues("telefonos");
		List<String> telefonos = new ArrayList<>();
		if (telefonosArray != null) {
			for (String telefono : telefonosArray) {
				if (telefono != null && !telefono.trim().isEmpty()) {
					telefonos.add(telefono.trim());
				}
			}
		}

		String[] correosArray = request.getParameterValues("correos");
		List<String> correos = new ArrayList<>();
		if (correosArray != null) {
			for (String correo : correosArray) {
				if (correo != null && !correo.trim().isEmpty()) {
					correos.add(correo.trim());
				}
			}
		}

		boolean modoEdicion = idParam != null && !idParam.isBlank();

		Estudiante estudiante;

		if (modoEdicion) {
		    int id = Integer.parseInt(idParam);

		    estudiante = new Estudiante(
		        id,
		        nombre,
		        primerApellido,
		        segundoApellido,
		        genero,
		        fechaNacimiento,
		        beca,
		        idFacultad,
		        totalAsignaturas
		    );
		} else {
		    estudiante = new Estudiante(
		        0,
		        nombre,
		        primerApellido,
		        segundoApellido,
		        genero,
		        fechaNacimiento,
		        beca,
		        idFacultad,
		        totalAsignaturas
		    );
		}
		
		try {
			if (modoEdicion) {
			    estudianteService.updateEstudiante(estudiante, telefonos, correos);
			} else {
			    estudianteService.insertEstudiante(estudiante, telefonos, correos);
			}
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

}
