module com.example.taskmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.kordamp.ikonli.javafx;
    requires java.net.http;
    requires io.github.cdimascio.dotenv.java;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires net.synedra.validatorfx;
    requires mfx.core;
    requires mfx.resources;
    requires mfx.effects;
    requires mfx.localization;
    requires layout;
    requires kernel;
    requires io;
    requires com.google.api.client;
    requires com.google.api.services.gmail;
    requires com.google.api.client.json.gson;
    requires google.api.client;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires jakarta.mail;
    requires jdk.httpserver;

    opens com.example.taskmanagement to javafx.fxml;
    opens com.example.taskmanagement.models to com.fasterxml.jackson.databind,javafx.base;
    exports com.example.taskmanagement;
    exports com.example.taskmanagement.models;
}