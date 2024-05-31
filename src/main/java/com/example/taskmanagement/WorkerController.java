package com.example.taskmanagement;

import com.example.taskmanagement.models.Trabajador;
import com.example.taskmanagement.models.Trabajo;
import com.example.taskmanagement.service.Service;
import com.example.taskmanagement.utils.Column;
import com.example.taskmanagement.utils.PdfUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

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
    @Column
    public TableColumn<Trabajador, String> columnaIdTrabajador;

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
        displayList("BASE_URL","api/trabajador");
    }
    @FXML
    protected void btnCleansesDisplay(MouseEvent event) {
        if(completableFuture!=null && !completableFuture.isDone())completableFuture.cancel(true);
        navigateTo(this,"list-cleanses.fxml", event);
    }
    @FXML
    protected void btnWorkerDisplay(){
        displayList("BASE_URL","api/trabajador");
    }
    @FXML
    protected void btnTasksDisplay(MouseEvent event){
        if(completableFuture!=null && !completableFuture.isDone())completableFuture.cancel(true);
        navigateTo(this,"list-jobs.fxml", event);
    }
    private <T> void displayList(String constant,String url) {
        service = new Service<>(constant, url, Trabajador.class);
        completableFuture= service.getAll();
        completableFuture.thenAcceptAsync(res->Platform.runLater(()-> {
            listView.setRowFactory(_ -> {
                TableRow<Trabajador> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    Dialog<Trabajo> dialog = new Dialog<>();
                    dialog.setTitle("Edit worker");
                    dialog.setHeaderText("Edit");
                    dialog.setContentText("Edit worker details");

                    // Set the button types.
                    ButtonType loginButtonType = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

                    // Create the username and password labels and fields.
                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setPadding(new Insets(20, 150, 10, 10));

                    TextField dni = new TextField();
                    dni.setPromptText(row.getItem().getDni());
                    dni.setText(row.getItem().getDni());

                    TextField nombre = new TextField();
                    nombre.setPromptText(row.getItem().getNombre());
                    nombre.setText(row.getItem().getNombre());

                    TextField apellidos = new TextField();
                    apellidos.setPromptText(row.getItem().getApellidos());
                    apellidos.setText(row.getItem().getApellidos());

                    TextField email = new TextField();
                    email.setPromptText(row.getItem().getEmail());
                    email.setText(row.getItem().getEmail());

                    TextField especialidad = new TextField();
                    especialidad.setPromptText(row.getItem().getEspecialidad());
                    especialidad.setText(row.getItem().getEspecialidad());

                    grid.add(new Label("DNI:"), 0, 0);
                    grid.add(dni, 1, 0);
                    grid.add(new Label("Name:"), 0, 1);
                    grid.add(nombre, 1, 1);
                    grid.add(new Label("Surnames:"), 0, 2);
                    grid.add(apellidos, 1, 2);

                    grid.add(new Label("Email:"), 0, 3);
                    grid.add(email, 1, 3);
                    grid.add(new Label("Speciality:"), 0, 4);
                    grid.add(especialidad, 1, 4);

                    dialog.getDialogPane().setContent(grid);

                    // Convert the result to a username-password-pair when the login button is clicked.
                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == loginButtonType) {
                            try
                            {
                                service = new Service<>("BASE_URL", "api/trabajador/" + row.getItem().getIdTrabajador(), Trabajador.class);
                                row.getItem().setDni(dni.getText());
                                row.getItem().setNombre(nombre.getText());
                                row.getItem().setApellidos(apellidos.getText());
                                row.getItem().setEmail(email.getText());
                                row.getItem().setEspecialidad(especialidad.getText());

                                service.put(row.getItem());
                                btnWorkerDisplay();
                            } catch (Exception e)
                            {
                                new Alert(Alert.AlertType.ERROR, "Some of the fields were incorrect", ButtonType.OK).show();
                            }
                        }
                        return null;
                    });
                    dialog.showAndWait();
                });

                return row;
            });

            obList.clear();
            obList.addAll(res);
        }));

    }
    public void generate(MouseEvent event){
        if(listView.getItems().isEmpty()){
            throw new RuntimeException();
        }
        CompletableFuture<Void>action=CompletableFuture.runAsync(()->{
            listView.getItems().forEach(e->{
                PdfUtils.writePDF("paycheck_"+ e.getDni(),e);
            });
        });
        action.thenRunAsync(()->Platform.runLater(()->new Alert(Alert.AlertType.INFORMATION, "Paychecks generated successfully").show()));
    }

    public void btnAddWorker(MouseEvent mouseEvent)
    {
        navigateTo(this,"create-worker.fxml", mouseEvent);
    }
}