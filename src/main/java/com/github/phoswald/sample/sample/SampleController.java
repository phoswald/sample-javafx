package com.github.phoswald.sample.sample;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.phoswald.sample.Application;
import com.github.phoswald.sample.ApplicationModule;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class SampleController implements Initializable {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Application application = ApplicationModule.instance().getApplication();

    @FXML
    private Button homeButton;

    @FXML
    private TextField greeting;

    @FXML
    private TextField sampleConfig;

    @FXML
    private TableView<Map.Entry<?, ?>> envVars;

    @FXML
    private TableColumn<Map.Entry<?, ?>, String> envVarName;

    @FXML
    private TableColumn<Map.Entry<?, ?>, String> envVarValue;

    @FXML
    private TableView<Map.Entry<?, ?>> sysProps;

    @FXML
    private TableColumn<Map.Entry<?, ?>, String> sysPropName;

    @FXML
    private TableColumn<Map.Entry<?, ?>, String> sysPropValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(this::onHome);
        greeting.setText("Hello, world!");
        sampleConfig.setText("Blubber");
        envVars.getItems().addAll(new TreeMap<>(System.getenv()).entrySet());
        envVarName.setCellValueFactory(createCellValueFactory(Map.Entry::getKey));
        envVarValue.setCellValueFactory(createCellValueFactory(Map.Entry::getValue));
        sysProps.getItems().addAll(new TreeMap<>(System.getProperties()).entrySet());
        sysPropName.setCellValueFactory(createCellValueFactory(Map.Entry::getKey));
        sysPropValue.setCellValueFactory(createCellValueFactory(Map.Entry::getValue));
    }

    private void onHome(ActionEvent event) {
        logger.info("Home button fired.");
        application.updateScene("/fxml/home.fxml");
    }

    private <S> Callback<CellDataFeatures<S, String>, ObservableValue<String>> createCellValueFactory( //
            Function<S, Object> getter) {
        return cellData -> new SimpleStringProperty(getter.apply(cellData.getValue()).toString());
    }
}
