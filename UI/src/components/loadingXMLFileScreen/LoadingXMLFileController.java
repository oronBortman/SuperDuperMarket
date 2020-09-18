package components.loadingXMLFileScreen;

import commonUI.SuperDuperMarketUtils;
import exceptions.*;
import exceptions.InvalidCoordinateException.*;
import exceptions.duplicateSerialID.*;
import exceptions.locationsIdentialException.*;
import exceptions.notExistException.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.*;
import metadata.*;
import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

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
    @FXML Label errorLabel;

    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
    private SimpleBooleanProperty isMetadataCollected;
    private SimpleBooleanProperty isActive;

    private Task<Boolean> currentRunningTask;

    private BusinessLogic businessLogicFromXML;
    private Stage primaryStage;
    private SimpleStringProperty fileName;

    Consumer<Boolean> loadBusinessLogicSuccessfully;


    public LoadingXMLFileController() {
        isActive = new SimpleBooleanProperty(false);
        isMetadataCollected = new SimpleBooleanProperty(false);
        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
        fileName = new SimpleStringProperty();
    }

    public void setProperties(Consumer<Boolean> loadBusinessLogicSuccessfully)
    {
        this.loadBusinessLogicSuccessfully = loadBusinessLogicSuccessfully;
    }


    /*public void setBusinessLogic(BusinessLogic businessLogic) {
        //this.businessLogicThatExist = businessLogic;
        //this.businessLogic = businessLogic;
    }
*/
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public BusinessLogic getBusinessLogicFromXML()
    {
        return businessLogicFromXML;
    }
    @FXML
    private void initialize() {
        selectedFileName.textProperty().bind(selectedFileProperty);
        LoadFileButton.disableProperty().bind(isFileSelected.not());
        stopTaskButton.disableProperty().bind(isActive.not());
        clearTaskButton.disableProperty().bind(isActive);
        fileNameProperty().bind(selectedFileProperty);

    }

    @FXML
   public void LoadFileButtonAction() {
        this.businessLogicFromXML = new BusinessLogic();
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

        aTask.setOnSucceeded(e ->
        {
            loadBusinessLogicSuccessfully.accept(true);
        });
        aTask.setOnFailed(e ->
        {
            Throwable ex = aTask.getException();
            if(ex != null)
            {
                if (ex.getClass() == DuplicateItemSerialIDException.class)
                {
                    printErrorOnDuplicateSerialID("item", (DuplicateSerialIDExceptionInSDM)ex);
                }
                else if (ex.getClass() == DuplicateStoreSerialIDException.class)
                {
                    printErrorOnDuplicateSerialID("store", (DuplicateSerialIDExceptionInSDM)ex);

                }
                else if (ex.getClass() == DuplicateCustomerSerialIDException.class)
                {
                    printErrorOnDuplicateSerialID("customer", (DuplicateSerialIDExceptionInSDM)ex);
                }
                else if (ex.getClass() == DuplicateItemSerialIDInStoreException.class)
                {
                    DuplicateItemSerialIDInStoreException duplicateItemSerialIDInStoreException = (DuplicateItemSerialIDInStoreException) ex;
                    int itemID = duplicateItemSerialIDInStoreException.getItemSerialId();
                    String storeName = duplicateItemSerialIDInStoreException.getStoreName();
                    int storeID = duplicateItemSerialIDInStoreException.getStoreSerialId();
                    errorLabel.setText("Error: Found duplicate serial key " + itemID + " in store " + storeName);
                }
                else if (ex.getClass() == ItemWithSerialIDNotExistInSDMException.class)
                {
                    ItemWithSerialIDNotExistInSDMException itemWithSerialIDNotExistInSDMException = (ItemWithSerialIDNotExistInSDMException) ex;
                    int itemID = itemWithSerialIDNotExistInSDMException.getSerialId();
                    errorLabel.setText("Error: Item with serial key " + itemID + " doesn't exist in Super Duper Market");
                }
                else if (ex.getClass() == StoreNotExistException.class)
                {
                    StoreNotExistException StoreNotExistException = (StoreNotExistException) ex;
                    int storeID = StoreNotExistException.getSerialId();
                    errorLabel.setText("Error: Store with serial key " + storeID + " doesn't exist in Super Duper Market");
                }
                else if (ex.getClass() == ItemNotExistInStoresException.class)
                {
                    ItemNotExistInStoresException itemNotExistInStoresException = (ItemNotExistInStoresException) ex;
                    Item item = itemNotExistInStoresException.getItem();
                    if(item != null)
                    {
                        int itemID = item.getSerialNumber();
                        String itemName = item.getName();
                        errorLabel.setText("Error: The item with the name " + itemName + " and serial key " + itemID + " doesn't exist in any store in Super Duper Market");
                    }
                    else
                    {
                        int itemID = item.getSerialNumber();
                        errorLabel.setText("Error: The item with the serial key " + itemID + " doesn't exist in any store in Super Duper Market");
                    }

                }
                else if (ex.getClass() == ItemIDNotExistInAStoreException.class)
                {
                    ItemIDNotExistInAStoreException itemIDNotExistInAStoreException = (ItemIDNotExistInAStoreException) ex;
                    int itemID = itemIDNotExistInAStoreException.getSerialId();
                    Store store = itemIDNotExistInAStoreException.getStore();
                    errorLabel.setText("Error: The item with the serial key " + itemID + " doesn't exist in the store with name " + store.getName() + " and serial id " + store.getSerialNumber());
                }
                else if (ex.getClass() == DuplicateDiscountNameException.class)
                {
                    DuplicateDiscountNameException duplicateDiscountNameException = (DuplicateDiscountNameException) ex;
                    String discountName = duplicateDiscountNameException.getDiscountName();
                    Store store = duplicateDiscountNameException.getStore();
                    errorLabel.setText("Error: A discount with the name " + discountName + " already exists in the store with the name "  + store.getName() + " and serial ID " + store.getSerialNumber());
                }
                else if(ex.getClass() == CustomerLocationIsIdenticalToCustomerException.class)
                {
                    printErrorOnIdentialLocations("customer", "customer", (IdentialLocationException) ex);
                }
                else if(ex.getClass() == CustomerLocationIsIdenticalToStoreException.class)
                {
                    printErrorOnIdentialLocations("customer", "store", (IdentialLocationException) ex);

                }
                else if(ex.getClass() == StoreLocationIsIdenticalToStoreException.class)
                {
                    printErrorOnIdentialLocations("store", "store", (IdentialLocationException) ex);

                }
                else if(ex.getClass() == StoreLocationIsIdenticalToCustomerException.class)
                {
                    printErrorOnIdentialLocations("store", "customer", (IdentialLocationException) ex);
                }
                else if(ex instanceof InvalidCoordinateXOfCustomerException)
                {
                    printErrorOnInvalidCoordinate("customer", "x", (InvalidCoordinateXOfCustomerException) ex);
                }
                else if(ex instanceof InvalidCoordinateYOfCustomerException)
                {
                    printErrorOnInvalidCoordinate("customer", "y", (InvalidCoordinateYOfCustomerException) ex);

                }
                else if(ex instanceof InvalidCoordinateXOfStoreException)
                {
                    printErrorOnInvalidCoordinate("store", "x", (InvalidCoordinateXOfStoreException) ex);

                }
                else if(ex instanceof InvalidCoordinateYOfStoreException)
                {
                    printErrorOnInvalidCoordinate("store", "y", (InvalidCoordinateYOfStoreException) ex);

                }
                else if(ex instanceof ItemIDInDiscountNotExistInSDMException)
                {
                    ItemIDInDiscountNotExistInSDMException itemIDInDiscountNotExistInSDMException = (ItemIDInDiscountNotExistInSDMException)ex;
                    Store store = itemIDInDiscountNotExistInSDMException.getStore();
                    String discountName = itemIDInDiscountNotExistInSDMException.getDiscountName();
                    Integer serialIdOfItem = itemIDInDiscountNotExistInSDMException.getSerialIdOfItem();
                    errorLabel.setText("Error: serial id of item " + serialIdOfItem + " in discount \"" + discountName + "\" in store " + store.getName() + " with serial id " + store.getSerialNumber() + " doesn't exist in Super Duper Market");

                }
                else if(ex instanceof ItemIDInDiscountNotExistInAStoreException)
                {
                    ItemIDInDiscountNotExistInAStoreException itemIDInDiscountNotExistInAStoreException = (ItemIDInDiscountNotExistInAStoreException)ex;
                    Store store = itemIDInDiscountNotExistInAStoreException.getStore();
                    String discountName = itemIDInDiscountNotExistInAStoreException.getDiscountName();
                    Integer serialIdOfItem = itemIDInDiscountNotExistInAStoreException.getSerialIdOfItem();

                    errorLabel.setText("Error: serial id of item " + serialIdOfItem + " in discount \"" + discountName + "\" in store with the name " + store.getName() + " and the serial id " + store.getSerialNumber() + " doesn't exist in the store");
                }

                SuperDuperMarketUtils.sleepForAWhile(100);
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

    public void printErrorOnDuplicateSerialID(String objectType, DuplicateSerialIDExceptionInSDM duplicateSerialIDExceptionInSDM)
    {
        Integer serialID = duplicateSerialIDExceptionInSDM.getSerialId();
        String name = duplicateSerialIDExceptionInSDM.getName();
        errorLabel.setText("Error: Found duplicate serial key " + serialID + " of " + objectType + " " + name);
    }

    public void printErrorOnIdentialLocations(String firstObjectType, String secondObjectType, IdentialLocationException identialLocationException)
    {
        SDMObjectWithUniqueLocationAndUniqueSerialID firstObject = identialLocationException.getFirstObject();
        SDMObjectWithUniqueLocationAndUniqueSerialID secondObject = identialLocationException.getSecondObject();

        SDMLocation location = firstObject.getLocation();
        String locationStr = "(" + location.getX() + "," + location.getY() +")";
        errorLabel.setText("Error: The location " + locationStr + " of the " + firstObjectType + " with name " + firstObject.getName() + " and serial ID: " + firstObject.getSerialNumber() +
                "\nis identical to the location of the " + secondObjectType + " with name " + secondObject.getName() + " and serial ID: " + secondObject.getSerialNumber());
    }

    public void printErrorOnInvalidCoordinate(String objectName, String coordinateType, InvalidCoordinateException invalidCoordinateException)
    {
        String name = invalidCoordinateException.getName();
        Integer serialID = invalidCoordinateException.getSerialID();
        Integer coordinate = invalidCoordinateException.getCoord();
        errorLabel.setText("Error: The coordinate " + objectName + " with value " +  coordinate + " of the location of the " + objectName + " with name " + name + " and serial ID: " + serialID + " is invalid");
    }

    public void onTaskFinished(Optional<Runnable> onFinish) {
        this.taskMessageLabel.textProperty().unbind();
        this.progressPercentLabel.textProperty().unbind();
        this.taskProgressBar.progressProperty().unbind();
        onFinish.ifPresent(Runnable::run);
    }

    private void cleanOldResults() {
        taskProgressBar.setProgress(0);
        errorLabel.setText("");
    }

    public void collectMetadata( Runnable onFinish) {

        CollectMetadataTask currentRunningTask = new CollectMetadataTask(fileName.get(), (q) -> onTaskFinished(Optional.ofNullable(onFinish)),  businessLogicFromXML);
        setCurrentRunningTask(currentRunningTask);

        Thread t = new Thread(currentRunningTask);
        t.start();
        bindTaskToUIComponents(currentRunningTask, onFinish);
    }

    private static void showError(Thread thread, Throwable throwable) {
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
}