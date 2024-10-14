/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import connection.ConexionBaseDatos;
import gui.mainWindow;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import gui.Saving;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.testDAO;
import modelo.testObject;
import controlador.controladorPrinc;
//Diferencia entre metodo concreto y abstracto
/**
 *
 * @author galia
 */
public class controladorGuardar implements MouseListener, WindowListener  {
    private final Saving ventana;
    private final controladorPrinc controladorPrincipal; // Referencia al controlador principal
    List<String> direcciones = new ArrayList(); // Lista que guardará las ubicaciones de los archivos

    // Constructor que recibe una referencia al controlador principal
    public controladorGuardar(Saving ventana, controladorPrinc controladorPrincipal) {
        this.ventana = ventana;
        this.controladorPrincipal = controladorPrincipal; // Asignar la referencia al controlador principal

        this.ventana.setAlwaysOnTop(true);
        this.ventana.setVisible(true);
        this.ventana.setSize(new Dimension(400, 300));
        this.ventana.setResizable(true);
        this.ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        oyentes();
        
        String datos = this.controladorPrincipal.getDatosmatriz();
        System.out.println("------------------");
        System.out.println(datos);
        String tipoCorrelacion = this.controladorPrincipal.getTipoCorrelacion();
        System.out.println(tipoCorrelacion);

        // Agregar el WindowListener para detectar cuando la ventana se abra
        this.ventana.addWindowListener(this);
    }

    private void oyentes() {
        ventana.btnConfirmar.addMouseListener(this);
        ventana.btnCancelar.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == ventana.btnCancelar) {
            ventana.dispose();

        } else if (e.getSource() == ventana.btnConfirmar) {

            String name = ventana.txtfName.getText();
            System.out.println(name);
            String observaciones = ventana.textaObservaciones.getText();
            System.out.print(observaciones);
            

            testDAO dao = new testDAO(ConexionBaseDatos.conectar());

            try {
                int test = dao.insertarInfoMatriz(name, observaciones);
                String mensaje = "Se agregaron correctamente los datos";
                JOptionPane.showMessageDialog(ventana, mensaje);
                
                // Actualizar la tabla en la ventana principal
                controladorPrincipal.actualizarTabla(); // Llamada para actualizar la tabla
                
                ventana.dispose(); // Cerrar la ventana de confirmación
            } catch (Exception le) {
                String mensaje = "Ocurrió un error";
                JOptionPane.showMessageDialog(ventana, mensaje);
            }
        }
    }
    
    public void windowOpened(WindowEvent e) {
        String urlImagen = "//LAPTOP-RIL8H4PU/Users/galia/Documents/Estadias/Netbeans/save2.png";
        ImageIcon img = new ImageIcon(urlImagen);
        Icon micono = new ImageIcon(img.getImage().getScaledInstance(ventana.lbImagen.getWidth(),ventana.lbImagen.getHeight(),Image.SCALE_DEFAULT));
        ventana.lbImagen.setIcon(micono);
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
