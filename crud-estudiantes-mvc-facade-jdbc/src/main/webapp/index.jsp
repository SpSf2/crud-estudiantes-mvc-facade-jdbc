<%@page import="java.util.List"%>
<%@page import="com.example.models.Estudiante"%>
<%@page import="com.example.services.EstudianteService"%>
<%@page import="com.example.services.EstudianteServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    EstudianteService estudianteService = new EstudianteServiceImpl();
    List<Estudiante> estudiantes = estudianteService.getEstudiantes();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listado de estudiantes</title>
</head>
<body>

    <h1>Listado de Estudiantes</h1>

    <table border="1">
        <thead>
            <tr>
                <th>Nombre</th>
                <th>Primer Apellido</th>
                <th>Segundo Apellido</th>
                <th>Genero</th>
                <th>Fecha Nacimiento</th>
                <th>Beca</th>
                <th>Facultad ID</th>
                <th>Total Asignaturas</th>
            </tr>
        </thead>
        <tbody>
            <% for (Estudiante estudiante : estudiantes) { %>
                <tr>
                    <td><%= estudiante.nombre() %></td>
                    <td><%= estudiante.primerApellido() %></td>
                    <td><%= estudiante.segundoApellido() %></td>
                    <td><%= estudiante.genero() %></td>
                    <td><%= estudiante.fechaNacimiento() %></td>
                    <td><%= estudiante.beca() %></td>
                    <td><%= estudiante.facultad_id() %></td>
                    <td><%= estudiante.totalAsignaturas() %></td>
                </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>