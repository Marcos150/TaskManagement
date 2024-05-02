package com.example.taskmanagement.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Service<T>{
    private HttpClient client;
    private HttpRequest RequestGET;
    public Service(String constant,String partialURI) {
        client=HttpClient.newHttpClient();
        RequestGET=HttpRequest.newBuilder().uri(URI.create(Dotenv.load().get(constant)+partialURI)).GET().build();

    }
    public List<T> getAll(){
        try {
            HttpResponse<String>response=client.send(RequestGET, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper=new ObjectMapper();
            List<T>collection=new ArrayList<T>();
            JsonFactory factory = mapper.getFactory();
            JsonParser parser=factory.createParser(response.body());
            collection=mapper.reader().readValue(parser,collection.getClass());
            return collection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpRequest getRequestGET() {
        return RequestGET;
    }

    public void setRequestGET(HttpRequest requestGET) {
        RequestGET = requestGET;
    }
}
