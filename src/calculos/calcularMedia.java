package calculos;

import java.util.List;

//Método para calcular la media entre los datos de una lista
public class calcularMedia {
    public static double calcularMedia(List<Double> lista){
        return lista.stream()
                .mapToDouble(a->a)
                .average().orElse(0);
    }   
}
