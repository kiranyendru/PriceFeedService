package com.santander.price;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PriceSubscriberTest {

    @Test
    public void testOnMessage() {
        PriceSubscriber priceSubscriber = new PriceSubscriber();
        String message = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001 \n 107, EUR/GBP, 1.1000,1.2000,01-06-2020 12:01:01:001";
        priceSubscriber.onMessage(message);
        Price latestPrice = priceSubscriber.getNewPrice("EUR/GBP");
        Assert.assertEquals(107, latestPrice.getId());
        Assert.assertEquals("EUR/GBP", latestPrice.getInstrument());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
        LocalDateTime expectedTimestamp = LocalDateTime.parse("01-06-2020 12:01:01:001", formatter);
        Assert.assertEquals(expectedTimestamp, latestPrice.getTimestamp());
        Assert.assertEquals(1.0989, latestPrice.getBid(), 0.0011);
        Assert.assertEquals(1.2012, latestPrice.getAsk(), 0.0012);
    }
}