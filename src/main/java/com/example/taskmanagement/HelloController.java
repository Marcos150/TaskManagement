package com.example.taskmanagement;

import com.example.taskmanagement.models.Limpieza;
import com.example.taskmanagement.models.Trabajador;
import com.example.taskmanagement.models.Trabajo;
import com.example.taskmanagement.service.Service;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.*;

public class HelloController implements Initializable
{
    @FXML
    private Button btnTasks;
    @FXML
    private Button btnCleanses;
    @FXML
    private Button btnWorkers;
    @FXML
    private TableView<Trabajador> listView;
    @FXML
    private TableColumn<Trabajador, String> column1;
    @FXML
    private TableColumn<String, String> column2;
    @FXML
    private TableColumn<String, String> column3;
    @FXML
    private TableColumn<String, String> column4;
    @FXML
    private TableColumn<String, String> column5;
    @FXML
    private TableColumn<String, String> column6;
    @FXML
    private TableColumn<String, String> column7;
    private Service<Trabajador> service =new Service<>("BASE_URL","api/trabajo", Trabajador.class);
    private ObservableList<Trabajador>obList= FXCollections.observableList(new ArrayList<Trabajador>());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(obList);
    }
    @FXML
    protected void btnCleansesDisplay(){
        displayList("BASE_URL_NODE",Limpieza.class,"limpieza");
    }
    @FXML
    protected void btnWorkerDisplay(){
        column1.setCellValueFactory(new PropertyValueFactory<Trabajador,String>("nombre"));
        displayList("BASE_URL",Trabajador.class,"api/trabajadores");
    }
    @FXML
    protected void btnTasksDisplay(){
        displayList("BASE_URL",Trabajo.class,"api/trabajo");
    }
    private <T> void displayList(String constant,Class<T> cls, String url){
        obList.clear();
        service=new Service<>(constant,url, Trabajador.class);
        Platform.runLater(()->{
            obList.addAll(service.getAll());
        });
    }
}