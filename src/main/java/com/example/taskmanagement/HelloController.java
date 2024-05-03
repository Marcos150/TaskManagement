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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable
{
    @FXML
    private TableColumn<String, String> columnaDni;
    @FXML
    public TableColumn<String, String> columnaNombre;
    @FXML
    public TableColumn<String,String> columnaApellidos;
    @FXML
    public TableColumn<String, String> columnaEspecialidad;
    @FXML
    public TableColumn<String, String> columnaEmail;
    @FXML
    private Button btnTasks;
    @FXML
    private Button btnCleanses;
    @FXML
    private Button btnWorkers;
    @FXML
    private TableView<Object> listView;
    private Service<?> service =new Service<Object>("BASE_URL","");
    private ObservableList<Object>obList= FXCollections.observableList(new ArrayList<Object>());

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
        displayList("BASE_URL",Trabajador.class,"api/trabajadores");
    }
    @FXML
    protected void btnTasksDisplay(){
        displayList("BASE_URL",Trabajo.class,"api/trabajo");
    }
    private <T> void displayList(String constant,Class<T> cls, String url){
        listView.getItems().clear();
        service=new Service<Object>(constant,url);
        Platform.runLater(()->{
            obList.addAll(service.getAll());
        });
    }
}