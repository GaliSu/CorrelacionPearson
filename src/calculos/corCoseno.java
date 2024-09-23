/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculos;
import java.util.List;

public class corCoseno {
    //Método para calcular el coseno del ángulo entre dos vectores (listas)
    public static double corCoseno(List<Double> listaU, List<Double> listaV){
        int longListas = listaU.size();

        //Se comprueba que las listas tienen la misma longitud
        if (longListas != listaV.size()) {
                throw new IllegalArgumentException("Ambas listas deben tener la misma longitud para calcular la correlación de Pearson.");
        }        
    
        double productoPunto = 0;
        //Sumatoria de los cuadrados de los elementos del vector para cada uno de ellos 
        double sumaU = 0;
        double sumaV = 0;
        
        //Suma del cuadrado de todos los elementos del vector U
        for(int i=0;i<listaU.size();i++){
            sumaU += Math.pow(listaU.get(i),2);
        }
        
        //Suma del cuadrado de todos los elementos del vector V
        for(int i=0;i<listaV.size();i++){
            sumaV += Math.pow(listaV.get(i),2);
        }
        
        //Se calcula el producto punto entre los dos vectores
        for(int i=0;i<listaU.size();i++){        
           productoPunto += listaU.get(i) * listaV.get(i);
        }
        
        //Se calcula el coseno del ángulo entre los dos vectores
        Double coseno = 1 -((( productoPunto / (Math.sqrt(sumaU) * Math.sqrt(sumaV)))+ 1)/ 2);
        return coseno;
        
    }
}
