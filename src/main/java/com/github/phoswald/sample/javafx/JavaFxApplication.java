package com.github.phoswald.sample.javafx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.phoswald.sample.ConfigProvider;
import com.github.phoswald.sample.task.TaskRepository;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class JavaFxApplication extends Application {

    private static final Logger logger = LogManager.getLogger();
    private static final ConfigProvider config = new ConfigProvider();
    private static final EntityManagerFactory emf = createEntityManagerFactory();

    private static Scene scene;

    public static void main(String[] args) {
        logger.info("sample-javafx is starting");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Open File");
        Pane root = (Pane) FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        scene = primaryStage.getScene();
    }

    public static void updateScene(String resouceName) {
        try {
            Pane root = (Pane) FXMLLoader.load(JavaFxApplication.class.getResource(resouceName));
            scene.setRoot(root);
        } catch (IOException e) {
            logger.catching(e);
        }
    }

    public static TaskRepository createTaskRepository() {
        return new TaskRepository(emf);
    }

    private static EntityManagerFactory createEntityManagerFactory() {
        Map<String, String> props = new HashMap<>();
        config.getConfigProperty("app.jdbc.driver"  ).ifPresent(v -> props.put("javax.persistence.jdbc.driver", v));
        config.getConfigProperty("app.jdbc.url"     ).ifPresent(v -> props.put("javax.persistence.jdbc.url", v));
        config.getConfigProperty("app.jdbc.username").ifPresent(v -> props.put("javax.persistence.jdbc.user", v));
        config.getConfigProperty("app.jdbc.password").ifPresent(v -> props.put("javax.persistence.jdbc.password", v));
        return Persistence.createEntityManagerFactory("taskDS", props);
    }
}
