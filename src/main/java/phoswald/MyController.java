package phoswald;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class MyController implements Initializable {

    @FXML
    private void handleButtonOne(ActionEvent event) {
        System.out.println("You clicked me (one)!");
        textField.setText("XXX");
    }

    @FXML
    private Button button2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button2.setOnAction(ev -> System.out.println("You clicked me (two):" + textField.getText()));
    }

    @FXML
    private TextField textField;
}
