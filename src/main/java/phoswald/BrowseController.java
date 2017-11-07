package phoswald;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BrowseController implements Initializable {

    @FXML
    private TextField urlField;

    @FXML
    private Button upButton;

    @FXML
    private Button goButton;

    @FXML
    private TableView<FileListItem> fileList;

    @FXML
    private TableColumn<FileListItem, String> fileName;

    @FXML
    private TableColumn<FileListItem, String> fileSize;

    @FXML
    private Button closeButton;

    @FXML
    private Button openButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: this should go away, right?
        fileName.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        fileSize.setCellValueFactory(cellData -> cellData.getValue().fileSizeProperty());
        urlField.setText(Paths.get(".").toAbsolutePath().toString());
        upButton.setOnAction(this::onUp);
        goButton.setOnAction(this::onGo);
        fileList.setOnMouseClicked(ev -> {
            String name = fileList.getSelectionModel().getSelectedItem().getName();
            Path url = Paths.get(urlField.getText());
            urlField.setText(url.resolve(name).toString());
            onGo(null);
        });
        onGo(null);
    }

    private void onUp(ActionEvent ev) {
        Path url = Paths.get(urlField.getText());
        urlField.setText(url.getParent().toString());
        onGo(null);
    }

    private void onGo(ActionEvent ev) {
        try {
            System.out.println("---");
            fileList.getItems().clear();
            Path url = Paths.get(urlField.getText());
            Files.list(url).forEach(file -> {
                try {
                    FileListItem item = new FileListItem();
                    item.setName(file.getFileName().toString());
                    item.setSize(Files.readAttributes(file, BasicFileAttributes.class).size());
                    System.out.println(file);
                    fileList.getItems().add(item);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
