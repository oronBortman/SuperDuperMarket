package components.addItem;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import logic.*;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.stream.Collectors.toCollection;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddItemController {

    @FXML private TextField TextFieldSerialID;
    @FXML private TextField TextFieldItemName;
    @FXML private Label LabelErrorSerialID;
    @FXML private Label LabelErrorItemName;
    @FXML private RadioButton RadioButtonQuantity;
    @FXML private RadioButton RadioButtonWeight;
    @FXML private Pane PaneAddItems;
    @FXML private TableView StoresTable;
    @FXML private TableColumn<Store, String> SerialIDCol;
    @FXML private TableColumn<Store, String> nameCol;
    @FXML private Button ButtonAddStore;
    @FXML private ComboBox<Store> ComboBoxChooseStore;
    @FXML private Label LabelErrorOnTable;
    @FXML private Button buttonAddItem;


    BusinessLogic businessLogic;
    SimpleBooleanProperty isStoreChosen;
    SimpleBooleanProperty textFieldSerialIDEmpty;
    SimpleBooleanProperty textFieldNameEmpty;
    SimpleBooleanProperty tableIsEmpty;
    SimpleBooleanProperty isRadioButtonQuantitySelected;
    SimpleBooleanProperty isRadioButtonWeightSelected;

    HashMap<Integer, Store> storeInComboBoxMap;
    HashMap<Integer, Store> storesToAddToItem;


    public AddItemController() {
        storeInComboBoxMap = new HashMap<Integer, Store>();
        storesToAddToItem = new HashMap<Integer, Store>();
        isStoreChosen = new SimpleBooleanProperty(false);
        isRadioButtonQuantitySelected = new SimpleBooleanProperty(false);
        isRadioButtonWeightSelected = new SimpleBooleanProperty(false);
        textFieldSerialIDEmpty = new SimpleBooleanProperty(true);
        textFieldNameEmpty = new SimpleBooleanProperty(true);
        tableIsEmpty = new SimpleBooleanProperty(true);
    }
    // final ObservableList<SimpleBooleanProperty> componentsNeedToBeFieldForAddingStore =
    //  FXCollections.observableArrayList(tableIsEmpty,textFieldPPKEmpty,textFieldNameEmpty,textFieldSerialIDEmpty);

    public void initialize() {
        initializeComboBoxChooseItem();
        initializeStoresTable();
        setProperties();
    }

    public void setProperties() {
        //TODO
        //Change xml loading
        ButtonAddStore.disableProperty().bind(isStoreChosen.not());
        buttonAddItem.disableProperty().bind(tableIsEmpty);
        RadioButtonQuantity.selectedProperty().bind(isRadioButtonWeightSelected);
       /* buttonAddStore.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            for (int i = 0; i < componentsNeedToBeFieldForAddingStore.size(); ++i) {
                if (componentsNeedToBeFieldForAddingStore.get(i).equals(true)) {
                    return false;
                }
            }
            return true;
        }, componentsNeedToBeFieldForAddingStore));*/


    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        storeInComboBoxMap = new HashMap(businessLogic.getItemsSerialIDMap());
        setComboBoxStoresFromBusinessLogic();
    }

    public void setComboBoxStoresFromBusinessLogic() {
        final ObservableList<Store> observableList = FXCollections.observableList(businessLogic.getStoresList());
        ComboBoxChooseStore.setItems(observableList);
    }

    @FXML
    public void initializeComboBoxChooseItem() {
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


    public boolean checkSerialNumberAndAddErrorMessage() {
        boolean serialIdIsOK = false;
        if (isTextFieldSerialIDEmpty()) {
            LabelErrorSerialID.setText("* must enter serial ID");
        } else {
            try {
                int serialID = getEnteredSerialID();
                //TODO
                //NEED to check there is no more store with there serial id
                if (serialID > 0) {
                    if (businessLogic.checkIfItemIdExists(serialID)) {
                        LabelErrorSerialID.setText("* There is already an item with this serial ID");
                    } else {
                        serialIdIsOK = true;
                        LabelErrorSerialID.setText("");
                    }
                } else {
                    LabelErrorSerialID.setText("* must enter a whole positive number");
                }
            } catch (NumberFormatException exception) {
                LabelErrorSerialID.setText("* must enter a whole positive number");
            }
        }
        return serialIdIsOK;
    }

    public boolean checkNameOfItemAndAddErrorMessage() {
        boolean nameIsOK = false;

        if (isTextFieldItemNameEmpty()) {
            LabelErrorItemName.setText("* item name can't be empty");
        } else {
            nameIsOK = true;
            LabelErrorItemName.setText("");
        }
        return nameIsOK;
    }


    public void clickedOnButtonAddItem(ActionEvent actionEvent) {

        SDMLocation location = null;
        boolean tableIsOK = checkTableAndAddErrorMessage();
        boolean serialIdIsOK = checkSerialNumberAndAddErrorMessage();
        boolean isNameOK = checkNameOfItemAndAddErrorMessage();
        //TODO
        boolean isAmountOK=false;
        Item newItemToAdd=null;

        if (tableIsOK  && serialIdIsOK && isNameOK && isAmountOK) {
            System.out.println("GREAT!!!");
            String nameOfItem = TextFieldItemName.getText();
            if(isRadioButtonQuantitySelected.getValue())
            {
                newItemToAdd = new Item(getEnteredSerialID(), nameOfItem, Item.TypeOfMeasure.Quantity);
            }
            else if(isRadioButtonWeightSelected.getValue())
            {
                newItemToAdd = new Item(getEnteredSerialID(), nameOfItem, Item.TypeOfMeasure.Weight);

            }
            businessLogic.addItem(newItemToAdd);
        }
        clearAll();
        setComboBoxStoresFromBusinessLogic();
    }

    public void clearAll() {
        setComboBoxStoresFromBusinessLogic();
        TextFieldSerialID.setText("");
        TextFieldItemName.setText("");
        StoresTable.getItems().clear();
        tableIsEmpty.set(true);
        ComboBoxChooseStore.getItems().clear();
    }

    public Integer getEnteredSerialID() {
        return Integer.parseInt(TextFieldSerialID.getText());
    }

    public boolean isTextFieldSerialIDEmpty() {
        return TextFieldSerialID.getText().equals("");
    }

    public boolean isTextFieldItemNameEmpty() {
        return TextFieldItemName.getText().equals("");
    }

    public void clickedOnAddButton(ActionEvent actionEvent) {
        Store storeInCombobox = ComboBoxChooseStore.getValue();
        storesToAddToItem.put(storeInCombobox.getSerialNumber(), storeInCombobox);
        storeInComboBoxMap.remove(storeInCombobox.getSerialNumber());
        final ObservableList<Store> storesInComboBoxList = FXCollections.observableList(storeInComboBoxMap.values().stream().collect(toCollection(ArrayList::new)));
        ComboBoxChooseStore.setItems(storesInComboBoxList);
        final ObservableList<Store> storesToAddToItemList = FXCollections.observableList(storesToAddToItem.values().stream().collect(toCollection(ArrayList::new)));
        StoresTable.setItems(storesToAddToItemList);
        tableIsEmpty.set(false);
    }

    @FXML
    public void chooseQuantityRadioButton(ActionEvent actionEvent)
    {
        if(RadioButtonQuantity.isSelected())
        {
            isRadioButtonQuantitySelected.set(true);
            isRadioButtonWeightSelected.set(false);
            RadioButtonWeight.setSelected(false);

        }
        else
        {
            isRadioButtonQuantitySelected.set(false);
        }
    }

    @FXML
    public void chooseWeightRadioButton(ActionEvent actionEvent)
    {
        if(RadioButtonWeight.isSelected())
        {
            isRadioButtonWeightSelected.set(true);
            isRadioButtonQuantitySelected.set(false);
            RadioButtonQuantity.setSelected(false);

        }
        else
        {
            isRadioButtonWeightSelected.set(false);
        }
    }

}
