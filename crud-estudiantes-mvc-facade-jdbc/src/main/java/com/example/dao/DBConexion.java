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
    
    public List<Facultad> getFacultades(Connection connection) {
        List<Facultad> facultades = new ArrayList<>();

        String query = "SELECT * FROM facultades";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                facultades.add(new Facultad(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ));
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
}
