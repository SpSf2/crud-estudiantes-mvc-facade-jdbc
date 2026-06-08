package com.example.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.dao.DBConexion;
import com.example.models.Correo;
import com.example.models.Estudiante;
import com.example.models.EstudianteCompleto;
import com.example.models.Facultad;
import com.example.models.Genero;
import com.example.models.Telefono;

public class EstudianteServiceImpl implements EstudianteService {

	@Override
	public boolean isConnectionOK() throws Exception {
		boolean connectionOK = false;

		try (DBConexion dbConexion = new DBConexion("root", "Temp2026")) {
			if (dbConexion.getConexion() != null) {
				connectionOK = true;
			}
		}

		return connectionOK;
	}

	@Override
	public List<Facultad> getFacultades() {
		List<Facultad> facultades = new ArrayList<>();

		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConexion()) {

			facultades = dbConexion.getFacultades(connection);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return facultades;
	}

	@Override
	public List<EstudianteCompleto> getEstudiantesCompletos() {
		List<EstudianteCompleto> estudiantesCompletos = new ArrayList<>();

		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConexion()) {

			ResultSet rs = dbConexion.getEstudiantesConFacultad(connection);

			while (rs.next()) {
				Estudiante estudiante = new Estudiante(
						rs.getInt("id"), 
						rs.getString("nombre"),
						rs.getString("primerApellido"), 
						rs.getString("segundoApellido"),
						Genero.valueOf(rs.getString("genero")),
						rs.getDate("fechaNacimiento").toLocalDate(),
						rs.getDouble("beca"),
						rs.getInt("facultad_id"),
						rs.getInt("totalAsignaturas"));

				Facultad facultad = new Facultad
						(rs.getInt("facultad_id"),
								rs.getString("nombre_facultad"));

				List<Telefono> telefonos = dbConexion.getTelefonosByEstudianteId(
						connection, estudiante.id());
				List<Correo> correos = dbConexion.getCorreosByEstudianteId(
						connection, estudiante.id());

				estudiantesCompletos.add(new EstudianteCompleto(
						estudiante, 
						facultad, 
						telefonos, 
						correos));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return estudiantesCompletos;
	}

	@Override
	public void insertEstudiante(Estudiante estudiante, 
			List<String> telefonos, 
			List<String> correos) throws Exception {
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026")) {
			dbConexion.insertEstudiante(estudiante, telefonos, correos);
		}
	}

	@Override
	public EstudianteCompleto getEstudianteCompletoById(int id) {
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConexion()) {

			return dbConexion.getEstudianteCompletoById(connection, id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public void updateEstudiante(Estudiante estudiante, 
			List<String> telefonos, 
			List<String> correos) throws Exception {
		
	    try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
	         Connection connection = dbConexion.getConexion()) {

	        dbConexion.updateEstudiante(connection, estudiante, telefonos, correos);

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	}
		
}

	
	





