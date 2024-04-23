package main.java.controlador;


import main.java.modelo.ServicioTasaCambio;
import main.java.vista.ViewConversor;

public class ControladorDivisas {
    private ServicioTasaCambio servicioTasaCambio;
    private ViewConversor viewConversor;
    private Thread taskThread;

    public ControladorDivisas(ServicioTasaCambio servicioTasaCambio) {
        this.servicioTasaCambio = servicioTasaCambio;
        this.viewConversor = new ViewConversor(this, servicioTasaCambio);
    }

    public void convertirDivisa(String origen, String destino, int cantidad) {
        taskThread = new Thread(() -> {
            viewConversor.stopWaitingStatus();
            servicioTasaCambio.convertirDivisa(origen, destino, cantidad);
            viewConversor.startWaitingStatus();
        });
        taskThread.start();
    }
}
