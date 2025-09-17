module com.example.javacoursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens com.example.javacoursework to javafx.fxml;
    exports com.example.javacoursework;
}