package phoswald;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MarkupApplication extends Application
{
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("I am the title of the stage.");
        Pane root = (Pane) FXMLLoader.load(getClass().getResource("greeting.fxml"));
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
