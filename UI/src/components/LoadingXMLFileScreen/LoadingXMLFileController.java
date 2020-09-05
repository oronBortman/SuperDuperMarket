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



    //private SimpleLongProperty totalWords;
   // private SimpleLongProperty totalLines;
   // private SimpleIntegerProperty totalDistinctWords;
   //private SimpleIntegerProperty totalProcessedWords;
    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
   // private SimpleBooleanProperty isMetadataCollected;

    private BusinessLogic businessLogic;
    private Stage primaryStage;
    //private Map<String, SingleWordController> wordToTileController;


    public LoadingXMLFileController() {
        /*totalWords = new SimpleLongProperty(0);
        totalLines = new SimpleLongProperty(0);
        totalDistinctWords = new SimpleIntegerProperty(0);
        totalProcessedWords = new SimpleIntegerProperty(0);

        isMetadataCollected = new SimpleBooleanProperty(false);
        wordToTileController = new HashMap<>();*/
        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        //businessLogic.fileNameProperty().bind(selectedFileProperty);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() {
        /*totalWordsLabel.textProperty().bind(Bindings.format("%,d", totalWords));
        totalLinesLabel.textProperty().bind(Bindings.format("%,d", totalLines));
        distinctWordsSoFar.textProperty().bind(Bindings.format("%,d", totalDistinctWords));
        totalCurrentProcessedWords.textProperty().bind(Bindings.format("%,d", totalProcessedWords));*/
        selectedFileName.textProperty().bind(selectedFileProperty);
        LoadFileButton.disableProperty().bind(isFileSelected.not());
        //calculateHistogramButton.disableProperty().bind(isMetadataCollected.not());
    }

    @FXML
    public void calculateHistogramAction() {
        cleanOldResults();
        //UIAdapter uiAdapter = createUIAdapter();

        toggleTaskButtons(true);

        //businessLogic.calculateHistogram(uiAdapter, () -> toggleTaskButtons(false));
    }

    @FXML
   public void LoadFileButtonAction() {
        toggleTaskButtons(true);

       /* businessLogic.collectMetadata(
                totalWords::set,
               // totalLines::set,
                () -> {
                    isMetadataCollected.set(true);
                    toggleTaskButtons(false);
                }
        );*/
    }

    @FXML
    public void openFileButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select words file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.xml"));
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
        //isMetadataCollected.set(false);
       // totalWords.set(0);
        //totalLines.set(0);
        clearTaskButtonAction();
        cleanOldResults();
    }

    @FXML
    public void clearTaskButtonAction() {
       // taskMessageLabel.setText("");
        progressPercentLabel.setText("");
        taskProgressBar.setProgress(0);
    }

    @FXML
    public void stopTaskButtonAction() {
       // businessLogic.cancelCurrentTask();
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

    private UIAdapter createUIAdapter() {
        return new UIAdapter(
               /* histogramData -> {
                    HistogramsUtils.log("EDT: CREATE new tile for [" + histogramData.toString() + "]");
                    createTile(histogramData.getWord(), histogramData.getCount());
                },

                histogramData -> {
                    HistogramsUtils.log("EDT: UPDATE tile for [" + histogramData.toString() + "]");
                    SingleWordController singleWordController = wordToTileController.get(histogramData.getWord());
                    if (singleWordController != null && singleWordController.getCount() != histogramData.getCount()) {
                        singleWordController.setCount(histogramData.getCount());
                    } else {
                        HistogramsUtils.log("ERROR ! Can't find tile for [" + histogramData.getWord() + "] with count " + histogramData.getCount());
                    }
                },*/
               /* () -> {
                    SuperDuperMarketUtils.log("EDT: INCREASE total distinct words");
                    totalDistinctWords.set(totalDistinctWords.get() + 1);
                },*/
                (delta) -> {
                    SuperDuperMarketUtils.log("EDT: INCREASE total processed words");
                    //TODO
                    //totalProcessedWords.set(totalProcessedWords.get() + delta);
                }
        );
    }

    private void cleanOldResults() {
       // wordToTileController.clear();
        flowPane.getChildren().clear();
        taskProgressBar.setProgress(0);
        //totalDistinctWords.set(0);
        //totalProcessedWords.set(0);
    }

   /* private void createTile(String word, int count) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HistogramResourcesConstants.MAIN_FXML_RESOURCE);
            Node singleWordTile = loader.load();

            SingleWordController singleWordController = loader.getController();
            singleWordController.setCount(count);
            singleWordController.setWord(word);

            histogramFlowPane.getChildren().add(singleWordTile);
            wordToTileController.put(word, singleWordController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private void toggleTaskButtons(boolean isActive) {
        stopTaskButton.setDisable(!isActive);
        clearTaskButton.setDisable(isActive);
    }
}