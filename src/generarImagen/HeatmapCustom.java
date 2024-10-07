/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generarImagen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HeatmapCustom extends JPanel {

    private final double[][] values;

    public HeatmapCustom(double[][] values) {
        this.values = values;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHeatmap(g);
    }

    private void drawHeatmap(Graphics g) {
        int cellSize = 100; // Tamaño de cada celda en la matriz
        BufferedImage heatmap = new BufferedImage(cellSize * values[0].length, cellSize * values.length, BufferedImage.TYPE_INT_RGB);

        // Escala de colores personalizada
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                int color = getColorForValue(values[i][j]);
                for (int x = 0; x < cellSize; x++) {
                    for (int y = 0; y < cellSize; y++) {
                        heatmap.setRGB(j * cellSize + x, i * cellSize + y, color);
                    }
                }
            }
        }

        g.drawImage(heatmap, 0, 0, null);
    }

    private int getColorForValue(double value) {
        if (value >= 1.0) {
            return new Color(255, 215, 0).getRGB(); // Amarillo mate para 1.0
        } else if (value > 0) {
            // Amarillos, gradiente desde amarillo claro hasta amarillo mate
            // Modificar para hacer amarillo más tenue cerca de 0
            int r = (int) (255 * value); // Rojo aumenta al acercarse a 1
            int g = (int) (215 * value); // Verde aumenta para un tono más cálido
            
            // Hacer el amarillo más claro cerca de 0
            int alpha = (int) (255 * (1 - value)); // Mayor transparencia a medida que el valor se acerca a 0
            return new Color(r, g, 0, alpha).getRGB(); // Devolver el color amarillo
        } else if (value == 0) {
            return Color.WHITE.getRGB(); // Color blanco para 0
        } else if (value > -1.0) {
            // Azules, gradiente desde azul muy claro hasta azul intenso
            int b = (int) (255 * -value * 0.5); // Azul se incrementa, pero más tenue cerca de 0
            return new Color(0, 0, b).getRGB(); // Devolver el color azul
        } else {
            return Color.BLUE.getRGB(); // Color azul intenso para -1.0
        }
    }

    public static void main(String[] args) {
        double[][] values = {
            {1.0, 0.8183960282606292, 0.8577663374663687},
            {0.8183960282606292, 1.0, 0.4073617368832029},
            {0.8577663374663687, 0.4073617368832029, -1.0}
        };

    }

}


//[1.0, 0.8183960282606292, 0.8577663374663687, 0.8183960282606292, 1.0, 0.4073617368832029, 0.8577663374663687, 0.4073617368832029, 1.0]