module com.example.javacoursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming;
    requires jakarta.persistence;
    requires org.controlsfx.controls;
    requires mysql.connector.j;
    requires javafx.graphics;

    opens com.example.javacoursework to javafx.fxml, org.hibernate.orm.core, jakarta.persistence;
    exports com.example.javacoursework;
    opens com.example.javacoursework.fxcontrollers to javafx.fxml;
    exports com.example.javacoursework.fxcontrollers;
    opens com.example.javacoursework.model to org.hibernate.orm.core;
    exports com.example.javacoursework.model;
}