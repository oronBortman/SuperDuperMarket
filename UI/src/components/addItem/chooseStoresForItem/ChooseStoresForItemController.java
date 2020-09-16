package components.addItem.chooseStoresForItem;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import logic.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toCollection;

public class ChooseStoresForItemController {

    @FXML private TableView StoresTable;
    @FXML private TableColumn<Store, String> SerialIDCol;
    @FXML private TableColumn<Store, String> nameCol;
    @FXML private Button ButtonAddStore;
    @FXML private ComboBox<Store> ComboBoxChooseStore;
    @FXML private Label LabelErrorOnTable;
    @FXML private Label LabelErrorEnterPrice;
    @FXML private TextField TextFieldEnterPrice;

    BusinessLogic businessLogic;
    Item addedItem;
    Consumer<Boolean> addedItemToStores;

    HashMap<Integer, Store> storeInComboBoxMap;
    HashMap<Store, AvailableItemInStore> storesToAddToItem;
    SimpleBooleanProperty isStoreChosen;
    SimpleBooleanProperty tableIsEmpty;


    public ChooseStoresForItemController() {
        addedItem = null;
        storeInComboBoxMap = new HashMap<Integer, Store>();
        storesToAddToItem = new HashMap<Store, AvailableItemInStore>();
        isStoreChosen = new SimpleBooleanProperty(false);
        tableIsEmpty = new SimpleBooleanProperty(true);
    }
    // final ObservableList<SimpleBooleanProperty> componentsNeedToBeFieldForAddingStore =
    //  FXCollections.observableArrayList(tableIsEmpty,textFieldPPKEmpty,textFieldNameEmpty,textFieldSerialIDEmpty);

    public void initialize() {
        initializeComboBoxChooseStore();
        initializeStoresTable();
    }

    public void setProperties(Consumer<Boolean> addedItemToStores) {
        this.addedItemToStores = addedItemToStores;
        ButtonAddStore.disableProperty().bind(isStoreChosen.not());
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        storeInComboBoxMap = new HashMap(businessLogic.getStoresSerialIDMap());
        setComboBoxStoresFromBusinessLogic();
    }

    public void setAddedItem(Item item) {
        this.addedItem = item;
    }

    public void setComboBoxStoresFromBusinessLogic() {
        final ObservableList<Store> observableList = FXCollections.observableList(businessLogic.getStoresList());
        ComboBoxChooseStore.setItems(observableList);
    }

    @FXML
    public void initializeComboBoxChooseStore() {
        ComboBoxChooseStore.setConverter(new StringConverter<Store>() {
            @Override
            public String toString(Store object) {
                return "Serial Number:" + object.getSerialNumber() + ", Name:" + object.getName();
            }

            @Override
            public Store fromString(String string) {
                return null;
            }
        });

        ComboBoxChooseStore.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {

            }
        });
    }


    private void initializeStoresTable() {
        SerialIDCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Store, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Store, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getSerialNumber().toString());
            }
        });
        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Store, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Store, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getName());
            }
        });
    }

    @FXML
    public void chooseStore(ActionEvent actionEvent) {
        isStoreChosen.set(true);
    }


    public boolean checkTableAndAddErrorMessage() {
        boolean tableIsOK = false;
        if (StoresTable.getItems() == null) {
            tableIsEmpty.set(true);
            LabelErrorOnTable.setText("* Table can't be empty");
        } else {
            tableIsOK = true;
            LabelErrorOnTable.setText("");
        }
        return tableIsOK;
    }


    @FXML
    public void clickedOnButtonFinish(ActionEvent actionEvent)
    {
        if(storesToAddToItem.isEmpty())
        {
            LabelErrorOnTable.setText("*Table can't be empty");
        }
        else
        {
            for(Map.Entry<Store, AvailableItemInStore> entry: storesToAddToItem.entrySet())
            {
                Store store = entry.getKey();
                AvailableItemInStore availableItemInStore = entry.getValue();
                store.addItemToStore(availableItemInStore);
            }
            addedItemToStores.accept(true);
        }
        clearAll();
        storesToAddToItem = new HashMap<Store, AvailableItemInStore>();

    }


    public void clearAll() {
        setComboBoxStoresFromBusinessLogic();
        StoresTable.getItems().clear();
        tableIsEmpty.set(true);
        ComboBoxChooseStore.getItems().clear();
    }



    public boolean isTextFieldEnterPriceEmpty()
    {
        return TextFieldEnterPrice.getText().equals("");
    }
    public Integer getEnteredPrice()
    {
        return Integer.parseInt(TextFieldEnterPrice.getText());
    }


    @FXML
    public void clickedOnButtonAddStore(ActionEvent actionEvent)
    {

        if(isTextFieldEnterPriceEmpty())
        {
            LabelErrorEnterPrice.setText("* Need to fill price field");
        }
        else
        {
            try
            {
                Integer price = getEnteredPrice();
                if(price < 0)
                {
                    LabelErrorEnterPrice.setText("* price has to be a positive whole number");
                }
                else
                {
                    Store storeInCombobox = ComboBoxChooseStore.getValue();
                    storesToAddToItem.put(storeInCombobox, new AvailableItemInStore(addedItem, getEnteredPrice()));
                    storeInComboBoxMap.remove(storeInCombobox.getSerialNumber());
                    final ObservableList<Store> storesInComboBoxList = FXCollections.observableList(storeInComboBoxMap.values().stream().collect(toCollection(ArrayList::new)));
                    ComboBoxChooseStore.setItems(storesInComboBoxList);
                    final ObservableList<Store> storesToAddToItemList = FXCollections.observableList(storesToAddToItem.keySet().stream().collect(toCollection(ArrayList::new)));
                    StoresTable.setItems(storesToAddToItemList);
                    tableIsEmpty.set(false);
                    isStoreChosen.set(false);
                    LabelErrorEnterPrice.setText("");
                }
            }
            catch(NumberFormatException exception)
            {
                LabelErrorEnterPrice.setText("* price has to be a positive whole number");
            }
        }

    }

}
