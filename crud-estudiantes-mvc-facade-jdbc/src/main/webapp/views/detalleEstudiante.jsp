<%@page import="com.example.models.EstudianteCompleto"%>
<%@page import="com.example.models.Telefono"%>
<%@page import="com.example.models.Correo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    EstudianteCompleto estudianteDetalle = (EstudianteCompleto) request.getAttribute("estudianteDetalle");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalle de Estudiante</title>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 30px;
        background-color: #f5f7fa;
        color: #333;
    }

    h1 {
        color: #1f3b5b;
        margin-bottom: 25px;
    }

    .contenedor {
        background-color: white;
        padding: 25px;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        max-width: 800px;
    }

    .fila {
        margin-bottom: 14px;
    }

    .etiqueta {
        font-weight: bold;
        color: #2f5d8a;
    }

    .lista-datos {
        margin-top: 6px;
        padding-left: 20px;
    }

    .acciones {
        margin-top: 25px;
    }

    .acciones a {
        margin-right: 15px;
    }
</style>
</head>
<body>

    <h1>Detalle de Estudiante</h1>

    <div class="contenedor">
        <div class="fila">
            <span class="etiqueta">Nombre:</span>
            <span><%= estudianteDetalle.estudiante().nombre() %></span>
        </div>

        <div class="fila">
            <span class="etiqueta">Primer Apellido:</span>
            <span><%= estudianteDetalle.estudiante().primerApellido() %></span>
        </div>

        <div class="fila">
            <span class="etiqueta">Segundo Apellido:</span>
            <span><%= estudianteDetalle.estudiante().segundoApellido() %></span>
        </div>

        <div class="fila">
            <span class="etiqueta">Total de Asignaturas:</span>
            <span><%= estudianteDetalle.estudiante().totalAsignaturas() %></span>
        </div>

        <div class="fila">
            <span class="etiqueta">Beca:</span>
            <span><%= estudianteDetalle.estudiante().beca() != null ? estudianteDetalle.estudiante().beca() : "Sin beca" %></span>
        </div>

        <div class="fila">
		    <span class="etiqueta">Teléfonos:</span>
		    <% if (estudianteDetalle.telefonos() != null && !estudianteDetalle.telefonos().isEmpty()) { %>
		        <ul class="lista-datos">
		            <% for (Telefono telefono : estudianteDetalle.telefonos()) { %>
		                <li><%= telefono.numero() %></li>
		            <% } %>
		        </ul>
		    <% } else { %>
		        <p>Sin teléfonos</p>
		    <% } %>
		</div>
		
       <div class="fila">
		    <span class="etiqueta">Correos:</span>
		    <% if (estudianteDetalle.correos() != null && !estudianteDetalle.correos().isEmpty()) { %>
		        <ul class="lista-datos">
		            <% for (Correo correo : estudianteDetalle.correos()) { %>
		                <li><%= correo.email() %></li>
		            <% } %>
		        </ul>
		    <% } else { %>
		        <p>Sin correos</p>
		    <% } %>
		</div>

        <div class="acciones">
            <a href="<%= request.getContextPath() %>/index.jsp">Volver al listado</a>
            <a href="<%= request.getContextPath() %>/ModificarEstudianteController?id=<%= estudianteDetalle.estudiante().id() %>">Modificar</a>
        </div>
    </div>

</body>
</html>