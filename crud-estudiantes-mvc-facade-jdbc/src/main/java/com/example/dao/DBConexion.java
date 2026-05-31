package com.example.dao;

import java.sql.Connection;
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
import com.example.models.Facultad;
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

        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/universidad",
                properties
        );

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
                facultad = new Facultad(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
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
                telefonos.add(new Telefono(
                        rs.getInt("id"),
                        rs.getString("numero"),
                        rs.getInt("estudiante_id")
                ));
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
                correos.add(new Correo(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getInt("estudiante_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return correos;
    }
}
