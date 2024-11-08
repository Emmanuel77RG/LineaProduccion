package GUI;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private Produccion panelProduccion;

    public VentanaPrincipal() {
        setTitle("Línea de Producción");
        setSize(755, 469);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelProduccion = new Produccion();
        add(panelProduccion);

        setVisible(true);
    }

    public Produccion getPanelProduccion() {
        return panelProduccion;
    }
}
