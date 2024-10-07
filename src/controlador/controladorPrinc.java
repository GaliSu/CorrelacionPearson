package controlador;

import static calculos.calcPearson.calcPearson;
import static calculos.corCoseno.corCoseno;
import static calculos.cosenoListas.cosenoListas;
import static calculos.pearson.pearsonPar;
import connection.ConexionBaseDatos;
import gui.mainWindow;
import gui.ventanaPrinc;
import modelo.testDAO;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static lectura.csvRead.leerCSV;
import java.awt.event.WindowListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import gui.Saving;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import generarImagen.HeatmapCustom;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class controladorPrinc implements MouseListener, WindowListener{
    private mainWindow ventana;
    List<String> direcciones = new ArrayList(); //Lista que guardará las ubicaciones de los archivos
    
    
    
    public controladorPrinc(mainWindow ventana){
        this.ventana = ventana;
        
        this.ventana.setAlwaysOnTop(true);
        this.ventana.setVisible(true);
        this.ventana.setSize(new Dimension(420, 500));
        this.ventana.setResizable(true);
        
        ventana.btnGuardar.setEnabled(false);
        ventana.btnGuardarmatriz.setEnabled(false);
        
        ventana.testGalia.setText("Hola Galia :D");
        
        oyentes();
        
        // Agregar el WindowListener para detectar cuando la ventana se abra
        this.ventana.addWindowListener(this);
    }
    
    public controladorPrinc(){
        this.ventana = ventana;

    }
    
    // Método para actualizar la tabla desde la base de datos
    public void actualizarTabla() {
        llenarTablaDesdeBaseDatos();
    }
    
    
    private void oyentes(){
        ventana.btnAdd.addMouseListener(this);
        ventana.btnCalc.addMouseListener(this);
        ventana.btnEliminar.addMouseListener(this);
        ventana.btnGuardar.addMouseListener(this);
    }
    
    private void llenarTabla(JTable tabDir){ //Método para colocar las ubicaciones de los archivos en una tabla
        //DefaultTableModel modeloT = (DefaultTableModel) ventana.tabGuardado.getModel();
        DefaultTableModel modeloT = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
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
        if(e.getSource() == ventana.btnAdd){ // Al presionar el botón "Buscar"
            JFileChooser selectorArchivos = new JFileChooser(); // Se crea un selector de archivos de tipo JFileChooser
            selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
            // Pasa la ventana principal como argumento para que el file chooser se sobreponga
            selectorArchivos.showOpenDialog(ventana); 

            // Asegúrate de que un archivo fue seleccionado antes de agregarlo
            if (selectorArchivos.getSelectedFile() != null) {
                direcciones.add(selectorArchivos.getSelectedFile().getAbsolutePath()); // Se agrega la ubicación del archivo seleccionado a la lista "direcciones"
                llenarTabla(ventana.tabDir); // Método llenar tabla, que agrega el archivo seleccionado a la tabla
            }
    
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
                
                // Para más de dos archivos .csv:
            else if (allGroupsResults.size() > 2) {
                List<Double> pearsonFinal = calcPearson(allGroupsResults); // Se obtiene la correlación entre los vectores
                System.out.println("El vector final es: " + pearsonFinal);

                // Calcula el tamaño de la matriz
                int size = (int) Math.ceil(Math.sqrt(pearsonFinal.size()));
                double[][] matrizPearson = new double[size][size]; // Crea la matriz cuadrada

                // Llena la matriz con los valores de pearsonFinal
                for (int i = 0; i < pearsonFinal.size(); i++) {
                    int row = i / size;
                    int col = i % size;
                    matrizPearson[row][col] = pearsonFinal.get(i);
                }

                // Crea el JFrame para la matriz de calor
                JFrame frame = new JFrame("Custom Heatmap Example");
                HeatmapCustom heatmapPanel = new HeatmapCustom(matrizPearson);
                frame.add(heatmapPanel, BorderLayout.CENTER); // Agrega el panel de la matriz de calor en el centro
                frame.setSize(300, 300); // Ajusta el tamaño del frame
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setAlwaysOnTop(true); // Asegura que la ventana esté siempre encima
                frame.setVisible(true);

                // Crea un BufferedImage con el contenido del panel de la matriz de calor
                BufferedImage image = new BufferedImage(heatmapPanel.getWidth(), heatmapPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = image.createGraphics();
                heatmapPanel.paint(g2d); // Dibuja el panel en el BufferedImage
                g2d.dispose();

                // Abre un JFileChooser para que el usuario elija la ubicación donde guardar la imagen
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar imagen como"); // Título de la ventana
                fileChooser.setSelectedFile(new File("heatmap_image.png")); // Nombre de archivo por defecto

                // Muestra el diálogo de guardado y obtiene la respuesta del usuario
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile(); // Archivo seleccionado por el usuario
                    try {
                        // Guarda la imagen en la ubicación seleccionada
                        ImageIO.write(image, "png", fileToSave);
                        System.out.println("Imagen guardada en: " + fileToSave.getAbsolutePath());
                    } catch (Exception eG) {
                        eG.printStackTrace();
                    }
                }
            }

             ventana.btnGuardarmatriz.setEnabled(true);    
               
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
            // Habilitar el botón "Guardar" después de calcular
            ventana.btnGuardar.setEnabled(true);
        }else if(e.getSource() == ventana.btnEliminar){
            
            ventana.testGalia.setText("Actualice Galia :D");
            
            int fila = ventana.tabDir.getSelectedRow();
            DefaultTableModel modelo = (DefaultTableModel) ventana.tabDir.getModel();
    
            if (fila >= 0) {
                // Obtener la dirección del archivo 
                String archivoEliminado = direcciones.get(fila);
        
                // Eliminar el archivo de la lista
                direcciones.remove(fila);
        
                // Eliminar la fila de la tabla
                modelo.removeRow(fila);
        
            } else {
                JOptionPane.showMessageDialog(ventana, "Seleccionar Fila");
            }
        }else if(e.getSource() == ventana.btnGuardar){
             if (ventana.btnGuardar.isEnabled()) {
                Saving ventanaGuardar = new Saving();
                controladorGuardar controladorGuardar = new controladorGuardar(ventanaGuardar, this); // Pasar referencia de controladorPrinc
            } else {
                JOptionPane.showMessageDialog(ventana, "Debes calcular la correlación antes de guardar.");
            }

        }else if(e.getSource() == ventana.btnEliminar){
            
        }     
    }
    
    public void windowOpened(WindowEvent e) {
        String urlImagen = "//LAPTOP-RIL8H4PU/Users/galia/Documents/Estadias/Netbeans/nicLab.png";
        ImageIcon img = new ImageIcon(urlImagen);
        Icon micono = new ImageIcon(img.getImage().getScaledInstance(ventana.lbLogo.getWidth(),ventana.lbLogo.getHeight(),Image.SCALE_DEFAULT));
        ventana.lbLogo.setIcon(micono);
        
        llenarTablaDesdeBaseDatos(); // Llenar la tabla desde la base de datos cuando se abre la ventana
    }
    
    
   public void llenarTablaDesdeBaseDatos() {
        // Crear un modelo de tabla que no permita la edición de celdas
        DefaultTableModel modeloT = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilitar la edición de todas las celdas
            }
        };

        ventana.tabGuardado.setModel(modeloT); // Establecer el modelo en la tabla
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("Observaciones");

        modeloT.setRowCount(0); // Limpiar la tabla

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT name, fecha, observaciones FROM matrizinfo"; // Consulta para obtener los datos de la tabla matrizinfo

        try {
            conn = ConexionBaseDatos.conectar(); // Usar la clase ConexionBaseDatos para conectar
            stmt = conn.createStatement(); // Cambiado a createStatement()
            rs = stmt.executeQuery(sql);

            // Iterar sobre los resultados y agregarlos a la tabla
            while (rs.next()) {
                String name = rs.getString("name");
                Timestamp fecha = rs.getTimestamp("fecha"); // Cambiar de int a Timestamp
                String observaciones = rs.getString("observaciones");
                modeloT.addRow(new Object[]{name, fecha, observaciones});
            }
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla desde la base de datos.");
            e.printStackTrace();
        } finally {
            // Cerrar ResultSet, Statement y Connection
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                ConexionBaseDatos.cerrarConexion(conn); // Usar el método cerrarConexion
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos de la base de datos.");
                e.printStackTrace();
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

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
}
