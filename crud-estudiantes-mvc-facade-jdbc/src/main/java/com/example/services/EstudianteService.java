package com.example.services;

import java.sql.SQLException;
import java.util.List;
import com.example.models.EstudianteDetalle;
import com.example.models.EstudianteCompleto;

public interface EstudianteService {

    boolean isConnectionOK() throws SQLException, Exception;

    List<EstudianteDetalle> getEstudiantes();
    
    List<EstudianteCompleto> getEstudiantesCompletos();
}