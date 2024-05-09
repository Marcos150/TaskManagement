package com.example.taskmanagement;

import com.example.taskmanagement.models.Trabajador;
import com.example.taskmanagement.service.Service;
import com.example.taskmanagement.utils.Column;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.example.taskmanagement.utils.NavigationUtilities.navigateTo;
import static com.example.taskmanagement.utils.Utils.cellBuilder;

public class WorkerController implements Initializable
{
    @FXML
    @Column
    public TableColumn<Trabajador, String> columnaDni;
    @FXML
    @Column
    public TableColumn<Trabajador, String> columnaNombre;
    @FXML
    @Column
    public TableColumn<Trabajador,String> columnaApellidos;
    @FXML
    @Column
    public TableColumn<Trabajador, String> columnaEspecialidad;
    @FXML
    @Column
    public TableColumn<Trabajador, String> columnaEmail;

    @FXML
    private Button btnTasks;
    @FXML
    private Button btnCleanses;
    @FXML
    private Button btnWorkers;
    @FXML
    private TableView<Trabajador> listView;
    private Service<Trabajador> service;
    private ObservableList<Trabajador>obList= FXCollections.observableList(new ArrayList<>());
    private CompletableFuture<List<Trabajador>> completableFuture;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(obList);
        cellBuilder(this,WorkerController.class);
    }
    @FXML
    protected void btnCleansesDisplay(MouseEvent event) {
        if(!completableFuture.isDone())completableFuture.cancel(true);
        navigateTo(this,"list-cleanses.fxml", event);
    }
    @FXML
    protected void btnWorkerDisplay(){
        displayList("BASE_URL","api/trabajadores");
    }
    @FXML
    protected void btnTasksDisplay(MouseEvent event){
        if(!completableFuture.isDone())completableFuture.cancel(true);
        navigateTo(this,"list-jobs.fxml", event);
    }
    private <T> void displayList(String constant,String url) {
        service = new Service<>(constant, url, Trabajador.class);
        completableFuture= service.getAll();
        completableFuture.thenAcceptAsync(res->Platform.runLater(()-> {
            obList.clear();
            obList.addAll(res);
        }));

    }

}