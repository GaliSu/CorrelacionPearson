package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection.ConexionBaseDatos;
import java.sql.Timestamp;

public class testDAO {

    // Constructor que acepta una conexión a la base de datos
    private Connection conexion;

    public testDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para buscar un testObject por su idtest
    public testObject buscarTest(int id) {
        // Consulta SQL
        String sql = "SELECT name FROM matrizinfo WHERE idmatrizinfo = ?";

        try (Connection conn = ConexionBaseDatos.conectar(); // Conexión a la base de datos
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establece el valor del parámetro de la consulta
            statement.setInt(1, id); // El valor es un entero, id del test

            // Ejecuta la consulta
            ResultSet resultSet = statement.executeQuery();

            // Si se encuentra un resultado, crea y devuelve el testObject
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Timestamp fecha = resultSet.getTimestamp("fecha");
                String observaciones = resultSet.getString("observaciones");

                
                ConexionBaseDatos.cerrarConexion(conexion);

                // Crea el objeto testObject con el id y el nombre obtenido de la base de datos
                return new testObject(id, name, fecha, observaciones);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el testObject: " + e.getMessage());
            e.printStackTrace();
            ConexionBaseDatos.cerrarConexion(conexion);

        }
        return null;  // Si no se encuentra, devuelve null

    }
    
    
    public int insertarInfoMatriz(String name, String observaciones) {
        // Consulta SQL
        String sql = "INSERT INTO matrizinfo (name, observaciones) VALUES (?, ?)";
        

        int estate = 0;
        System.out.println("llegue a 1");
        try (Connection conn = ConexionBaseDatos.conectar(); // Conexión a la base de datos
             
            
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            // Establece el valor del parámetro de la consulta
            statement.setString(1, name); // El valor es un entero, id del test
            statement.setString(2, observaciones);

            
              // Ejecutamos la consulta
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Los datos fueron insertados exitosamente.");
                estate = 1;
                return estate;
            }

    
        } catch (SQLException e) {
            System.out.println("Error al insertar los datos: " + e.getMessage());
            return estate;
        }

        return estate;
          // Si no se encuentra, devuelve null

    }
    
    
}
