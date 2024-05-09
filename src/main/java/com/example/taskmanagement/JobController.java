package com.example.taskmanagement;

import com.example.taskmanagement.models.Trabajador;
import com.example.taskmanagement.models.Trabajo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import static com.example.taskmanagement.utils.NavigationUtilities.navigateTo;
import static com.example.taskmanagement.utils.Utils.cellBuilder;

public class JobController implements Initializable {
    @FXML
    @Column
    public TableColumn<Trabajo, String> columnaCategoria;
    @FXML
    @Column
    public TableColumn<Trabajo, String> columnaDescripcion;
    @FXML
    @Column
    public TableColumn<Trabajo,String> columnaFecIni;
    @FXML
    @Column
    public TableColumn<Trabajo, String> columnaFecFin;
    @FXML
    @Column
    public TableColumn<Trabajo, String> columnaTiempo;
    @FXML
    @Column
    public TableColumn<Trabajo, String> columnaPrioridad;

    @FXML
    private Button btnTasks;
    @FXML
    private Button btnCleanses;
    @FXML
    private Button btnWorkers;
    @FXML
    private TableView<Trabajo> listView;
    private Service<Trabajo> service;
    private ObservableList<Trabajo> obList= FXCollections.observableList(new ArrayList<>());
    private CompletableFuture<List<Trabajo>> completableFuture;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(obList);
        cellBuilder(this,JobController.class);
        displayList("BASE_URL","api/trabajo");
    }
    @FXML
    protected void btnCleansesDisplay(MouseEvent event){
        navigateTo(this,"list-cleanses.fxml", event);
    }
    @FXML
    protected void btnWorkerDisplay(MouseEvent event){
        navigateTo(this,"hello-view.fxml", event);
    }
    @FXML
    protected void btnTasksDisplay(){
        displayList("BASE_URL","api/trabajo");
    }
    private <T> void displayList(String constant,String url) {
        service = new Service<>(constant, url, Trabajo.class);
        completableFuture= service.getAll();
        completableFuture.thenAcceptAsync(res->Platform.runLater(()-> {
            obList.clear();
            obList.addAll(res);
        }));
    }
}
