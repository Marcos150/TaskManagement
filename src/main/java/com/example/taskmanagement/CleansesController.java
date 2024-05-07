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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.taskmanagement.utils.NavigationUtilities.navigateTo;
import static com.example.taskmanagement.utils.Utils.cellBuilder;

public class CleansesController implements Initializable {
    @FXML
    @Column
    public TableColumn<Trabajador, String> columnaCategoria;
    @FXML
    @Column
    public TableColumn<Trabajador, String> columnaDescripcion;
    @FXML
    @Column
    public TableColumn<Trabajador,String> columnaFecIni;
    @FXML
    @Column
    public TableColumn<Trabajador, String> columnaFecFin;
    @FXML
    @Column
    public TableColumn<Trabajador, String> columnaTiempo;
    @FXML
    @Column
    public TableColumn<Trabajador, String> columnaPrioridad;

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
        cellBuilder(this, CleansesController.class);
    }
    @FXML
    protected void btnCleansesDisplay(){
        navigateTo(this,"template.fmxl");
    }
    @FXML
    protected void btnWorkerDisplay(){
        navigateTo(this,"template.fmxl");
    }
    @FXML
    protected void btnTasksDisplay(){
        displayList("BASE_URL","api/trabajo");
    }
    private <T> void displayList(String constant,String url) {
        obList.clear();
        service = new Service<>(constant, url, Trabajador.class);
        Platform.runLater(() -> {
            obList.addAll(service.getAll());
        });
    }
}
