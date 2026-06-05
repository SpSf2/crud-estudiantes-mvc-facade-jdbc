package com.example.services;

import java.sql.SQLException;
import java.util.List;

import com.example.models.Estudiante;
import com.example.models.EstudianteCompleto;
import com.example.models.Facultad;

public interface EstudianteService {

    boolean isConnectionOK() throws SQLException, Exception;
    
    List<EstudianteCompleto> getEstudiantesCompletos();
    
    List<Facultad> getFacultades();
    
    void insertEstudiante(Estudiante estudiante, List<String> telefonos, List<String> correos) throws Exception;
}