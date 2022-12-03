package com.github.phoswald.sample;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    private static final Logger logger = LogManager.getLogger();

    private Scene scene;

    public static void main(String[] args) {
        logger.info("sample-javafx is starting");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ApplicationModule.setup(new ApplicationModule() { }, this);

        Pane root = (Pane) FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        primaryStage.setTitle("sample-javafx");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        scene = primaryStage.getScene();
    }

    public void updateScene(String resourceName) {
        try {
            Pane root = (Pane) FXMLLoader.load(getClass().getResource(resourceName));
            scene.setRoot(root);
        } catch (IOException e) {
            logger.catching(e);
        }
    }
}
