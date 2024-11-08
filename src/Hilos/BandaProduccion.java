package Hilos;

import GUI.Produccion;

public class BandaProduccion extends Thread {
    private int cantidadArticulos;
    private Produccion panel;
    private int empaquetados;

    public BandaProduccion(int cantidadArticulos, Produccion panel) {
        this.cantidadArticulos = cantidadArticulos;
        this.panel = panel;
        this.empaquetados = 0;
    }

    @Override
    public void run() {
        for (int i = 1; i <= cantidadArticulos; i++) {
            try {
                Thread.sleep(1000); // Simula el movimiento de cada artículo
                panel.actualizarContador(i);
                empaquetados++;
                panel.actualizarEmpaquetados(empaquetados);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
