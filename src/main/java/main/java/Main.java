package main.java;

import main.java.controlador.CurrencyController;
import main.java.modelo.ExchangeRateService;

public class Main {
    public static void main(String[] args) {
        ExchangeRateService exchangeRateService = new ExchangeRateService();
        new CurrencyController(exchangeRateService);
    }
}