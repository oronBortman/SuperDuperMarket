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
import logic.*;
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
    @FXML Label errorLabel;

    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
    private SimpleBooleanProperty isMetadataCollected;
    private SimpleBooleanProperty isActive;

    private Task<Boolean> currentRunningTask;

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
                    Integer itemID = duplicateItemSerialIDException.getSerialId();
                    String itemName = duplicateItemSerialIDException.getName();
                    errorLabel.setText("Error: Found duplicate serial key " + itemID + " of item " + itemName);
                }
                else if (ex.getClass() == DuplicateStoreSerialIDException.class)
                {

                    DuplicateStoreSerialIDException duplicateStoreSerialIDException = (DuplicateStoreSerialIDException) ex;
                    Integer storeID = duplicateStoreSerialIDException.getSerialId();
                    String storeName = duplicateStoreSerialIDException.getName();
                    errorLabel.setText("Error: Found duplicate serial key " + storeID + " of store" + storeName);
                }
                else if (ex.getClass() == DuplicateCustomerSerialIDException.class)
                {
                    DuplicateCustomerSerialIDException duplicateCustomerSerialIDException = (DuplicateCustomerSerialIDException) ex;
                    Integer customerID = duplicateCustomerSerialIDException.getSerialId();
                    String customerName = duplicateCustomerSerialIDException.getName();
                    errorLabel.setText("Error: Found duplicate serial key " + customerID + " of customer " + customerID);
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
                    String storeName = itemIDNotExistInAStoreException.getStoreName();
                    errorLabel.setText("Error: The item with the serial key " + itemID + " doesn't exist in the store " + storeName);
                }
                else if (ex.getClass() == DuplicateDiscountNameException.class)
                {
                    DuplicateDiscountNameException duplicateDiscountNameException = (DuplicateDiscountNameException) ex;
                    String discountName = duplicateDiscountNameException.getDiscountName();
                    String storeName = duplicateDiscountNameException.getStoreName();
                    errorLabel.setText("Error: A discount with the name " + discountName + " already exists in the store"  + storeName);
                }
                else if(ex.getClass() == CustomerLocationIsIdenticalToCustomerException.class)
                {
                    CustomerLocationIsIdenticalToCustomerException customerLocationIsIdenticalToCustomerException = (CustomerLocationIsIdenticalToCustomerException) ex;
                    Customer firstCustomer = customerLocationIsIdenticalToCustomerException.getFirstCustomer();
                    Customer secondCustomer = customerLocationIsIdenticalToCustomerException.getSecondCustomer();
                    SDMLocation location = firstCustomer.getLocation();
                    String locationStr = "(" + location.getX() + "," + location.getY() + ")";
                    errorLabel.setText("Error: The location " + locationStr + " of the customer with name " + firstCustomer.getName() + " and serial ID: " + firstCustomer.getSerialNumber() +
                            "\nis identical to the location of the customer with name " + secondCustomer.getName() + " and serial ID: " + secondCustomer.getSerialNumber());
                }
                else if(ex.getClass() == CustomerLocationIsIdenticalToStoreException.class)
                {
                    CustomerLocationIsIdenticalToStoreException customerLocationIsIdenticalToStoreException = (CustomerLocationIsIdenticalToStoreException) ex;
                    Customer customer = customerLocationIsIdenticalToStoreException.getCustomer();
                    Store store = customerLocationIsIdenticalToStoreException.getStore();
                    SDMLocation location = customer.getLocation();
                    String locationStr = "(" + location.getX() + "," + location.getY() + ")";
                    errorLabel.setText("Error: The location " + locationStr + " of the customer with name " + customer.getName() + " and serial ID: " + customer.getSerialNumber() +
                            "\nis identical to the location of the store with name " + store.getName() + " and serial ID: " + store.getSerialNumber());
                }
                else if(ex.getClass() == StoreLocationIsIdenticalToStoreException.class)
                {
                    StoreLocationIsIdenticalToStoreException storeLocationIsIdenticalToStoreException = (StoreLocationIsIdenticalToStoreException) ex;
                    Store firstStore = storeLocationIsIdenticalToStoreException.getFirstStore();
                    Store secondStore = storeLocationIsIdenticalToStoreException.getSecondStore();
                    SDMLocation location = firstStore.getLocationOfShop();
                    String locationStr = "(" + location.getX() + "," + location.getY() + ")";
                    errorLabel.setText("Error: The location " + locationStr + " of the store with name " + firstStore.getName() + " and serial ID: " + firstStore.getSerialNumber() +
                            "\nis identical to the location of the store with name " + secondStore.getName() + " and serial ID: " + secondStore.getSerialNumber());
                }
                else if(ex.getClass() == StoreLocationIsIdenticalToCustomerException.class)
                {
                    StoreLocationIsIdenticalToCustomerException storeLocationIsIdenticalToCustomerException = (StoreLocationIsIdenticalToCustomerException) ex;
                    Store store = storeLocationIsIdenticalToCustomerException.getStore();
                    Customer customer = storeLocationIsIdenticalToCustomerException.getCustomer();
                    SDMLocation location = store.getLocationOfShop();
                    String locationStr = "(" + location.getX() + "," + location.getY() +")";
                    errorLabel.setText("Error: The location " + locationStr + " of the store with name " + store.getName() + " and serial ID: " + store.getSerialNumber() +
                            "\nis identical to the location of the customer with name " + customer.getName() + " and serial ID: " + customer.getSerialNumber());
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
}