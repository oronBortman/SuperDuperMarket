package components.LoadingXMLFileScreen;

import exceptions.*;
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
import metadata.*;
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
    @FXML Label errorLabel;

    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
    private SimpleBooleanProperty isMetadataCollected;
    private SimpleBooleanProperty isActive;
    private SimpleStringProperty errorMessageProperty;

    private Task<Boolean> currentRunningTask;

    private BusinessLogic businessLogic;
    private Stage primaryStage;
    private SimpleStringProperty fileName;
    private SimpleStringProperty taskMessageLabelProperty;

    public LoadingXMLFileController() {
        isActive = new SimpleBooleanProperty(false);
        isMetadataCollected = new SimpleBooleanProperty(false);
        selectedFileProperty = new SimpleStringProperty();
        errorMessageProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
        fileName = new SimpleStringProperty();
        taskMessageLabelProperty = new SimpleStringProperty();
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
        errorLabel.setText("");

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
        errorLabel.setText("");
    }

    @FXML
    public void clearTaskButtonAction() {
        taskMessageLabel.setText("");
        progressPercentLabel.setText("");
        taskProgressBar.setProgress(0);
        errorLabel.setText("");
    }

    @FXML
    public void stopTaskButtonAction() {
        if(getCurrentRunningTask() != null)
        {
            System.out.println("There is current task running");
            cancelCurrentTask();
        }
        else {
            System.out.println("There isn't current task running");

        }
    }

    public void bindTaskToUIComponents(Task<Boolean> aTask, Runnable onFinish) {
        // task message
        taskMessageLabel.textProperty().bind(aTask.messageProperty());

        taskProgressBar.progressProperty().bind(aTask.progressProperty());

        aTask.setOnFailed(e ->
        {
            Throwable ex = aTask.getException();
            if(ex != null)
            {
                if (ex.getClass() == DuplicateItemSerialIDException.class)
                {
                    DuplicateItemSerialIDException duplicateItemSerialIDException = (DuplicateItemSerialIDException) ex;
                    errorLabel.setText("Error: Found duplicate serial key " + duplicateItemSerialIDException.getSerialId() + " of item " + duplicateItemSerialIDException.getName());
                }
                else if (ex.getClass() == DuplicateStoreSerialIDException.class)
                {
                    DuplicateStoreSerialIDException duplicateStoreSerialIDException = (DuplicateStoreSerialIDException) ex;
                    errorLabel.setText("Error: Found duplicate serial key " + duplicateStoreSerialIDException.getSerialId() + " of store" + duplicateStoreSerialIDException.getName());
                }
                else if (ex.getClass() == DuplicateCustomerSerialIDException.class)
                {
                    DuplicateCustomerSerialIDException duplicateCustomerSerialIDException = (DuplicateCustomerSerialIDException) ex;
                    errorLabel.setText("Error: Found duplicate serial key " + duplicateCustomerSerialIDException.getSerialId() + " of customer " + duplicateCustomerSerialIDException.getName());
                }
            }
        });

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
        errorLabel.setText("");
    }

    public void collectMetadata( Runnable onFinish) {

        CollectMetadataTask currentRunningTask = new CollectMetadataTask(fileName.get(), (q) -> onTaskFinished(Optional.ofNullable(onFinish)),  businessLogic);
        setCurrentRunningTask(currentRunningTask);

        Thread t = new Thread(currentRunningTask);
        t.start();
        bindTaskToUIComponents(currentRunningTask, onFinish);
    }

    private static void showError(Thread thread, Throwable throwable) {
        System.out.println("A");

    }



    public void setCurrentRunningTask( CollectMetadataTask currentRunningTask)
    {
        this.currentRunningTask = currentRunningTask;
    }

    public Task<Boolean> getCurrentRunningTask( )
    {
        return this.currentRunningTask;
    }
    public void cancelCurrentTask() {
        currentRunningTask.cancel();
    }


    public SimpleStringProperty fileNameProperty() {
        return this.fileName;
    }

   /* private boolean readShopsFromXMLFile(String xmlFileName, BusinessLogic baseFromXml) {
        boolean readShopsSuccefully;
        try
        {
            baseFromXml.createStoresSerialIDMapFromXml(xmlFileName);
            baseFromXml.createStoresLocationMapFromXml(xmlFileName);
            readShopsSuccefully=true;
        }
        catch (DuplicateSerialIDException e)
        {
            System.out.println("The serial ID " + e.getSerialId() + " of the store " + e.getName() + " is not unique.");
            System.out.println("Please fix your xml file and try again\n");
            readShopsSuccefully=false;
        }
        catch( InvalidCoordinateXException e)
        {
            System.out.println("The coordinate X with value " + e.getCoord() + " of the store " + e.getName() + " is invalid. You need to enter coordinate between 1 to 50.");
            System.out.println("Please fix your xml file and try again\n");
            readShopsSuccefully=false;
        }
        catch( InvalidCoordinateYException e)
        {
            System.out.println("The coordinate Y with value " + e.getCoord() + " of the store " + e.getName() + " is invalid. You need to enter coordinate between 1 to 50.");
            System.out.println("Please fix your xml file and try again\n");
            readShopsSuccefully=false;
        }
        catch(Exception e)
        {
            readShopsSuccefully=false;
        }
        return readShopsSuccefully;
    }

    private boolean readItemsFromXMLFile(BusinessLogic baseFromXml) {
        boolean readItemsSuccessfully;
        try
        {
            baseFromXml.addItemsSerialIDMapFromXml(fileName.getName());
            readItemsSuccessfully=true;
        }
        catch (DuplicateSerialIDException e)
        {
            System.out.println("The serial ID " + e.getSerialId() + " of the item " + e.getName()   + " is not unique" + " in Super Duper Market");
            System.out.println("Please fix your xml file and try again\n");
            readItemsSuccessfully=false;
        }
        catch (Exception e)
        {
            readItemsSuccessfully=false;
        }
        return readItemsSuccessfully;
    }*/
}