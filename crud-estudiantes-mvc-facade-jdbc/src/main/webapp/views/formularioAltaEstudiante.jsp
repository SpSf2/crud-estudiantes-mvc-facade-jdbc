<%@page import="com.example.models.Facultad"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
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

    <div class="campo">
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required>
    </div>

    <div class="campo">
        <label for="primerApellido">Primer Apellido:</label>
        <input type="text" id="primerApellido" name="primerApellido" required>
    </div>

    <div class="campo">
        <label for="segundoApellido">Segundo Apellido:</label>
        <input type="text" id="segundoApellido" name="segundoApellido">
    </div>

    <div class="campo">
        <label for="genero">Género:</label>
        <select id="genero" name="genero" required>
            <option value="">Seleccione</option>
            <option value="HOMBRE">Hombre</option>
            <option value="MUJER">Mujer</option>
            <option value="OTRO">Otro</option>
        </select>
    </div>

    <div class="campo">
        <label for="fechaNacimiento">Fecha de Nacimiento:</label>
        <input type="date" id="fechaNacimiento" name="fechaNacimiento" required>
    </div>

    <div class="campo">
        <label for="beca">Beca:</label>
        <input type="text" id="beca" name="beca">
    </div>

    <div class="campo">
        <label for="totalAsignaturas">Total Asignaturas:</label>
        <input type="number" id="totalAsignaturas" name="totalAsignaturas" required>
    </div>

    <div class="campo">
        <label for="facultad">Facultad:</label>
        <select id="facultad" name="facultad" required>
            <option value="">Seleccione una facultad</option>
            <% for (Facultad facultad : facultades) { %>
                <option value="<%= facultad.id() %>"><%= facultad.nombre() %></option>
            <% } %>
        </select>
    </div>

    <div class="campo">
        <label>Teléfonos:</label>
        <div id="telefonosContainer" class="bloque-lista">
            <div class="fila-dinamica">
                <input type="text" name="telefonos" placeholder="Ingrese un teléfono">
            </div>
        </div>
        <button type="button" id="btnAgregarTelefono">Añadir teléfono</button>
    </div>

    <div class="campo">
        <label>Correos:</label>
        <div id="correosContainer" class="bloque-lista">
            <div class="fila-dinamica">
                <input type="email" name="correos" placeholder="Ingrese un correo">
            </div>
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