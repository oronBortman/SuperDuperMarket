package components.LoadingXMLFileScreen;

import commonUI.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.BusinessLogic;
import logic.metadata.*;
import java.io.File;
import java.util.Optional;

public class LoadingXMLFileController {

    @FXML Button openFileButton;
    @FXML Button  LoadFileButton;
    @FXML Button clearButton;
    @FXML Button stopTaskButton;
    @FXML Button clearTaskButton;
    @FXML Label selectedFileName;
    @FXML ProgressBar taskProgressBar;
    @FXML Label progressPercentLabel;
    @FXML Label taskMessageLabel;
    @FXML FlowPane flowPane;


    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
    private SimpleBooleanProperty isMetadataCollected;
    private SimpleBooleanProperty isActive;

    private BusinessLogic businessLogic;
    private Stage primaryStage;
    private SimpleStringProperty fileName;



    public LoadingXMLFileController() {
        isActive = new SimpleBooleanProperty(false);
        isMetadataCollected = new SimpleBooleanProperty(false);

        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
        fileName = new SimpleStringProperty();
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        fileNameProperty().bind(selectedFileProperty);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() {
        selectedFileName.textProperty().bind(selectedFileProperty);
        LoadFileButton.disableProperty().bind(isFileSelected.not());
        stopTaskButton.disableProperty().bind(isActive.not());
        clearTaskButton.disableProperty().bind(isActive);

    }

    @FXML
   public void LoadFileButtonAction() {
        isActive.set(true);

        collectMetadata(
                () -> {
                    isMetadataCollected.set(true);
                    isActive.set(false);
                }
        );
    }

    @FXML
    public void openFileButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select words file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        selectedFileProperty.set(absolutePath);
        isFileSelected.set(true);
    }

    @FXML
    public void clearButtonAction() {
        selectedFileProperty.set("");
        isFileSelected.set(false);
        isMetadataCollected.set(false);
        clearTaskButtonAction();
        cleanOldResults();
    }

    @FXML
    public void clearTaskButtonAction() {
        taskMessageLabel.setText("");
        progressPercentLabel.setText("");
        taskProgressBar.setProgress(0);
    }

    @FXML
    public void stopTaskButtonAction() {
        if(businessLogic.getCurrentRunningTask() != null)
        {
            System.out.println("There is current task running");
            businessLogic.cancelCurrentTask();
        }
        else {
            System.out.println("There isn't current task running");

        }
    }

    public void bindTaskToUIComponents(Task<Boolean> aTask, Runnable onFinish) {
        // task message
        taskMessageLabel.textProperty().bind(aTask.messageProperty());

        // task progress bar
        taskProgressBar.progressProperty().bind(aTask.progressProperty());

        // task percent label
        progressPercentLabel.textProperty().bind(
                Bindings.concat(
                        Bindings.format(
                                "%.0f",
                                Bindings.multiply(
                                        aTask.progressProperty(),
                                        100)),
                        " %"));

        // task cleanup upon finish
        aTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            onTaskFinished(Optional.ofNullable(onFinish));
        });
    }

    public void onTaskFinished(Optional<Runnable> onFinish) {
        this.taskMessageLabel.textProperty().unbind();
        this.progressPercentLabel.textProperty().unbind();
        this.taskProgressBar.progressProperty().unbind();
        onFinish.ifPresent(Runnable::run);
    }

    private void cleanOldResults() {
        flowPane.getChildren().clear();
        taskProgressBar.setProgress(0);
    }

    public void collectMetadata(Runnable onFinish) {

        CollectMetadataTask currentRunningTask = new CollectMetadataTask(fileName.get(), (q) -> onTaskFinished(Optional.ofNullable(onFinish)));
        businessLogic.setCurrentRunningTask(currentRunningTask);

        bindTaskToUIComponents(currentRunningTask, onFinish);

        new Thread(currentRunningTask).start();
    }


    public SimpleStringProperty fileNameProperty() {
        return this.fileName;
    }
}