package com.example.taskmanagement;

import javafx.scene.input.MouseEvent;
import static com.example.taskmanagement.utils.NavigationUtilities.navigateTo;

public class CreateJobController
{
    public void btnGoBack(MouseEvent event)
    {
        navigateTo(this,"list-jobs.fxml", event);
    }
}
