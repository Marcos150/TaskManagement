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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    private TableView<Trabajador> listView;
    private Service<Trabajador> service;
    private ObservableList<Trabajador> obList= FXCollections.observableList(new ArrayList<>());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(obList);
        cellBuilder(this,JobController.class);
    }
    @FXML
    protected void btnCleansesDisplay(){
        navigateTo(this,"list-cleanses.fxml");
    }
    @FXML
    protected void btnWorkerDisplay(){
        navigateTo(this,"hello-view.fxml");
    }
    @FXML
    protected void btnTasksDisplay(){
        displayList("BASE_URL","api/trabajo");
    }
    private <T> void displayList(String constant,String url) {

        Thread thread= new Thread(()->{
            obList.clear();
            service = new Service<>(constant, url, Trabajador.class);
            Platform.runLater(() -> {
                //obList.addAll(service.getAll());
            });
        });
        thread.start();
    }
}
