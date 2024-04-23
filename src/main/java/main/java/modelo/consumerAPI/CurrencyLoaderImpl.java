package main.java.modelo.consumerAPI;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class CurrencyLoaderImpl implements CurrencyLoader {

    private String API_KEY;
    private String URL;

    public CurrencyLoaderImpl() {
        Dotenv dotenv = Dotenv.configure().load();
        API_KEY = dotenv.get("API_KEY");
        if (API_KEY == null || API_KEY.isEmpty()) {
            throw new RuntimeException("API_KEY environment variable is not set in .env file");
        }
        URL = "https://v6.exchangerate-api.com/v6/%s/latest/".formatted(API_KEY);
    }

    public  Map<String, Double> getExchangeRates(String divisa) throws IOException, InterruptedException {
        String response = makeHTTPRequest(divisa);
        return parseJsonToCurrencyMap(response);
    }

    private String makeHTTPRequest(String divisa) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL+divisa))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200){
            throw new IOException("Error en petición HTTP: ERROR[%d]".formatted(response.statusCode()));
        }

        return response.body();
    }

    private Map<String, Double> parseJsonToCurrencyMap(String json) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

        Map<String, Double> exchangeRates = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : conversionRates.entrySet()) {
            exchangeRates.put(entry.getKey(), entry.getValue().getAsDouble());
        }
        return exchangeRates;
    }
}
