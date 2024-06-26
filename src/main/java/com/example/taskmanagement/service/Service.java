package com.example.taskmanagement.service;

import com.example.taskmanagement.models.Trabajador;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Service<T>{
    private final HttpClient client;
    private HttpRequest RequestGET;
    private HttpRequest RequestPOST;
    private HttpRequest RequestPUT;
    private URI url;
    private final Class<T>type;
    public Service(String constant,String partialURI,Class<T>cls) {
        client=HttpClient.newHttpClient();
        url=URI.create(Dotenv.load().get(constant)+partialURI);
        RequestGET=HttpRequest.newBuilder().uri(url).GET().build();
        type=cls;
    }
    public CompletableFuture<List<T>> getAll(){
        return CompletableFuture.supplyAsync(()->{
            HttpResponse<String>response= null;
            try {
                response = client.send(RequestGET, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ObjectMapper mapper=new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            JsonParser parser= null;
            try {
                parser = factory.createParser(response.body());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                return mapper.readValue(parser,mapper.getTypeFactory().constructCollectionType(ArrayList.class, type));
            } catch (IOException e) {
                try
                {
                    //Si la API no da una lista el metodo crea y devuelve una lista con el objeto de la API
                    T singleResult = mapper.readValue(parser,mapper.getTypeFactory().constructType(type));
                    return Collections.singletonList(singleResult);
                } catch (IOException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void post(T object){
        try {
            RequestPOST = HttpRequest.newBuilder().uri(url).header("Content-Type","application/json")
                    .method("POST",HttpRequest.BodyPublishers.ofString(jsonObject(object))).build();
            HttpClient.newHttpClient().send(RequestPOST,HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void put(T object){
        try {
            RequestPUT = HttpRequest.newBuilder().uri(url).header("Content-Type","application/json")
                    .method("PUT",HttpRequest.BodyPublishers.ofString(jsonObject(object))).build();
            HttpClient.newHttpClient().send(RequestPUT,HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String jsonObject(T object){
        ObjectWriter ow=new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json="";
        try {
            json=ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public HttpRequest getRequestGET() {
        return RequestGET;
    }

    public void setRequestGET(HttpRequest requestGET) {
        RequestGET = requestGET;
    }
}
