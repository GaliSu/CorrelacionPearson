package controlador;

import static calculos.calcPearson.calcPearson;
import static calculos.corCoseno.corCoseno;
import static calculos.cosenoListas.cosenoListas;
import static calculos.pearson.pearsonPar;
import gui.ventanaPrinc;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static lectura.csvRead.leerCSV;

public class controladorPrinc implements MouseListener{
    private final ventanaPrinc ventana;
    List<String> direcciones = new ArrayList(); //Lista que guardará las ubicaciones de los archivos
    
    public controladorPrinc(ventanaPrinc ventana){
        this.ventana = ventana;
        
        this.ventana.setAlwaysOnTop(true);
        this.ventana.setVisible(true);
        this.ventana.setSize(new Dimension(420, 500));
        this.ventana.setResizable(true);
        
        oyentes();
    }
    
    private void oyentes(){
        ventana.btnAdd.addMouseListener(this);
        ventana.btnCalc.addMouseListener(this);
    }
    
    private void llenarTabla(JTable tabDir){ //Método para colocar las ubicaciones de los archivos en una tabla
        DefaultTableModel modeloT = new DefaultTableModel();
        tabDir.setModel(modeloT);
        
        modeloT.addColumn("Archivos seleccionados");
        modeloT.setNumRows(0);
        
        int numArchivos = direcciones.size();
        
        for(int i=0;i<numArchivos;i++){ //Para cada archivo seleccionado:
            modeloT.addRow(new Object[]{direcciones.get(i)}); //Se añade una fila y se agrega el archivo que se haya seleccionado
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == ventana.btnAdd){ //Al presionar el botón "Buscar"
            JFileChooser selectorArchivos = new JFileChooser(); //Se crea un selector de archivos de tipo JFileChooser
            selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            
            selectorArchivos.showOpenDialog(selectorArchivos);

            direcciones.add(selectorArchivos.getSelectedFile().getAbsolutePath()); //Se agrega la ubicación del archivo seleccionado a la lista "direcciones"
            
            llenarTabla(ventana.tabDir); //Método llenar tabla, que agrega el archivo seleccionado a la tabla
        }
        
        if(e.getSource() == ventana.btnCalc){ //Al presionar el botón "Calcular":
            
            if(ventana.selMetodo.getSelectedItem() == "Correlación de Pearson"){ //Selección de método para "Correlación de Pearson"
                List<List<Double>> allGroupsResults = new ArrayList(); //Lista que guardará los vectores resultantes de cada grupo
                for(int i=0;i<direcciones.size();i++){
                    List<List<Double>> datos = leerCSV(direcciones.get(i));
                    List<Double> groupResult = calcPearson(datos); //Correlación de Pearson para los vectores de un archivo .csv
                    allGroupsResults.add(groupResult); //Se guardan esos resultados en una lista
                    System.out.println(groupResult); //Se imprime la correlación de 
                }
                if(allGroupsResults.size() == 2){ //En caso de solo ingresar dos archivos, se obtiene la correlación entre esos dos grupos, arrojando un solo valor
                    Double correlacion = pearsonPar(allGroupsResults.get(0),allGroupsResults.get(1));
                    System.out.println("El resultado final es: "+correlacion);
                }
                
                //Para más de dos archivos .csv:
                else if(allGroupsResults.size() > 2){
                    List<Double> pearsonFinal = calcPearson(allGroupsResults); //Se obtiene la correlación entre los vectores que contienen las correlaciones de cada grupo
                    System.out.println("El vector final es: "+pearsonFinal);

                }
            }
            else if(ventana.selMetodo.getSelectedItem() == "Coseno"){ //Selección de método para Coseno del ángulo entre vectores
                List<List<Double>> allGroupsResults = new ArrayList(); //Lista que guardará los vectores resultantes de cada grupo
                for(int i=0;i<direcciones.size();i++){
                    List<List<Double>> datos = leerCSV(direcciones.get(i));
                    List<Double> groupResult = cosenoListas(datos); //Cálculo de coseno del ángulo para los vectores de un archivo .csv
                    allGroupsResults.add(groupResult); //Se guardan esos resultados en una lista
                    System.out.println(groupResult); //Se imprime 
                }
                if(allGroupsResults.size() == 2){ //En caso de solo ingresar dos archivos, se obtiene la correlación entre esos dos grupos
                    Double correlacion = corCoseno(allGroupsResults.get(0),allGroupsResults.get(1));
                    System.out.println("El resultado final es: "+correlacion);
                }
                
                //Para más de dos archivos .csv:
                else if(allGroupsResults.size() > 2){
                    List<Double> cosenoFinal = cosenoListas(allGroupsResults); //Se obtiene el coseno del ángulo entre los vectores que contienen los cosenos de cada grupo
                    System.out.println("El vector final es: "+cosenoFinal);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
