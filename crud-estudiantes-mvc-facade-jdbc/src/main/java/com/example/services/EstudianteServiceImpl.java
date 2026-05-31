package com.example.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.example.dao.DBConexion;
import com.example.models.Estudiante;
import com.example.models.Genero;
import com.example.models.EstudianteDetalle;
import com.example.models.Facultad;
import com.example.models.Correo;
import com.example.models.EstudianteCompleto;
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
    public List<EstudianteDetalle> getEstudiantes() {
        List<EstudianteDetalle> estudiantes = new ArrayList<>();

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
                        rs.getInt("totalAsignaturas")
                );

                Facultad facultad = new Facultad(
                        rs.getInt("facultad_id"),
                        rs.getString("nombre_facultad")
                );

                estudiantes.add(new EstudianteDetalle(estudiante, facultad));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return estudiantes;
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
                        rs.getInt("totalAsignaturas")
                );

                Facultad facultad = new Facultad(
                        rs.getInt("facultad_id"),
                        rs.getString("nombre_facultad")
                );

                List<Telefono> telefonos = dbConexion.getTelefonosByEstudianteId(connection, estudiante.id());
                List<Correo> correos = dbConexion.getCorreosByEstudianteId(connection, estudiante.id());

                estudiantesCompletos.add(
                        new EstudianteCompleto(estudiante, facultad, telefonos, correos)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return estudiantesCompletos;
    }
}
