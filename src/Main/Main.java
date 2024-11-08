package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JLabel fondoLabel, cajaAbiertaLabel, cajaCerradaLabel;
    private JTextField txtPaquetesPorEmpaquetar, txtPaquetesEmpaquetados;
    private Timer timer;
    private int posX = 0;
    private int cantidadArticulos; // Paquetes en el lote actual
    private int empaquetadosEnLote = 0; // Paquetes empaquetados en el lote actual
    private int totalEmpaquetados = 0; // Contador acumulado de todos los empaquetados
    private boolean empaquetadoEnCentro = false; // Controla el estado de empaquetado en el centro

    public Main() {
        configurarVentana();
        iniciarProduccion();
    }

    private void configurarVentana() {
        setTitle("Línea de Producción");
        setSize(755, 469);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Imagen de fondo
        fondoLabel = new JLabel(new ImageIcon("C:\\Users\\Emmanuel Rojas\\Downloads\\Producción\\src\\GUI\\BandaTransportadora2.jpg"));
        fondoLabel.setBounds(0, 0, 755, 469);
        add(fondoLabel);

        // Caja abierta (primer estado)
        cajaAbiertaLabel = new JLabel(new ImageIcon("C:\\Users\\Emmanuel Rojas\\Downloads\\Producción\\src\\GUI\\Caja_abierta.png"));
        cajaAbiertaLabel.setBounds(posX, 175, 100, 100);
        fondoLabel.add(cajaAbiertaLabel);

        // Caja cerrada (segundo estado), inicialmente invisible
        cajaCerradaLabel = new JLabel(new ImageIcon("C:\\Users\\Emmanuel Rojas\\Downloads\\Producción\\src\\GUI\\Caja_empaquetada.png"));
        cajaCerradaLabel.setBounds(posX, 175, 100, 100);
        cajaCerradaLabel.setVisible(false);
        fondoLabel.add(cajaCerradaLabel);

        // Campo de texto para "Paquetes por empaquetar" en la esquina inferior izquierda
        txtPaquetesPorEmpaquetar = new JTextField("Paquetes por empaquetar: 0");
        txtPaquetesPorEmpaquetar.setBounds(10, 380, 200, 30); // Ajustar la posición y tamaño
        txtPaquetesPorEmpaquetar.setEditable(false);
        txtPaquetesPorEmpaquetar.setBackground(Color.WHITE);
        txtPaquetesPorEmpaquetar.setForeground(Color.BLACK);
        fondoLabel.add(txtPaquetesPorEmpaquetar);

        // Campo de texto para "Paquetes empaquetados" en la esquina inferior derecha
        txtPaquetesEmpaquetados = new JTextField("Paquetes empaquetados: 0");
        txtPaquetesEmpaquetados.setBounds(500, 380, 200, 30); // Ajustar la posición y tamaño
        txtPaquetesEmpaquetados.setEditable(false);
        txtPaquetesEmpaquetados.setBackground(Color.WHITE);
        txtPaquetesEmpaquetados.setForeground(Color.BLACK);
        fondoLabel.add(txtPaquetesEmpaquetados);
    }

    private void iniciarProduccion() {
        // Detenemos el timer mientras pedimos la nueva cantidad
        if (timer != null) {
            timer.stop();
        }

        // Pedir la cantidad de artículos y reiniciar el contador de empaquetados en el lote actual
        String input = JOptionPane.showInputDialog("Ingrese la cantidad de artículos:");
        if (input != null) { // Si el usuario no canceló
            cantidadArticulos = Integer.parseInt(input);
            empaquetadosEnLote = 0; // Reinicia el contador de empaquetados en el lote actual

            // Actualiza el contador de "Paquetes por empaquetar"
            actualizarContadorPaquetesPorEmpaquetar();

            // Configuración del timer para mover la caja
            if (timer == null) {
                timer = new Timer(10, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        moverCaja();
                    }
                });
            }
            timer.start();
        } else {
            System.exit(0); // Salir si el usuario cancela la entrada inicial
        }
    }

    // Método para mover la caja en la línea de producción
    private void moverCaja() {
        // Si no se ha empaquetado en el centro
        if (!empaquetadoEnCentro && posX < 315) {
            posX += 2;
            cajaAbiertaLabel.setBounds(posX, 175, 100, 100);
        } else if (!empaquetadoEnCentro) {
            // Cambia a caja empaquetada al llegar al centro
            empaquetarEnCentro();
        } else if (posX < getWidth() - 100) { // Mueve la caja empaquetada hasta el final de la ventana
            posX += 2;
            cajaCerradaLabel.setBounds(posX, 175, 100, 100);
        } else {
            // Incrementa el contador de empaquetados en el lote y el total acumulado
            empaquetadosEnLote++;
            totalEmpaquetados++;
            
            // Actualizar los contadores en pantalla
            actualizarContadorPaquetesPorEmpaquetar();
            actualizarContadorPaquetesEmpaquetados();

            if (empaquetadosEnLote < cantidadArticulos) {
                resetearPosicion();
            } else {
                // Mostrar mensaje cuando todos los artículos del lote han sido empaquetados
                JOptionPane.showMessageDialog(this, "Todos los artículos han sido empaquetados en este lote.");
                preguntarParaContinuar();
            }
        }
    }

    // Método que cambia la caja a empaquetada en el centro
    private void empaquetarEnCentro() {
        cajaAbiertaLabel.setVisible(false);
        cajaCerradaLabel.setVisible(true);
        empaquetadoEnCentro = true; // Marca que ya se realizó el empaquetado
    }

    // Pregunta si el usuario quiere empaquetar más artículos o salir
    private void preguntarParaContinuar() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea empaquetar más artículos?", "Continuar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            resetearPosicion();
            iniciarProduccion();
        } else {
            JOptionPane.showMessageDialog(this, "Programa finalizado.");
            System.exit(0);
        }
    }

    // Restablece la posición para el siguiente artículo
    private void resetearPosicion() {
        posX = 0;
        cajaAbiertaLabel.setVisible(true);
        cajaCerradaLabel.setVisible(false);
        empaquetadoEnCentro = false; // Reinicia el estado de empaquetado
        timer.restart();
    }

    // Actualiza el contador de "Paquetes por empaquetar" en el campo de texto correspondiente
    private void actualizarContadorPaquetesPorEmpaquetar() {
        int paquetesRestantes = cantidadArticulos - empaquetadosEnLote;
        txtPaquetesPorEmpaquetar.setText("Paquetes por empaquetar: " + paquetesRestantes);
    }

    // Actualiza el contador de "Paquetes empaquetados" acumulado en el campo de texto correspondiente
    private void actualizarContadorPaquetesEmpaquetados() {
        txtPaquetesEmpaquetados.setText("Paquetes empaquetados: " + totalEmpaquetados);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
