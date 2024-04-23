package main.java.modelo;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class LoadDivisas {

    private static final String URL = "https://v6.exchangerate-api.com/v6/9c8bced6fffad53823151f67/latest/";

    public static Map<String, Double> getTasasCambio(String divisa) {
        String response = realizarConsultaHTTP(divisa);
        return parsearJsonAMapDeDivisas(response);
    }

    private static String realizarConsultaHTTP(String divisa) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL+divisa))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Double> parsearJsonAMapDeDivisas(String json) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

        Map<String, Double> divisas = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : conversionRates.entrySet()) {
            divisas.put(entry.getKey(), entry.getValue().getAsDouble());
        }
        return divisas;
    }
}
