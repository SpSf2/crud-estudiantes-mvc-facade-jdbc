package com.example.services;

import java.util.List;
import com.example.models.Estudiante;

public interface EstudianteService {

    boolean isConnectionOK() throws Exception;

    List<Estudiante> getEstudiantes();
}