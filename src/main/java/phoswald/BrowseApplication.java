package phoswald;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BrowseApplication extends Application
{
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Open File");
        Pane root = (Pane) FXMLLoader.load(getClass().getResource("browse.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
