<%@page import="java.util.List"%>
<%@page import="com.example.models.EstudianteCompleto"%>
<%@page import="com.example.services.EstudianteService"%>
<%@page import="com.example.services.EstudianteServiceImpl"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
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
    <div>
    <a href="AltaEstudianteController" title="Muestra el formulario de alta de estudiante">
        Alta de Estudiante
    </a>
</div>

<br>

    <table border="1">
        <thead>
            <tr>
			    <th>Nombre</th>
			    <th>Primer Apellido</th>
			    <th>Segundo Apellido</th>
			    <th>Fecha de Nacimiento</th>
			    <th>Género</th>
			    <th>Facultad</th>
			    <th>Acciones</th>
			</tr>
        </thead>
        <tbody>
            <% for (EstudianteCompleto item: estudiantes) { %>
			    <tr>
			        <td><%= item.estudiante().nombre() %></td>
			        <td><%= item.estudiante().primerApellido() %></td>
			        <td><%= item.estudiante().segundoApellido() %></td>
			        <td><%= item.estudiante().fechaNacimiento() %></td>
			        <td><%= item.estudiante().genero() %></td>
			        <td><%= item.facultad().nombre() %></td>
			        <td>
			            <a href="DetalleEstudianteController?id=<%= item.estudiante().id() %>">
			                Detalles
			            </a>
			            |
			            <a href="ModificarEstudianteController?id=<%= item.estudiante().id() %>">
			                Modificación
			            </a>
			        </td>
			    </tr>
			<% } %>
        </tbody>
    </table>
</body>
</html>