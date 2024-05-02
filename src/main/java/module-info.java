module com.example.taskmanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens com.example.taskmanagement to javafx.fxml;
    exports com.example.taskmanagement;
}