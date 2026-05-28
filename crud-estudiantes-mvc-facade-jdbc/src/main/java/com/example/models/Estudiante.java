package com.example.models;

import java.time.LocalDate;

public record Estudiante(
        int id,
        String nombre,
        String primerApellido,
        String segundoApellido,
        Genero genero,
        LocalDate fechaNacimiento,
        Double beca,
        int facultad_id,
        int totalAsignaturas
              
) {
}