package com.example.taskmanagement.utils;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class NavigationUtilities {
    public static void navigateTo(Object ob,String url){
        try {
            FXMLLoader.load(ob.getClass().getResource(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
