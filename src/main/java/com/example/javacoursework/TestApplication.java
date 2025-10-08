package com.example.javacoursework;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TestApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @FXML
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TestApplication.class.getResource("test.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);

        primaryStage.setTitle("Test Stage");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
