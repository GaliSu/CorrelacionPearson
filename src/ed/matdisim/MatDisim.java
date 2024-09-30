package ed.matdisim;

import controlador.controladorPrinc;
import gui.ventanaPrinc;
import gui.mainWindow;

public class MatDisim{

    public static void main(String[] args){
        mainWindow ventana = new mainWindow();
        controladorPrinc controlador = new controladorPrinc(ventana);
    }
}