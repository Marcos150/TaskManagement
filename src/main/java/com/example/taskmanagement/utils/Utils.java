package com.example.taskmanagement.utils;


import com.example.taskmanagement.WorkerController;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.Arrays;

import java.util.Locale;


public class Utils {
    public static void cellBuilder(Object object, Class<?> ControllerClass) {
        Arrays.stream(ControllerClass.getDeclaredFields())
                .filter(p -> p.isAnnotationPresent(Column.class))
                .forEach(p -> {
                    try {
                        ((TableColumn<Object,String>)p.get(object))
                                .setCellValueFactory(new PropertyValueFactory<>(p.getName()
                                        .replace("columna","")
                                        .toLowerCase(Locale.ROOT)));
                    } catch (IllegalAccessException e) {
                        System.out.println("Funny Exception");
                    } catch (NullPointerException o){
                        System.out.println(o.getMessage());
                    }
        });
    }
}
