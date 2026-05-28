package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

public class DBConexion implements AutoCloseable {

    private static final Logger LOG = Logger.getLogger("DBConexion");
    private Connection connection;

    public DBConexion(String user, String password) throws Exception {
        super();

        Properties properties = new Properties();
        properties.put("user", user);
        properties.put("password", password);

        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/universidad",
                properties
        );

        LOG.info("Conexion realizada con exito");
    }

    public Connection getConexion() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }

    public ResultSet getEstudiantes(Connection connection) {
        ResultSet rs = null;
        String query = "SELECT * FROM estudiantes";
        Statement stmt = null;

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }
}
