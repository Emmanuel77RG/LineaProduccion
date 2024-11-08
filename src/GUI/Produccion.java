package GUI;

import javax.swing.*;
import java.awt.*;

public class Produccion extends JPanel {
    private JLabel lblContador, lblEmpaquetados;

    public Produccion() {
        setLayout(new BorderLayout());

        lblContador = new JLabel("Artículos en la banda: 0");
        lblEmpaquetados = new JLabel("Artículos empaquetados: 0");

        add(lblContador, BorderLayout.NORTH);
        add(lblEmpaquetados, BorderLayout.SOUTH);
    }

    public void actualizarContador(int totalArticulos) {
        lblContador.setText("Artículos en la banda: " + totalArticulos);
    }

    public void actualizarEmpaquetados(int empaquetados) {
        lblEmpaquetados.setText("Artículos empaquetados: " + empaquetados);
    }
}
