package com.example.taskmanagement;

import com.example.taskmanagement.models.Trabajador;
import com.example.taskmanagement.models.Trabajo;
import com.example.taskmanagement.service.Service;
import com.example.taskmanagement.utils.Column;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.example.taskmanagement.utils.EmailUtils.*;
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
    @Column
    public TableColumn<Trabajo, String> columnaCodTrabajo;

    @FXML
    private Button btnTasks;
    @FXML
    private Button btnCleanses;
    @FXML
    private Button btnWorkers;
    @FXML
    private Button btnConfirmAssignation;
    @FXML
    private TableView<Trabajo> listView;

    private Service<Trabajo> serviceTrabajo;
    private Service<Trabajador> serviceTrabajador;
    private final ObservableList<Trabajo> obList = FXCollections.observableList(new ArrayList<>());
    private CompletableFuture<List<Trabajo>> completableFutureTrabajo;
    private CompletableFuture<List<Trabajador>> completableFutureTrabajador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(obList);
        cellBuilder(this,JobController.class);
        displayList("BASE_URL","api/trabajo");

        serviceTrabajador = new Service<>("BASE_URL", "api/trabajador", Trabajador.class);
        completableFutureTrabajador = serviceTrabajador.getAll();
        completableFutureTrabajador.thenAcceptAsync(res->Platform.runLater(()-> listView.setRowFactory(_ -> {
            TableRow<Trabajo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    List<Trabajador> choices = new ArrayList<>(res);

                    ChoiceDialog<Trabajador> dialog = new ChoiceDialog<>(choices.getFirst(), choices);
                    dialog.setTitle("Select worker");
                    dialog.setHeaderText("Select the worker assigned to this task");
                    dialog.setContentText("Choose the worker:");

                    Optional<Trabajador> result = dialog.showAndWait();

                    result.ifPresent(r ->{
                        row.getItem().setIdTrabajador(r);
                        row.setStyle("-fx-background-color:lightgreen");
                        //Si todos los trabajos estan asignados activa el boton de confirmar
                        if (listView.getItems().filtered(t -> t.getIdTrabajador() == null).isEmpty())
                            btnConfirmAssignation.setDisable(false);
                        serviceTrabajo = new Service<>("BASE_URL", "api/trabajo/"+row.getItem().getCodTrabajo(), Trabajo.class);
                        serviceTrabajo.put(row.getItem());
                    });
                }
            });
            return row;
        })));
    }
    @FXML
    protected void btnCleansesDisplay(MouseEvent event){
        if(completableFutureTrabajo !=null && !completableFutureTrabajo.isDone()) completableFutureTrabajo.cancel(true);
        navigateTo(this,"list-cleanses.fxml", event);
    }
    @FXML
    protected void btnWorkerDisplay(MouseEvent event){
        if(completableFutureTrabajo !=null && !completableFutureTrabajo.isDone()) completableFutureTrabajo.cancel(true);
        navigateTo(this,"hello-view.fxml", event);
    }
    @FXML
    protected void btnTasksDisplay(){
        displayList("BASE_URL","api/trabajo");
    }
    private <T> void displayList(String constant,String url) {
        serviceTrabajo = new Service<>(constant, url, Trabajo.class);
        completableFutureTrabajo = serviceTrabajo.getAll();
        completableFutureTrabajo.thenAcceptAsync(res->Platform.runLater(()-> {
            obList.clear();
            obList.addAll(res);
            listView.refresh();
        }));
    }

    public void btnAddJob(MouseEvent event)
    {
        navigateTo(this,"create-job.fxml", event);
    }

    public void btnTasksWithoutWorker(MouseEvent mouseEvent)
    {
        displayList("BASE_URL","api/trabajo/not/join");
    }

    public void btnConfirmAssignation(MouseEvent mouseEvent)
    {
        listView.refresh();
        new Thread(()-> listView.getItems().forEach(item -> {
            final NetHttpTransport HTTP_TRANSPORT =
                    new NetHttpTransport();
            Gmail service;
            try
            {
                service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                        getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();

                String user = "me";
                MimeMessage emailContent = createEmail(item.getIdTrabajador().getEmail(),
                        "marcosfalso2@gmail.com", "New task assigned", "");

                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(
                        "<h1>Assigned task: " + item.getDescripcion() + "</h1>", "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(htmlPart);
                emailContent.setContent(multipart);

                sendMessage(service, user, emailContent);
            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        })).start();
    }
}
