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
<title>Listado de Estudiantes</title>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 30px;
        background-color: #f5f7fa;
        color: #333;
    }

    h1 {
        text-align: center;
        margin-bottom: 25px;
        color: #1f3b5b;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        background-color: white;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    }

    th, td {
        padding: 12px 15px;
        text-align: left;
        border: 1px solid #dcdcdc;
        vertical-align: top;
    }

    th {
        background-color: #2f5d8a;
        color: white;
    }

    tbody tr:nth-child(even) {
        background-color: #f2f6fa;
    }

    tbody tr:hover {
        background-color: #eaf2fb;
    }

    .lista-datos {
        margin: 0;
        padding-left: 18px;
    }

    .lista-datos li {
        margin-bottom: 4px;
    }
</style>
</head>
<body>
    <h1>Listado de Estudiantes</h1>

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
            <% for (EstudianteCompleto item: estudiantes) { %>
                <tr>
                    <td><%= item.estudiante().nombre() %></td>
                    <td><%= item.estudiante().primerApellido() %></td>
                    <td><%= item.estudiante().segundoApellido() %></td>
                    <td><%= item.facultad().nombre() %></td>
                    <td>
					    <ul class="lista-datos">
					        <% for (Telefono telefono : item.telefonos()) { %>
					            <li><%= telefono.numero() %></li>
					        <% } %>
					    </ul>
					</td>
                    <td>
					    <ul class="lista-datos">
					        <% for (Correo correo : item.correos()) { %>
					            <li><%= correo.email() %></li>
					        <% } %>
					    </ul>
					</td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>