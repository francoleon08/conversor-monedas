package main.java;

import main.java.controlador.ControladorDivisas;
import main.java.modelo.ServicioTasaCambio;

public class Main {
    public static void main(String[] args) {
        ServicioTasaCambio servicioTasaCambio = new ServicioTasaCambio();
        new ControladorDivisas(servicioTasaCambio);
    }
}