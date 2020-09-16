package components.addStore;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import logic.*;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.stream.Collectors.toCollection;

public class AddStoreController {

    @FXML private TextField TextFieldSerialID;
    @FXML private TextField TextFieldStoreName;
    @FXML private Slider SliderCoordinateX;
    @FXML private Slider SliderCoordinateY;
    @FXML private Label LabelCoordinateX;
    @FXML private Label LabelCoordinateY;
    @FXML private TextField TextFieldPPK;
    @FXML private Label LabelErrorSerialID;
    @FXML private Label LabelErrorStoreName;
    @FXML private Label LabelErrorPPK;
    @FXML private TableView ItemsTable;
    @FXML private TableColumn<Item, String> SerialIDCol;
    @FXML private TableColumn<Item, String> nameCol;
    @FXML private TableColumn<Item, String> typeOfMeasureCol;
    @FXML private Pane PaneAddItems;

    @FXML private Button ButtonAdd;
    @FXML private Button  buttonAddStore;
    @FXML private TextField TextFieldEnterPrice;
    @FXML private TextField textFieldCoordinateX;
    @FXML private TextField textFieldCoordinateY;
    @FXML private ComboBox<Item> ComboBoxChooseItem;
    @FXML private Label LabelErrorEnterPrice;
    @FXML private Label LabelErrorOnTable;
    @FXML private Label LabelErrorCoordinateX;
    @FXML private Label LabelErrorCoordinateY;

    BusinessLogic businessLogic;
    SimpleBooleanProperty isItemChosen;
    SimpleBooleanProperty textFieldPriceEmpty;
    SimpleBooleanProperty textFieldSerialIDEmpty;
    SimpleBooleanProperty textFieldNameEmpty;
    SimpleBooleanProperty textFieldPPKEmpty;
    SimpleBooleanProperty tableIsEmpty;

    HashMap<Integer, Item> itemsInComboBoxMap;
    HashMap<Integer, AvailableItemInStore> itemsToAddToStore;


    public AddStoreController()
    {
        itemsInComboBoxMap = new HashMap<Integer, Item>();
        itemsToAddToStore = new HashMap<Integer, AvailableItemInStore>();
        isItemChosen = new SimpleBooleanProperty(false);
        textFieldPriceEmpty = new SimpleBooleanProperty(true);
        textFieldSerialIDEmpty = new SimpleBooleanProperty(true);
        textFieldNameEmpty = new SimpleBooleanProperty(true);
        textFieldPPKEmpty = new SimpleBooleanProperty(true);
        tableIsEmpty = new SimpleBooleanProperty(true);
    }
   // final ObservableList<SimpleBooleanProperty> componentsNeedToBeFieldForAddingStore =
          //  FXCollections.observableArrayList(tableIsEmpty,textFieldPPKEmpty,textFieldNameEmpty,textFieldSerialIDEmpty);

    public void initialize()
    {
        initializeComboBoxChooseItem();
        initializeItemsTable();
        setProperties();
    }
    public void setProperties()
    {
        //TODO
        //Change xml loading
        TextFieldEnterPrice.disableProperty().bind(isItemChosen.not());
        ButtonAdd.disableProperty().bind(isItemChosen.not());
        buttonAddStore.disableProperty().bind(tableIsEmpty);
       /* buttonAddStore.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            for (int i = 0; i < componentsNeedToBeFieldForAddingStore.size(); ++i) {
                if (componentsNeedToBeFieldForAddingStore.get(i).equals(true)) {
                    return false;
                }
            }
            return true;
        }, componentsNeedToBeFieldForAddingStore));*/


    }

    public void setBusinessLogic(BusinessLogic businessLogic)
    {
        this.businessLogic = businessLogic;
        itemsInComboBoxMap = new HashMap(businessLogic.getItemsSerialIDMap());
        setComboBoxItemsFromBusinessLogic();
    }

    public void setComboBoxItemsFromBusinessLogic()
    {
        final ObservableList<Item> observableList = FXCollections.observableList(businessLogic.getItemsList());
        ComboBoxChooseItem.setItems(observableList);
    }

    @FXML
    public void initializeComboBoxChooseItem()
    {
        ComboBoxChooseItem.setConverter(new StringConverter<Item>() {
            @Override
            public String toString(Item object) {
                return "Serial Number:" + object.getSerialNumber() + ", Name:" +object.getName();
            }

            @Override
            public Item fromString(String string) {
                return null;
            }
        });

        ComboBoxChooseItem.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
            {

            }
        });
    }


    private void initializeItemsTable()
    {
        SerialIDCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getSerialNumber().toString());
            }
        });
        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getName());
            }
        });
        typeOfMeasureCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getTypeOfMeasureStr());
            }
        });
    }

    public void chooseItem(ActionEvent actionEvent) {
        isItemChosen.set(true);
        TextFieldEnterPrice.setText("");
        LabelErrorEnterPrice.setText("");
    }


    public boolean checkTableAndAddErrorMessage()
    {
        boolean tableIsOK = false;
        if(ItemsTable.getItems() == null)
        {
            tableIsEmpty.set(true);
            LabelErrorOnTable.setText("* Table can't be empty");
        }
        else
        {
            tableIsOK=true;
            LabelErrorOnTable.setText("");
        }
        return tableIsOK;
    }

    public boolean checkPPKAndAddErrorMessage()
    {
        boolean ppkIsOK=false;
        if(isTextFieldPPKEmpty())
        {
            LabelErrorPPK.setText("* must enter ppk");
        }
        else
        {
            try
            {
                int ppk = getEnteredPPK();
                if(ppk > 0)
                {
                    ppkIsOK=true;
                    LabelErrorOnTable.setText("");
                }
                else
                {
                    LabelErrorPPK.setText("* must enter a whole positive number");
                }
            }
            catch(NumberFormatException exception)
            {
                LabelErrorPPK.setText("* must enter a whole positive number");
            }
        }
        return ppkIsOK;
    }

    public boolean checkSerialNumberAndAddErrorMessage()
    {
        boolean serialIdIsOK=false;
        if(isTextFieldSerialIDEmpty())
        {
            LabelErrorSerialID.setText("* must enter serial ID");
        }
        else
        {
            try
            {
                int serialID = getEnteredSerialID();
                //TODO
                //NEED to check there is no more store with there serial id
                if(serialID > 0)
                {
                    if(businessLogic.checkIfStoreExists(serialID))
                    {
                        LabelErrorSerialID.setText("* There is already a store with this serial ID");
                    }
                    else
                    {
                        serialIdIsOK=true;
                        LabelErrorSerialID.setText("");
                    }
                }
                else
                {
                    LabelErrorSerialID.setText("* must enter a whole positive number");
                }
            }
            catch(NumberFormatException exception)
            {
                LabelErrorSerialID.setText("* must enter a whole positive number");
            }
        }
        return serialIdIsOK;
    }

    public boolean checkNameOfStoreAndAddErrorMessage()
    {
        boolean nameIsOK=false;

        if(isTextFieldStoreNameEmpty())
        {
            LabelErrorStoreName.setText("* store name can't be empty");
        }
        else
        {
            nameIsOK=true;
            LabelErrorStoreName.setText("");
        }
        return nameIsOK;
    }

    public boolean checkCoordinateAndAddErrorMessage(TextField textField, Label errorLabel)
    {
        boolean coordinateIsOK=false;
        if(textField.getText() != "")
        {
            try
            {
                int coordinate = Integer.parseInt(textField.getText());
                //TODO
                //NEED to check there is no more store with there serial id
                if(coordinate >= 1 || coordinate <= 50)
                {
                        coordinateIsOK=true;
                        textField.setText("");
                }
            }
            catch(NumberFormatException exception)
            {
            }
        }
        if(coordinateIsOK == false)
        {
            errorLabel.setText( "* must enter a number between 1 to 50");
        }
        return coordinateIsOK;

    }


    public Integer getCoordinateX()
    {
        return Integer.parseInt(textFieldCoordinateX.getText());
    }

    public Integer getCoordinateY()
    {
        return Integer.parseInt(textFieldCoordinateY.getText());
    }

    public void clickedOnButtonAddStore(ActionEvent actionEvent) {

        SDMLocation location=null;
        boolean tableIsOK = checkTableAndAddErrorMessage();
        boolean ppkIsOK = checkPPKAndAddErrorMessage();
        boolean serialIdIsOK = checkSerialNumberAndAddErrorMessage();
        boolean isNameOK = checkNameOfStoreAndAddErrorMessage();
        boolean isCoordinateXOK = checkCoordinateAndAddErrorMessage(textFieldCoordinateX, LabelErrorCoordinateX);
        boolean isCoordinateYOK = checkCoordinateAndAddErrorMessage(textFieldCoordinateY, LabelErrorCoordinateY);

        if(isCoordinateXOK && isCoordinateYOK)
        {
            int coordinateX=getCoordinateX();
            int coordinateY=getCoordinateY();
            location = new SDMLocation(coordinateX, coordinateY);
            if(businessLogic.checkIfLocationAlreadyExists(location))
            {
                LabelErrorCoordinateX.setText("* There is already a store with this location");
                LabelErrorCoordinateY.setText("* There is already a store with this location");
                isCoordinateXOK = false;
                isCoordinateYOK = false;
            }
        }
        if(tableIsOK && ppkIsOK && serialIdIsOK && isNameOK && isCoordinateXOK && isCoordinateYOK)
        {
            System.out.println("GREAT!!!");
            String nameOfStore = TextFieldStoreName.getText();
            Store newStoreToAdd = new Store(getEnteredSerialID(),nameOfStore, getEnteredPPK(), location, itemsToAddToStore);
            businessLogic.addStore(newStoreToAdd);
        }
        clearAll();
        setComboBoxItemsFromBusinessLogic();
    }

    public void clearAll()
    {
        setComboBoxItemsFromBusinessLogic();
        TextFieldSerialID.setText("");
        TextFieldEnterPrice.setText("");
        TextFieldPPK.setText("");
        TextFieldStoreName.setText("");
        /*nameCol.setText("");
        SerialIDCol.setText("");
        typeOfMeasureCol.setText("");*/
        ItemsTable.getItems().clear();
        tableIsEmpty.set(true);
        ComboBoxChooseItem.getItems().clear();
        textFieldCoordinateX.setText("");
        textFieldCoordinateY.setText("");
    }

    public boolean isTextFieldEnterPriceEmpty()
    {
        return TextFieldEnterPrice.getText().equals("");
    }


    public Integer getEnteredPrice()
    {
        return Integer.parseInt(TextFieldEnterPrice.getText());
    }

    public Integer getEnteredPPK()
    {
        return Integer.parseInt(TextFieldPPK.getText());
    }

    public Integer getEnteredSerialID()
    {
        return Integer.parseInt(TextFieldSerialID.getText());
    }
    public boolean isTextFieldSerialIDEmpty()
    {
        return TextFieldSerialID.getText().equals("");
    }

    public boolean isTextFieldStoreNameEmpty()
    {
        return TextFieldStoreName.getText().equals("");
    }

    public boolean isTextFieldPPKEmpty()
    {
        return TextFieldPPK.getText().equals("");
    }
    public void clickedOnAddButton(ActionEvent actionEvent)
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
                    Item itemInCombobox = ComboBoxChooseItem.getValue();
                    itemsToAddToStore.put(itemInCombobox.getSerialNumber(), new AvailableItemInStore(itemInCombobox, price));
                    itemsInComboBoxMap.remove(itemInCombobox.getSerialNumber());
                    final ObservableList<Item> itemsInComboBoxList = FXCollections.observableList(itemsInComboBoxMap.values().stream().collect(toCollection(ArrayList::new)));
                    ComboBoxChooseItem.setItems(itemsInComboBoxList);
                    final ObservableList<AvailableItemInStore> itemsToAddToStoreList = FXCollections.observableList(itemsToAddToStore.values().stream().collect(toCollection(ArrayList::new)));
                    ItemsTable.setItems(itemsToAddToStoreList);
                    tableIsEmpty.set(false);
                }
            }
            catch(NumberFormatException exception)
            {
                LabelErrorEnterPrice.setText("* price has to be a positive whole number");
            }
        }
    }

    public void sliderCoordinateXAction(MouseEvent mouseEvent) {
    }

    public void sliderCoordinateYAction(MouseEvent mouseEvent) {
    }
}
