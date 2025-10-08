module com.example.javacoursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires javafx.graphics;

    opens com.example.javacoursework to javafx.fxml;
    exports com.example.javacoursework;
}