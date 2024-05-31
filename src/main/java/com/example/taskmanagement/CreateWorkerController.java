package com.example.taskmanagement;

import com.example.taskmanagement.models.Trabajador;
import com.example.taskmanagement.models.Trabajo;
import com.example.taskmanagement.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import net.synedra.validatorfx.Check.Context;
import net.synedra.validatorfx.Validator;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.taskmanagement.utils.NavigationUtilities.navigateTo;

public class CreateWorkerController implements Initializable
{
    @FXML
    private TextField txtFieldId;
    @FXML
    private TextField txtFieldDni;
    @FXML
    private TextField txtFieldEspecialidad;
    @FXML
    private TextField txtFieldNombre;
    @FXML
    private TextField txtFieldApellidos;
    @FXML
    private TextField txtFieldEmail;
    private final Service<Trabajador> service = new Service<Trabajador>("BASE_URL", "api/trabajador", Trabajador.class);
    private final Validator validator = new Validator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        validator.createCheck()
                .dependsOn("text", txtFieldId.textProperty())
                .withMethod(c -> this.maxLentgh(c, 100))
                .withMethod(this::required)
                .decorates(txtFieldId)
                .immediate();
        validator.createCheck()
                .dependsOn("text", txtFieldDni.textProperty())
                .withMethod(this::dni)
                .withMethod(this::required)
                .decorates(txtFieldDni)
                .immediate();
        validator.createCheck()
                .dependsOn("text", txtFieldEspecialidad.textProperty())
                .withMethod(c -> this.maxLentgh(c, 100))
                .decorates(txtFieldEspecialidad)
                .immediate();
        validator.createCheck()
                .dependsOn("text", txtFieldNombre.textProperty())
                .withMethod(this::required)
                .withMethod(c -> this.maxLentgh(c, 50))
                .decorates(txtFieldNombre)
                .immediate();
        validator.createCheck()
                .dependsOn("text", txtFieldApellidos.textProperty())
                .withMethod(this::required)
                .withMethod(c -> this.maxLentgh(c, 100))
                .decorates(txtFieldApellidos)
                .immediate();
        validator.createCheck()
                .dependsOn("text", txtFieldEmail.textProperty())
                .withMethod(c -> this.maxLentgh(c, 255))
                .withMethod(this::required)
                .decorates(txtFieldEmail)
                .immediate();
    }

    public void btnGoBack(MouseEvent event)
    {
        navigateTo(this, "hello-view.fxml", event);
    }

    public void btnCreateJob(MouseEvent mouseEvent)
    {
        if (!validator.containsErrors())
        {
            Trabajador trabajador = new Trabajador();
            trabajador.setIdTrabajador(txtFieldId.getText());
            trabajador.setEspecialidad(txtFieldEspecialidad.getText());
            trabajador.setEmail(txtFieldEmail.getText());
            trabajador.setApellidos(txtFieldApellidos.getText());
            trabajador.setNombre(txtFieldNombre.getText());
            trabajador.setDni(txtFieldDni.getText());
            trabajador.setContrasenya("password1234");
            service.post(trabajador);
            new Alert(Alert.AlertType.INFORMATION, "Worker created successfully").show();
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

    private void dni(Context context) {
        Pattern pattern = Pattern.compile("^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$");
        Matcher matcher = pattern.matcher(context.get("text"));
        if (!matcher.matches())
            context.error("The DNI needs 8 numbers and 1 letter");
    }
}
