<%@page import="com.example.models.EstudianteDetalle"%>
<%@page import="java.util.List"%>
<%@page import="com.example.models.EstudianteDetalle"%>
<%@page import="com.example.services.EstudianteService"%>
<%@page import="com.example.services.EstudianteServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    EstudianteService estudianteService = new EstudianteServiceImpl();
    List<EstudianteDetalle> estudiantes = estudianteService.getEstudiantes();
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
                <th>Facultad</th>
                <th>Total Asignaturas</th>
            </tr>
        </thead>
        <tbody>
            <% for (EstudianteDetalle detalle : estudiantes) { %>
                <tr>
                    <td><%= detalle.estudiante().nombre() %></td>
					<td><%= detalle.estudiante().primerApellido() %></td>
					<td><%= detalle.estudiante().segundoApellido() %></td>
					<td><%= detalle.estudiante().genero() %></td>
					<td><%= detalle.estudiante().fechaNacimiento() %></td>
					<td><%= detalle.estudiante().beca() %></td>
					<td><%= detalle.facultad().nombre() %></td>
					<td><%= detalle.estudiante().totalAsignaturas() %></td>
                </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>