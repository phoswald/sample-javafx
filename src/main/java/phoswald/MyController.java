package phoswald;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


public class MyController implements Initializable {

    public void handleTheButton(ActionEvent event) {
        System.out.println("You clicked me (1)!");
    }

    public Button button2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button2.setOnAction(ev -> System.out.println("You clicked me (2)!"));
    }
}
