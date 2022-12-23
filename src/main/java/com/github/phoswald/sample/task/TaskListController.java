package com.github.phoswald.sample.task;

import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Supplier;

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

public class TaskListController implements Initializable {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Application application = ApplicationModule.instance().getApplication();
    private final Supplier<TaskRepository> repositoryFactory = ApplicationModule.instance().getTaskRepositoryFactory();

    @FXML
    private Button homeButton;

    @FXML
    private TableView<TaskEntity> taskList;

    @FXML
    private TableColumn<TaskEntity, String> taskDone;

    @FXML
    private TableColumn<TaskEntity, String> taskTitle;

    @FXML
    private TableColumn<TaskEntity, String> taskUpdated;

    @FXML
    private Button addButton;

    @FXML
    private TextField addTitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(this::onHome);
        addButton.setOnAction(this::onAdd);
        taskDone.setCellValueFactory(createCellValueFactory(TaskEntity::isDone));
        taskTitle.setCellValueFactory(createCellValueFactory(TaskEntity::getTitle));
        taskUpdated.setCellValueFactory(createCellValueFactory(TaskEntity::getTimestamp));
        try(TaskRepository repository = repositoryFactory.get()) {
            logger.info("Loading all tasks");
            List<TaskEntity> entities = repository.selectAllTasks();
            logger.info("Found {} tasks", entities.size());
            taskList.getItems().addAll(entities);
        } catch (RuntimeException e) {
            logger.error("Catching:", e);
        }
    }

    private void onHome(ActionEvent event) {
        logger.info("Home button fired.");
        application.updateScene("/fxml/home.fxml");
    }

    private void onAdd(ActionEvent event) {
        logger.info("Add button fired: {}", addTitle.getText());
        try(TaskRepository repository = repositoryFactory.get()) {
            TaskEntity entity = new TaskEntity();
            entity.setNewTaskId();
            entity.setUserId("guest");
            entity.setTimestamp(Instant.now());
            entity.setTitle(addTitle.getText());
            entity.setDescription("n/a");
            entity.setDone(false);
            repository.createTask(entity);
            taskList.getItems().add(entity);
        } catch (RuntimeException e) {
            logger.error("Catching:", e);
        }
    }

    private <S> Callback<CellDataFeatures<S, String>, ObservableValue<String>> createCellValueFactory( //
            Function<S, Object> getter) {
        return cellData -> new SimpleStringProperty(getter.apply(cellData.getValue()).toString());
    }
}
