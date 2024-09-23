package calculos;
import static calculos.calcularMedia.calcularMedia;
import java.util.List;


public class pearson{
    
    //Método que plasma el cálculo de la correlación de pearson dadas dos listas
    public static double pearsonPar(List<Double> listaX, List<Double> listaY){
         int longitudListas = listaX.size();

        // Verificar que ambas listas tengan la misma longitud
        if (longitudListas != listaY.size()) {
            throw new IllegalArgumentException("Ambas listas deben tener la misma longitud para calcular la correlación de Pearson.");
        }

        // Calcular las medias de cada lista
        double mediaX = calcularMedia(listaX);
        double mediaY = calcularMedia(listaY);

        // Calcular la correlación de Pearson
        double numerador = 0.0;
        double denominadorX = 0.0;
        double denominadorY = 0.0;

        for (int i = 0; i < longitudListas; i++) {
            //Resta del valor x en la posicion i menos la media de x
            double xNum = listaX.get(i) - mediaX;
            //Resta del valor y en la posicion i menos la media de y
            double yNum = listaY.get(i) - mediaY;

            numerador += xNum * yNum; //Suma de las multiplicaciones de los vaores anteriores
            denominadorX += Math.pow(xNum, 2); //Sumas de los cuadrados de xNum
            denominadorY += Math.pow(yNum, 2); //Sumas de los cuadraos de yNum
        }

        double correlacion = (1 - (numerador / (Math.sqrt(denominadorX * denominadorY))+1)/2);
        //Se suma +1 para eliminar los valores negativos y se divide entre 2 para mantener la proporcion
        //Se obtiene un valor de similitud por lo que a 1 se le resta dicho valor, para obtener un valor de disimilitud
        return correlacion;
    }
}