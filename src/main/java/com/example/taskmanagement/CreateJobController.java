package com.example.taskmanagement;

import com.example.taskmanagement.models.Trabajo;
import com.example.taskmanagement.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import net.synedra.validatorfx.Validator;

import net.synedra.validatorfx.Check.Context;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.taskmanagement.utils.NavigationUtilities.navigateTo;

public class CreateJobController implements Initializable
{
    @FXML
    private TextField txtFieldCodTrabajo;
    @FXML
    private TextField txtFieldDescripcion;
    @FXML
    private TextField txtFieldCategoria;
    @FXML
    private TextField txtFieldTrabajador;
    private final Service<Trabajo> service = new Service<Trabajo>("BASE_URL", "api/trabajo", Trabajo.class);
    private final Validator validator = new Validator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        validator.createCheck()
                .dependsOn("text", txtFieldCodTrabajo.textProperty())
                .withMethod(c -> this.maxLentgh(c, 5))
                .withMethod(this::required)
                .decorates(txtFieldCodTrabajo)
                .immediate();
        validator.createCheck()
                .dependsOn("text", txtFieldDescripcion.textProperty())
                .withMethod(this::required)
                .withMethod(c -> this.maxLentgh(c, 500))
                .decorates(txtFieldDescripcion)
                .immediate();
        validator.createCheck()
                .dependsOn("text", txtFieldCategoria.textProperty())
                .withMethod(this::required)
                .withMethod(c -> this.maxLentgh(c, 50))
                .decorates(txtFieldCategoria)
                .immediate();
        validator.createCheck()
                .dependsOn("text", txtFieldTrabajador.textProperty())
                .withMethod(c -> this.maxLentgh(c, 5))
                .decorates(txtFieldTrabajador)
                .immediate();
    }

    public void btnGoBack(MouseEvent event)
    {
        navigateTo(this, "list-jobs.fxml", event);
    }

    public void btnCreateJob(MouseEvent mouseEvent)
    {
        if (!validator.containsErrors())
        {
            Trabajo trabajo = new Trabajo();
            trabajo.setDescripcion(txtFieldDescripcion.getText());
            trabajo.setCategoria(txtFieldCategoria.getText());
            trabajo.setFecIni("1970-01-01");
            trabajo.setPrioridad(1);
            trabajo.setCodTrabajo(txtFieldCodTrabajo.getText());
            //TODO: Asignar trabajador al trabajo
            service.post(trabajo);
            //TODO: Comprobar que realmente se ha creado con la respuesta de la api
            new Alert(Alert.AlertType.INFORMATION, "Task created successfully").show();
        }
        else
            new Alert(Alert.AlertType.ERROR, "Some of the fields are incorrect").show();
    }

    private void required(Context context) {
        String text = context.get("text");
        if (text == null || text.isEmpty()) {
            context.error("This field is required.");
        }
    }

    private void maxLentgh(Context context, int maxLentgh) {
        String text = context.get("text");
        if (text.length() > maxLentgh) {
            context.error("This field has to be " + maxLentgh + " or less characters long.");
        }
    }
}
