package main.java.modelo;

import java.util.*;

public class ServicioTasaCambio {
    private static final String divisaBase = "USD";
    private Map<String, Double> tasasCambio;
    private List<LoadModeloListener> listeners;
    private double lastResult;

    public ServicioTasaCambio() {
        tasasCambio = LoadDivisas.getTasasCambio(divisaBase);
        listeners = new ArrayList<>();
        lastResult = 0;
    }

    public void convertirDivisa(String origen, String destino, int cantidad) {
        getTasasCambio(origen);
        double tasaDestino = tasasCambio.get(destino);
        lastResult = cantidad * tasaDestino;
        notifyLoadFinishedListener();
    }

    public double getLastResult() {
        return lastResult;
    }

    public void addListener(LoadModeloListener listener) {
        listeners.add(listener);
    }

    public Set<String> getTasas() {
        return tasasCambio.keySet();
    }

    private void getTasasCambio(String divisa) {
        if(Objects.requireNonNull(tasasCambio).containsKey(divisa))
            tasasCambio = LoadDivisas.getTasasCambio(divisa);
    }

    private void notifyLoadFinishedListener() {
        for (LoadModeloListener listener : listeners) {
            listener.loadModeloFinished();
        }
    }
}
