/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ConexionBaseDatos {

    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/matriz";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Método para establecer la conexión
    public static Connection conectar() {
        Connection conexion = null;

        try {
            // Registrar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa a la base de datos.");

        } catch (ClassNotFoundException e) {
            System.out.println("Error: no se encontro el controlador JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
            
        }

        return conexion;
    }

    // Método para cerrar la conexión
    public static void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexion cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexion.");
            e.printStackTrace();
        }
    }

    // Método de ejemplo para ejecutar una consulta
    public static void ejecutarConsulta() {
        Connection conexion = conectar();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Crear un Statement
            stmt = conexion.createStatement();

            // Ejecutar la consulta
            String sql = "SELECT * FROM matrizinfo";
            rs = stmt.executeQuery(sql);

            // Procesar los resultados
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("idmatrizInfo"));
                System.out.println("Nombre: " + rs.getString("name"));
                // Otros campos...
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta.");
            e.printStackTrace();
        } finally {
            // Cerrar ResultSet, Statement y Connection
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                cerrarConexion(conexion);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ejecutarConsulta();  // Llamada al método de ejemplo
    }
}
