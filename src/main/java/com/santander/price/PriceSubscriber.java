package com.santander.price;

import com.santander.http.HttpClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class PriceSubscriber implements Subscriber {

    private final Map<String, Price> latestPrices;
    private final HttpClient httpClient;
    private static final String ENDPOINT_URI = "https://api.santander.com/v1/prices/";

    public PriceSubscriber() {
        this.latestPrices = new HashMap<>();
        this.httpClient = new HttpClient();
    }

    public void onMessage(String message) {
        if(message != null) {
            String[] lines = message.split("\\r?\\n");
            for (String line : lines) {
                String[] fields = line.split(",");
                String id = fields[0].trim();
                String instrument = fields[1].trim();
                double bid = Double.parseDouble(fields[2].trim());
                double ask = Double.parseDouble(fields[3].trim());
                LocalDateTime timestamp = LocalDateTime.parse(fields[4].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS"));
                Price price = new Price(Long.parseLong(id), instrument, getBidAfterCommission(bid), getAskAfterCommission(ask), timestamp);
                latestPrices.put(instrument, price);

                String requestBody = String.format(
                        "{\"symbol\": \"%s\", \"bid\": %.5f, \"ask\": %.5f}",
                        price.getInstrument(), price.getBid(), price.getAsk());

                // publish the adjusted price to a REST endpoint
                    String status = httpClient.post(ENDPOINT_URI, requestBody);
                    System.out.println(status);
            }
        }

    }

    private double getBidAfterCommission(double bid) {
        return bid - (bid * 0.1)/100;
    }

    private double getAskAfterCommission(double ask) {
        return ask + (ask * 0.1)/100;
    }

    public Price getNewPrice(String instrument) {
        return latestPrices.get(instrument);
    }
}
