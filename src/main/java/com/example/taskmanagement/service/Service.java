package com.example.taskmanagement.service;

import com.example.taskmanagement.models.Trabajador;
import com.example.taskmanagement.models.Trabajo;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Service<T>{
    private final HttpClient client;
    private HttpRequest RequestGET;
    private final Class<T>type;
    public Service(String constant,String partialURI,Class<T>cls) {
        client=HttpClient.newHttpClient();
        RequestGET=HttpRequest.newBuilder().uri(URI.create(Dotenv.load().get(constant)+partialURI)).GET().build();
        type=cls;
    }
    public List<T> getAll(){
        try {
            HttpResponse<String>response=client.send(RequestGET, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper=new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            JsonParser parser=factory.createParser(response.body());
            return mapper.readValue(parser,mapper.getTypeFactory().constructCollectionType(ArrayList.class, type));
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
