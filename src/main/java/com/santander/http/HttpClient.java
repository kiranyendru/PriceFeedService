package com.santander.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class HttpClient {
    private final CloseableHttpClient httpClient;


    public HttpClient() {
        this.httpClient = HttpClientBuilder.create().build();
    }

    public String post(String URI, String requestBody) {
        if(null != URI && requestBody != null) {
            HttpPost httpPost = new HttpPost(URI);
            httpPost.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

            // Send the request and handle the response
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
            } catch (IOException e) {
                System.out.println("Internal Error:  Failed to publish price to REST endpoint " + e);
            }
            if(response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    return "Price published successfully";
                }
            }
        }
        return "Failed to publish price";
    }
}
