package com.example.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import com.example.models.Correo;
import com.example.models.Estudiante;
import com.example.models.EstudianteCompleto;
import com.example.models.Facultad;
import com.example.models.Genero;
import com.example.models.Telefono;

public class DBConexion implements AutoCloseable {

	private static final Logger LOG = Logger.getLogger("DBConexion");
	private Connection connection;

	public DBConexion(String user, String password) throws Exception {
		super();

		Class.forName("com.mysql.cj.jdbc.Driver");

		Properties properties = new Properties();
		properties.put("user", user);
		properties.put("password", password);

		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/universidad", properties);

		LOG.info("Conexion realizada con exito");
	}

	public Connection getConexion() {
		return connection;
	}

	@Override
	public void close() throws Exception {
		this.connection.close();
	}
	
	

	public Facultad getFacultadById(Connection connection, int facultadId) {
		Facultad facultad = null;
		String query = "SELECT * FROM facultades WHERE id = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, facultadId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				facultad = new Facultad(rs.getInt("id"), rs.getString("nombre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return facultad;
	}

	public ResultSet getEstudiantesConFacultad(Connection connection) {
		ResultSet rs = null;

		String query = """
				SELECT e.id,
				       e.nombre,
				       e.primerApellido,
				       e.segundoApellido,
				       e.genero,
				       e.fechaNacimiento,
				       e.beca,
				       e.facultad_id,
				       e.totalAsignaturas,
				       f.nombre AS nombre_facultad
				FROM estudiantes e
				INNER JOIN facultades f ON e.facultad_id = f.id
				ORDER BY e.id DESC
				""";

		Statement stmt = null;

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public List<Telefono> getTelefonosByEstudianteId(Connection connection, int estudianteId) {
		List<Telefono> telefonos = new ArrayList<>();

		String query = "SELECT * FROM telefonos WHERE estudiante_id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, estudianteId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				telefonos.add(new Telefono(rs.getInt("id"), rs.getString("numero"), rs.getInt("estudiante_id")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return telefonos;
	}

	public List<Correo> getCorreosByEstudianteId(Connection connection, int estudianteId) {
		List<Correo> correos = new ArrayList<>();

		String query = "SELECT * FROM correos WHERE estudiante_id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, estudianteId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				correos.add(new Correo(rs.getInt("id"), rs.getString("email"), rs.getInt("estudiante_id")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return correos;
	}

	public List<Facultad> getFacultades(Connection connection) {
		List<Facultad> facultades = new ArrayList<>();

		String query = "SELECT * FROM facultades";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				facultades.add(new Facultad(rs.getInt("id"), rs.getString("nombre")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return facultades;
	}

	public void insertEstudiante(Estudiante estudiante, List<String> telefonos, List<String> correos) throws Exception {
		String sql = "INSERT INTO estudiantes (nombre, primerApellido, segundoApellido, "
				+ "genero, fechaNacimiento, beca, facultad_id, totalAsignaturas) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		int estudianteId;

		try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, estudiante.nombre());
			ps.setString(2, estudiante.primerApellido());
			ps.setString(3, estudiante.segundoApellido());
			ps.setString(4, estudiante.genero().name());
			ps.setDate(5, Date.valueOf(estudiante.fechaNacimiento()));

			if (estudiante.beca() != null) {
				ps.setDouble(6, estudiante.beca());
			} else {
				ps.setNull(6, java.sql.Types.DOUBLE);
			}

			ps.setInt(7, estudiante.facultad_id());
			ps.setInt(8, estudiante.totalAsignaturas());

			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					estudianteId = rs.getInt(1);
				} else {
					throw new SQLException("No se pudo obtener el id del estudiante insertado");
				}
			}

			String sqlTelefonos = "INSERT INTO telefonos (numero, estudiante_id) VALUES (?, ?)";

			try (PreparedStatement psTelefono = connection.prepareStatement(sqlTelefonos)) {
				for (String telefono : telefonos) {
					psTelefono.setString(1, telefono);
					psTelefono.setInt(2, estudianteId);
					psTelefono.executeUpdate();
				}
			}

			String sqlCorreos = "INSERT INTO correos (email, estudiante_id) VALUES (?, ?)";

			try (PreparedStatement psCorreo = connection.prepareStatement(sqlCorreos)) {
				for (String correo : correos) {
					psCorreo.setString(1, correo);
					psCorreo.setInt(2, estudianteId);
					psCorreo.executeUpdate();
				}
			}
		}

	}

	public EstudianteCompleto getEstudianteCompletoById(Connection connection, int id) {
		String query = """
				SELECT e.id,
				       e.nombre,
				       e.primerApellido,
				       e.segundoApellido,
				       e.genero,
				       e.fechaNacimiento,
				       e.beca,
				       e.facultad_id,
				       e.totalAsignaturas,
				       f.nombre AS nombre_facultad
				FROM estudiantes e
				INNER JOIN facultades f ON e.facultad_id = f.id
				WHERE e.id = ?
				""";

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
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

					Facultad facultad = new Facultad(
							rs.getInt("facultad_id"),
							rs.getString("nombre_facultad"));

					List<Telefono> telefonos = getTelefonosByEstudianteId(connection, estudiante.id());
					List<Correo> correos = getCorreosByEstudianteId(connection, estudiante.id());

					return new EstudianteCompleto(
							estudiante, 
							facultad, 
							telefonos, 
							correos);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void updateEstudiante(Connection connection, Estudiante estudiante, List<String> telefonos, List<String> correos) throws Exception {
	    String sqlUpdateEstudiante = """
	            UPDATE estudiantes
	            SET nombre = ?,
	                primerApellido = ?,
	                segundoApellido = ?,
	                genero = ?,
	                fechaNacimiento = ?,
	                beca = ?,
	                facultad_id = ?,
	                totalAsignaturas = ?
	            WHERE id = ?
	            """;

	    String sqlDeleteTelefonos = "DELETE FROM telefonos WHERE estudiante_id = ?";
	    String sqlDeleteCorreos = "DELETE FROM correos WHERE estudiante_id = ?";
	    String sqlInsertTelefono = "INSERT INTO telefonos (numero, estudiante_id) VALUES (?, ?)";
	    String sqlInsertCorreo = "INSERT INTO correos (email, estudiante_id) VALUES (?, ?)";

	    try {
	        connection.setAutoCommit(false);

	        try (PreparedStatement psUpdate = connection.prepareStatement(sqlUpdateEstudiante)) {
	            psUpdate.setString(1, estudiante.nombre());
	            psUpdate.setString(2, estudiante.primerApellido());
	            psUpdate.setString(3, estudiante.segundoApellido());
	            psUpdate.setString(4, estudiante.genero().name());
	            psUpdate.setDate(5, Date.valueOf(estudiante.fechaNacimiento()));

	            if (estudiante.beca() != null) {
	                psUpdate.setDouble(6, estudiante.beca());
	            } else {
	                psUpdate.setNull(6, java.sql.Types.DOUBLE);
	            }

	            psUpdate.setInt(7, estudiante.facultad_id());
	            psUpdate.setInt(8, estudiante.totalAsignaturas());
	            psUpdate.setInt(9, estudiante.id());

	            psUpdate.executeUpdate();
	        }

	        try (PreparedStatement psDeleteTelefonos = connection.prepareStatement(sqlDeleteTelefonos)) {
	            psDeleteTelefonos.setInt(1, estudiante.id());
	            psDeleteTelefonos.executeUpdate();
	        }

	        try (PreparedStatement psDeleteCorreos = connection.prepareStatement(sqlDeleteCorreos)) {
	            psDeleteCorreos.setInt(1, estudiante.id());
	            psDeleteCorreos.executeUpdate();
	        }

	        try (PreparedStatement psInsertTelefono = connection.prepareStatement(sqlInsertTelefono)) {
	            for (String telefono : telefonos) {
	                psInsertTelefono.setString(1, telefono);
	                psInsertTelefono.setInt(2, estudiante.id());
	                psInsertTelefono.executeUpdate();
	            }
	        }

	        try (PreparedStatement psInsertCorreo = connection.prepareStatement(sqlInsertCorreo)) {
	            for (String correo : correos) {
	                psInsertCorreo.setString(1, correo);
	                psInsertCorreo.setInt(2, estudiante.id());
	                psInsertCorreo.executeUpdate();
	            }
	        }

	        connection.commit();

	    } catch (Exception e) {
	        connection.rollback();
	        throw e;
	    } finally {
	        connection.setAutoCommit(true);
	    }
	}
		
}
	
	
	

