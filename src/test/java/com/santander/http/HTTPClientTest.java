package com.santander.http;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HTTPClientTest {
    private HttpClient httpClient;
    private final String URI = "https://santander.com/prices-with-brokerage";

    @Before
    public void before() {
        httpClient = new HttpClient();
    }

    @Test
    public void testPostFail() {
        String requestBody = String.format("{\"symbol\": \"%s\", \"bid\": %.5f, \"ask\": %.5f}",
                "GBP/USD", 1.264, 1.356);
        Assert.assertEquals(httpClient.post(URI, requestBody), "Failed to publish price");
    }

    @Test
    public void testPostWithNull() {
        String requestBody = String.format("{\"symbol\": \"%s\", \"bid\": %.5f, \"ask\": %.5f}",
                "GBP/USD", 1.264, 1.356);
        Assert.assertEquals(httpClient.post(null, null), "Failed to publish price");
    }

    @Test
    public void testPostSuccess() {
        String requestBody = String.format("{\"symbol\": \"%s\", \"bid\": %.5f, \"ask\": %.5f}",
                "GBP/USD", 1.264, 1.356);
        Assert.assertEquals(httpClient.post(URI, requestBody), "Price published successfully");
    }


}
