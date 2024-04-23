package main.java;

import main.java.controlador.CurrencyController;
import main.java.modelo.ExchangeRateService;
import main.java.modelo.consumerAPI.CurrencyLoader;
import main.java.modelo.consumerAPI.CurrencyLoaderImpl;

public class Main {
    public static void main(String[] args) {
        CurrencyLoader currencyLoader = new CurrencyLoaderImpl();
        ExchangeRateService exchangeRateService = new ExchangeRateService(currencyLoader);
        new CurrencyController(exchangeRateService);
    }
}