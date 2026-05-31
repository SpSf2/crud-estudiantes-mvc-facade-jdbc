package com.example.models;

import java.util.List;

public record EstudianteCompleto(
        Estudiante estudiante,
        Facultad facultad,
        List<Telefono> telefonos,
        List<Correo> correos
) {
}