module com.example.javacoursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.javacoursework to javafx.fxml;
    exports com.example.javacoursework;
}