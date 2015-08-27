package phoswald;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


public class MyController implements Initializable {

    @FXML
    private void handleButtonOne(ActionEvent event) {
        System.out.println("You clicked me (one)!");
    }

    @FXML
    private Button button2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button2.setOnAction(ev -> System.out.println("You clicked me (two)!"));
    }
}
