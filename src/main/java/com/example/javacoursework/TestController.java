package com.example.javacoursework;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TestController {
    @FXML
    public Text testTeLabel;
    public Pane testContainer;
    public Button userButton;

    public void accessUser(){
        testTeLabel.setText("Veikia");
    }
}
