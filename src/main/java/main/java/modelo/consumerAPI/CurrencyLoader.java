package main.java.modelo.consumerAPI;

import java.io.IOException;
import java.util.Map;

public interface CurrencyLoader {

    public Map<String, Double> getExchangeRates(String divisa) throws IOException, InterruptedException;
}
