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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

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
    private boolean isShowingAll = true;

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
                if (event.getClickCount() == 1 && !isShowingAll) {
                    Dialog<Trabajo> dialog = new Dialog<>();
                    dialog.setTitle("Edit task");
                    dialog.setHeaderText("Edit");
                    dialog.setContentText("Edit task details");

                    // Set the button types.
                    ButtonType loginButtonType = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

                    // Create the username and password labels and fields.
                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setPadding(new Insets(20, 150, 10, 10));

                    TextField category = new TextField();
                    category.setPromptText(row.getItem().getCategoria());
                    category.setText(row.getItem().getCategoria());

                    TextField description = new TextField();
                    description.setPromptText(row.getItem().getDescripcion());
                    description.setText(row.getItem().getDescripcion());

                    TextField priority = new TextField();
                    priority.setPromptText(String.valueOf(row.getItem().getPrioridad()));
                    priority.setText(String.valueOf(row.getItem().getPrioridad()));

                    grid.add(new Label("Category:"), 0, 0);
                    grid.add(category, 1, 0);
                    grid.add(new Label("Description:"), 0, 1);
                    grid.add(description, 1, 1);
                    grid.add(new Label("Priority:"), 0, 2);
                    grid.add(priority, 1, 2);

                    dialog.getDialogPane().setContent(grid);

                    Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                    priority.textProperty().addListener((observable, oldValue, newValue) -> {
                        loginButton.setDisable(!newValue.matches("-?\\d+(\\.\\d+)?"));
                    });

                    // Convert the result to a username-password-pair when the login button is clicked.
                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == loginButtonType) {
                            try
                            {
                                serviceTrabajo = new Service<>("BASE_URL", "api/trabajo/" + row.getItem().getCodTrabajo(), Trabajo.class);
                                row.getItem().setDescripcion(description.getText());
                                row.getItem().setCategoria(category.getText());
                                row.getItem().setPrioridad(Integer.parseInt(priority.getText()));
                                serviceTrabajo.put(row.getItem());
                            } catch (Exception e)
                            {
                                new Alert(Alert.AlertType.ERROR, "Some of the fields were incorrect", ButtonType.OK).show();
                            }
                        }
                        return null;
                    });

                    dialog.showAndWait();
                }
                else if (event.getClickCount() == 2) {
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
        isShowingAll = true;
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
        isShowingAll = false;
        displayList("BASE_URL","api/trabajo/not/join");
    }

    public void btnConfirmAssignation(MouseEvent mouseEvent)
    {
        btnConfirmAssignation.setDisable(true);
        new Thread(()->{ listView.getItems().forEach(item -> {
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
        });
            btnTasksWithoutWorker(mouseEvent);
        }).start();

    }
}
