<%@page import="com.example.models.Facultad"%>
<%@page import="java.util.List"%>
<%@page import="com.example.models.EstudianteCompleto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    EstudianteCompleto estudianteEditar = (EstudianteCompleto) request.getAttribute("estudianteEditar");
    boolean modoEdicion = estudianteEditar != null;
%>

<html>
<head>
<meta charset="UTF-8">
<title>Formulario Alta Estudiante</title>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 30px;
    }
    .campo {
        margin-bottom: 12px;
    }
    .bloque-lista {
        margin-top: 8px;
        margin-bottom: 8px;
    }
    .fila-dinamica {
        margin-bottom: 6px;
    }
</style>
</head>
<body>

<%
    List<Facultad> facultades = (List<Facultad>) request.getAttribute("facultades");
%>

<h1>Formulario de Alta de Estudiante</h1>

<form action="AltaEstudianteController" method="post">

	<% if (modoEdicion) { %>
    <input type="hidden" name="id" value="<%= estudianteEditar.estudiante().id() %>">
<% } %>

    <div class="campo">
    <label for="nombre">Nombre:</label>
    <input type="text" id="nombre" name="nombre" required
           value="<%= modoEdicion ? estudianteEditar.estudiante().nombre() : "" %>">
	</div>
	
	<div class="campo">
	    <label for="primerApellido">Primer Apellido:</label>
	    <input type="text" id="primerApellido" name="primerApellido" required
	           value="<%= modoEdicion ? estudianteEditar.estudiante().primerApellido() : "" %>">
	</div>
	
	<div class="campo">
	    <label for="segundoApellido">Segundo Apellido:</label>
	    <input type="text" id="segundoApellido" name="segundoApellido"
	           value="<%= modoEdicion && estudianteEditar.estudiante().segundoApellido() != null ? estudianteEditar.estudiante().segundoApellido() : "" %>">
	</div>

    <div class="campo">
    <label for="genero">Género:</label>
    <select id="genero" name="genero" required>
        <option value="">Seleccione</option>
        <option value="HOMBRE" <%= modoEdicion && "HOMBRE".equals(estudianteEditar.estudiante().genero().name()) ? "selected" : "" %>>Hombre</option>
        <option value="MUJER" <%= modoEdicion && "MUJER".equals(estudianteEditar.estudiante().genero().name()) ? "selected" : "" %>>Mujer</option>
        <option value="OTRO" <%= modoEdicion && "OTRO".equals(estudianteEditar.estudiante().genero().name()) ? "selected" : "" %>>Otro</option>
    </select>
	</div>
	
	<div class="campo">
	    <label for="fechaNacimiento">Fecha de Nacimiento:</label>
	    <input type="date" id="fechaNacimiento" name="fechaNacimiento" required
	           value="<%= modoEdicion ? estudianteEditar.estudiante().fechaNacimiento() : "" %>">
	</div>
	
	<div class="campo">
	    <label for="beca">Beca:</label>
	    <input type="text" id="beca" name="beca"
	           value="<%= modoEdicion ? estudianteEditar.estudiante().beca() : "" %>">
	</div>
	
	<div class="campo">
	    <label for="totalAsignaturas">Total Asignaturas:</label>
	    <input type="number" id="totalAsignaturas" name="totalAsignaturas" required
	           value="<%= modoEdicion ? estudianteEditar.estudiante().totalAsignaturas() : "" %>">
	</div>

   <div class="campo">
    <label for="facultad">Facultad:</label>
    <select id="facultad" name="facultad" required>
        <option value="">Seleccione una facultad</option>
        <% for (Facultad facultad : facultades) { %>
            <option value="<%= facultad.id() %>"
                <%= modoEdicion && facultad.id() == estudianteEditar.estudiante().facultad_id() ? "selected" : "" %>>
                <%= facultad.nombre() %>
            </option>
        <% } %>
    </select>
	</div>

    <div class="campo">
    <label>Teléfonos:</label>
    <div id="telefonosContainer" class="bloque-lista">
        <% if (modoEdicion && estudianteEditar.telefonos() != null && !estudianteEditar.telefonos().isEmpty()) { %>
            <% for (com.example.models.Telefono telefono : estudianteEditar.telefonos()) { %>
                <div class="fila-dinamica">
                    <input type="text" name="telefonos" placeholder="Ingrese un teléfono"
                           value="<%= telefono.numero() %>">
                </div>
            <% } %>
        <% } else { %>
            <div class="fila-dinamica">
                <input type="text" name="telefonos" placeholder="Ingrese un teléfono">
            </div>
        <% } %>
    </div>
    <button type="button" id="btnAgregarTelefono">Añadir teléfono</button>
	</div>

	<div class="campo">
    <label>Correos:</label>
    <div id="correosContainer" class="bloque-lista">
        <% if (modoEdicion && estudianteEditar.correos() != null && !estudianteEditar.correos().isEmpty()) { %>
            <% for (com.example.models.Correo correo : estudianteEditar.correos()) { %>
                <div class="fila-dinamica">
                    <input type="email" name="correos" placeholder="Ingrese un correo"
                           value="<%= correo.email() %>">
                </div>
            <% } %>
        <% } else { %>
            <div class="fila-dinamica">
                <input type="email" name="correos" placeholder="Ingrese un correo">
            </div>
        <% } %>
    </div>
        <button type="button" id="btnAgregarCorreo">Añadir correo</button>
    </div>

    <br>
    <input type="submit" value="Enviar">
</form>

<script>
    const telefonosContainer = document.getElementById('telefonosContainer');
    const correosContainer = document.getElementById('correosContainer');

    document.getElementById('btnAgregarTelefono').addEventListener('click', () => {
        const div = document.createElement('div');
        div.className = 'fila-dinamica';
        div.innerHTML = '<input type="text" name="telefonos" placeholder="Ingrese un teléfono"> <button type="button" class="btnEliminar">Eliminar</button>';
        telefonosContainer.appendChild(div);
    });

    document.getElementById('btnAgregarCorreo').addEventListener('click', () => {
        const div = document.createElement('div');
        div.className = 'fila-dinamica';
        div.innerHTML = '<input type="email" name="correos" placeholder="Ingrese un correo"> <button type="button" class="btnEliminar">Eliminar</button>';
        correosContainer.appendChild(div);
    });

    document.addEventListener('click', (e) => {
        if (e.target.classList.contains('btnEliminar')) {
            e.target.parentElement.remove();
        }
    });
</script>

</body>
</html>