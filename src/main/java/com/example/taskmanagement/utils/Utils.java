package com.example.taskmanagement.utils;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Arrays;

public class Utils {
    public static void cellBuilder(Object object, Class<?> ControllerClass) {
        Arrays.stream(ControllerClass.getDeclaredFields())
                .filter(p -> p.isAnnotationPresent(Column.class))
                .forEach(p -> {
                    try {
                        ((TableColumn<Object,String>)p.get(object))
                                .setCellValueFactory(new PropertyValueFactory<>(p.getName()
                                        .replace("columna","")));
                    } catch (IllegalAccessException e) {
                        System.out.println("Funny Exception");
                    } catch (NullPointerException o){
                        System.out.println(o.getMessage());
                    }
        });
    }
}
