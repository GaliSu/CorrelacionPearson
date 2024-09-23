package calculos;

import java.util.ArrayList;
import java.util.List;
import static calculos.pearson.pearsonPar;

public class calcPearson{
    //Cálculo de correlación de Pearson dada una lista de listas
    public static List<Double> calcPearson(List<List<Double>> datos){
        int numListas = datos.size();

        // Verificar que hay al menos dos listas para comparar
        if (numListas < 2) {
            throw new IllegalArgumentException("Se requieren al menos dos listas para calcular la correlación de Pearson.");
        }

        List<Double> resultados = new ArrayList<>(); //Se crea la lista que contendrá las correlaciones

        // Calcular la correlación con cada otra lista
        for (int i = 0; i < numListas; i++) {
            double correlacion = 0.0;
            for (int j = 0; j < numListas; j++) {
                if (i != j) { //Condición para que no se compare una lista consigo misma
                    correlacion = pearsonPar(datos.get(i), datos.get(j));
                    resultados.add(correlacion);
                }
            }
        }

        return resultados;
    }
}