package ed.matdisim;

import controlador.controladorPrinc;
import gui.ventanaPrinc;

public class MatDisim{

    public static void main(String[] args){
        ventanaPrinc ventana = new ventanaPrinc();
        controladorPrinc controlador = new controladorPrinc(ventana);
    }
}