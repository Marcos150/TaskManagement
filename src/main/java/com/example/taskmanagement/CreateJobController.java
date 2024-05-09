package com.example.taskmanagement;

import com.example.taskmanagement.models.Trabajo;
import com.example.taskmanagement.service.Service;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import static com.example.taskmanagement.utils.NavigationUtilities.navigateTo;

public class CreateJobController
{
    @FXML
    private TextField txtFieldDescripcion;
    @FXML
    private TextField txtFieldCategoria;
    @FXML
    private TextField txtFieldTrabajador;
    private final Service<Trabajo> service = new Service<Trabajo>("BASE_URL", "api/trabajo", Trabajo.class);

    public void btnGoBack(MouseEvent event)
    {
        navigateTo(this,"list-jobs.fxml", event);
    }

    public void btnCreateJob(MouseEvent mouseEvent)
    {
        Trabajo trabajo = new Trabajo();
        trabajo.setDescripcion(txtFieldDescripcion.getText());
        trabajo.setCategoria(txtFieldCategoria.getText());
        trabajo.setFecIni("1970-01-01");
        trabajo.setPrioridad(1);
        trabajo.setCodTrabajo(String.valueOf(System.currentTimeMillis()).substring(0, 5));
        //TODO: Asignar trabajador al trabajo
        service.post(trabajo);
    }
}
