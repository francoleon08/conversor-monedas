package main.java.modelo;

import main.java.modelo.exceptions.InvalidCurrencyExcpetion;

import java.util.*;

public class ExchangeRateService {
    private static final String BASE_CURRENCY = "USD";
    private static final Double DEFAULT_RATE = 1.0;
    private Map<String, Double> exchangeRates;
    private List<LoadModelListener> listeners;
    private double lastResult;

    public ExchangeRateService() {
        exchangeRates = CurrencyLoader.getExchangeRates(BASE_CURRENCY);
        listeners = new ArrayList<>();
        lastResult = 0;
    }

    public void convertCurrency(String source, String target, int amount) throws InvalidCurrencyExcpetion {
        if(amount <= 0) {
            throw new InvalidCurrencyExcpetion("La cantidad debe ser mayor a 0");
        }
        getExchangeRates(source);
        double destinationRate = exchangeRates.get(target);
        lastResult = amount * destinationRate;
        notifyLoadFinishedListener();
    }

    public double getLastResult() {
        return lastResult;
    }

    public void addListener(LoadModelListener listener) {
        listeners.add(listener);
    }

    public Set<String> getRates() {
        return exchangeRates.keySet();
    }

    private void getExchangeRates(String currency) {
        if(!Objects.equals(exchangeRates.get(currency), DEFAULT_RATE))
            exchangeRates = CurrencyLoader.getExchangeRates(currency);
    }

    private void notifyLoadFinishedListener() {
        for (LoadModelListener listener : listeners) {
            listener.loadModelFinished();
        }
    }
}
