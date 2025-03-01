package com.github.phoswald.sample.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.phoswald.sample.Application;
import com.github.phoswald.sample.ApplicationModule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class HomeController implements Initializable {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Application application = ApplicationModule.instance().getApplication();

    @FXML
    private Button homeButton;

    @FXML
    private Button sampleButton;

    @FXML
    private Button tasksButton;

    @FXML
    private Button canvasButton;

    @FXML
    private Button webButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(this::onHome);
        sampleButton.setOnAction(this::onSample);
        tasksButton.setOnAction(this::onTasks);
        canvasButton.setOnAction(this::onCanvas);
        webButton.setOnAction(this::onWeb);
    }

    private void onHome(ActionEvent event) {
        logger.info("Home button fired.");
        application.updateScene("/fxml/home.fxml");
    }

    private void onSample(ActionEvent event) {
        logger.info("Sample button fired.");
        application.updateScene("/fxml/sample.fxml");
    }

    private void onTasks(ActionEvent event) {
        logger.info("Tasks button fired.");
        application.updateScene("/fxml/task-list.fxml");
    }

    private void onCanvas(ActionEvent event) {
        logger.info("Canvas button fired.");
        application.updateScene("/fxml/canvas.fxml");
    }

    private void onWeb(ActionEvent event) {
        logger.info("Web button fired.");
        application.updateScene("/fxml/web.fxml");
    }
}
