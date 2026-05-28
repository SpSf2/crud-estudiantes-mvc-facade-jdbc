package com.example.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.dao.DBConexion;
import com.example.models.Estudiante;
import com.example.models.Genero;

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
    public List<Estudiante> getEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<Estudiante>();

        try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
             Connection connection = dbConexion.getConexion()) {

            ResultSet rs = dbConexion.getEstudiantes(connection);

            while (rs.next()) {
                estudiantes.add(new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("primerApellido"),
                        rs.getString("segundoApellido"),
                        Genero.valueOf(rs.getString("genero")),
                        rs.getDate("fechaNacimiento").toLocalDate(),
                        rs.getDouble("beca"),
                        rs.getInt("facultad_id"),
                        rs.getInt("totalAsignaturas")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return estudiantes;
    }
}