package components.addItemToStoreScreen;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import logic.BusinessLogic;
import logic.Item;
import logic.Store;
import commonUI.*;

public class AddItemToStoreContoller {

    @FXML GridPane showStoresGridPane;
    @FXML ComboBox<Store> comboBoxStores;
    @FXML ComboBox<Item> comboBoxItems;
    @FXML Label serialNumberValue;
    BusinessLogic businessLogic;
    @FXML TextField updatePriceTextField;
    @FXML Label statusAfterClickedOnButton;
    @FXML Button addItemButton;

    SimpleBooleanProperty isStoreSelected;
    SimpleBooleanProperty isItemSelected;

    @FXML
    private void initialize() {




        comboBoxStores.setConverter(new StringConverter<Store>() {
            @Override
            public String toString(Store object) {
                return "Serial Number:" + object.getSerialNumber() + ", Name:" +object.getName();

            }

            @Override
            public Store fromString(String string) {
                return comboBoxStores.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });

        comboBoxItems.setConverter(new StringConverter<Item>() {
            @Override
            public String toString(Item object) {
                return "Serial Number:" + object.getSerialNumber() + ", Name:" +object.getName();

            }

            @Override
            public Item fromString(String string) {
                return comboBoxItems.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
        //collectMetadataButton.disableProperty().bind(isFileSelected.not());

        updatePriceTextField.disableProperty().bind(isStoreSelected.not());
        addItemButton.disableProperty().bind(isStoreSelected.not());
        comboBoxStores.disableProperty().bind(isItemSelected.not());

    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        final ObservableList<Item> items = FXCollections.observableList(businessLogic.getItemsList());
        comboBoxItems.setItems(items);
        //TODO
        //Change xml loading
    }

    public AddItemToStoreContoller()
    {
        isStoreSelected = new SimpleBooleanProperty(false);
        isItemSelected = new SimpleBooleanProperty(false);
    }

    @FXML
    void chooseStore(ActionEvent event){
        commonUI.GeneralUIMethods.initiateStatusAfterClickedOnButtonLabel(statusAfterClickedOnButton);
      //  updatePriceTextField.setDisable(false);
       // addItemButton.setDisable(false);
        isStoreSelected.set(true);
    }

    @FXML
    void chooseItem(ActionEvent event){
        commonUI.GeneralUIMethods.initiateStatusAfterClickedOnButtonLabel(statusAfterClickedOnButton);
        //comboBoxStores.setDisable(false);
        final ObservableList<Store> stores = FXCollections.observableList(businessLogic.getStoresList());
        comboBoxStores.setItems(stores);
        isItemSelected.set(true);

    }

    @FXML
    void clickedOnAddItemButton(ActionEvent event)
    {
        boolean checkIfStoreAlreadySellesItem = comboBoxStores.getValue().checkIfItemIdExists(comboBoxItems.getValue().getSerialNumber());
        boolean checkIfStringIsValidPrice = commonUI.GeneralUIMethods.checkIfStringIsValidPriceAndSetError(updatePriceTextField.getText(), statusAfterClickedOnButton);
        if(checkIfStoreAlreadySellesItem)
        {
            commonUI.GeneralUIMethods.setAnErrorToStatusAfterClickedOnButtonLabel("Item already in store", statusAfterClickedOnButton);
        }
        else if(checkIfStringIsValidPrice)
        {
            int priceInt = Integer.parseInt(updatePriceTextField.getText());
            comboBoxStores.getValue().addItemToStore(comboBoxItems.getValue(), priceInt);
            statusAfterClickedOnButton.setText("Update price succesfully");
            statusAfterClickedOnButton.setTextFill(Color.BLACK);
        }
    }
}
