package com.github.phoswald.sample.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class HomeController implements Initializable {

    private static final Logger logger = LogManager.getLogger();

    @FXML
    private Button homeButton;

    @FXML
    private Button sampleButton;

    @FXML
    private Button tasksButton;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(this::onHome);
        sampleButton.setOnAction(this::onSample);
        tasksButton.setOnAction(this::onTasks);
    }
    
    private void onHome(ActionEvent event) {
        logger.info("Home button fired.");
        JavaFxApplication.updateScene("/fxml/home.fxml");
    }
    
    private void onSample(ActionEvent event) {
        logger.info("Sample button fired.");
        JavaFxApplication.updateScene("/fxml/sample.fxml");
    }
    
    private void onTasks(ActionEvent event) {
        logger.info("Tasks button fired.");
        JavaFxApplication.updateScene("/fxml/task-list.fxml");
    }
}
