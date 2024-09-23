package calculos;

import static calculos.corCoseno.corCoseno;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eduar
 */
public class cosenoListas {
    //Método para calcular el coseno del ángulo de cada par de vectores entre todos los vectores de una lista
    public static List<Double> cosenoListas(List<List<Double>> datos){
        if (datos.size() < 2) {
            throw new IllegalArgumentException("Se requieren al menos dos listas para calcular la correlación de Pearson.");
        }
        
        List<Double> resultados = new ArrayList<>();
        
        for(int i=0;i<datos.size();i++){
            double coseno = 0;
            for(int j=0;j<datos.size();j++){
                if(i != j){//Evita que se haga el calculo de una lista consigo misma
                    coseno = corCoseno(datos.get(i),datos.get(j)); //Coseno del ángulo entre los vectores de una lista
                    resultados.add(coseno); //Se agregan los resultados de cada par de vectores a una lista
                }
            }
        }
        return resultados;
    }
    
}
