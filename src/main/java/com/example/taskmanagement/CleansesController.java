package com.example.taskmanagement;

import com.example.taskmanagement.models.Limpieza;
import com.example.taskmanagement.models.RespuestaLimpieza;
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

public class CleansesController implements Initializable {
    @FXML
    @Column
    public TableColumn<Limpieza, String> columnaFecha;
    @FXML
    @Column
    public TableColumn<Limpieza, String> columnaHabitacion;
    @FXML
    @Column
    public TableColumn<Limpieza,String> columnaObservaciones;

    @FXML
    private Button btnTasks;
    @FXML
    private Button btnCleanses;
    @FXML
    private Button btnWorkers;
    @FXML
    private TableView<Limpieza> listView;
    private Service<RespuestaLimpieza> service;
    private CompletableFuture<List<RespuestaLimpieza>> completableFuture;
    private ObservableList<Limpieza> obList= FXCollections.observableList(new ArrayList<>());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(obList);
        cellBuilder(this, CleansesController.class);
        displayList("BASE_URL_NODE","limpieza");
    }
    @FXML
    protected void btnCleansesDisplay(){
        displayList("BASE_URL_NODE","limpieza");
    }
    @FXML
    protected void btnWorkerDisplay(MouseEvent event){
        navigateTo(this,"hello-view.fxml", event);
    }
    @FXML
    protected void btnTasksDisplay(MouseEvent event){
        navigateTo(this,"list-jobs.fxml", event);
    }
    private <T> void displayList(String constant,String url) {
        obList.clear();
        service = new Service<>(constant, url, RespuestaLimpieza.class);
        completableFuture = service.getAll();
        completableFuture.thenAcceptAsync(res->Platform.runLater(() -> {
            obList.clear();
            obList.addAll(res.getFirst().resultado);
        }));
    }
}
