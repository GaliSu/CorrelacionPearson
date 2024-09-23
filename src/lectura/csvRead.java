package lectura;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class csvRead { //Metodo de lectura de archivos CSV    
    public static List<List<Double>> leerCSV(String filePath) {
        //Se crea una lista de listas donde se almacenará el conjunto de datos
        List<List<Double>> datos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) { //File path=ubicacion del archivo
            String linea;
                        
            //Por cada línea se crea una lista que almacenará los datos contenidos en ella
            while ((linea = br.readLine()) != null) { //Se leera siempre y cuando exista
                
                String[] valores = linea.split(","); //Separador
                
                //lineaDatos=guarda las lineas leidas
                List<Double> lineaDatos = new ArrayList<>();
                for (String valor : valores) {
                    lineaDatos.add(Double.parseDouble(valor.trim())); //Se convierten los datos contenidos en la línea a datos de tipo Double y se agregan a una lista
                }
                
                datos.add(lineaDatos); //Se agrega cada lista de datos a otra lista
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datos;
    }
    }
    
