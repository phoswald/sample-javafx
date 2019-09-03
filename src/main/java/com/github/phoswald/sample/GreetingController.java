package com.github.phoswald.sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GreetingController implements Initializable {

    @FXML
    private void handleButtonOne(ActionEvent event) {
        System.out.println("You have clicked button ONE!");
        textField.setText("one");
    }

    @FXML
    private Button button2;

    @FXML
    private TextField textField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button2.setOnAction(ev -> System.out.println("You have clicked button TWO: input=" + textField.getText()));
    }
}
