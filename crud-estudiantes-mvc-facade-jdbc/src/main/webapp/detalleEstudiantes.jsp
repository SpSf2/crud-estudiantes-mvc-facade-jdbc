<%@page import="java.util.List"%>
<%@page import="com.example.models.EstudianteCompleto"%>
<%@page import="com.example.models.Telefono"%>
<%@page import="com.example.models.Correo"%>
<%@page import="com.example.services.EstudianteService"%>
<%@page import="com.example.services.EstudianteServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    EstudianteService estudianteService = new EstudianteServiceImpl();
    List<EstudianteCompleto> estudiantes = estudianteService.getEstudiantesCompletos();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalle de estudiantes</title>
</head>
<body>
    <h1>Detalle de Estudiantes</h1>

    <table border="1">
        <thead>
            <tr>
                <th>Nombre</th>
                <th>Primer Apellido</th>
                <th>Segundo Apellido</th>
                <th>Facultad</th>
                <th>Telefonos</th>
                <th>Correos</th>
            </tr>
        </thead>
        <tbody>
            <% for (EstudianteCompleto detalle : estudiantes) { %>
                <tr>
                    <td><%= detalle.estudiante().nombre() %></td>
                    <td><%= detalle.estudiante().primerApellido() %></td>
                    <td><%= detalle.estudiante().segundoApellido() %></td>
                    <td><%= detalle.facultad().nombre() %></td>
                    <td>
                        <% for (Telefono telefono : detalle.telefonos()) { %>
                            <%= telefono.numero() %><br>
                        <% } %>
                    </td>
                    <td>
                        <% for (Correo correo : detalle.correos()) { %>
                            <%= correo.email() %><br>
                        <% } %>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>