package components.updateItemInStoreOption.updatePriceOfItemInStoreScreen;

import commonUI.GeneralUIMethods;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import logic.AvailableItemInStore;
import logic.BusinessLogic;
import logic.Store;

public class UpdateItemInStoreController {

    @FXML GridPane UpdateItemFromStoreGridPane;
    @FXML ComboBox<Store> comboBoxStores;
    @FXML ComboBox<AvailableItemInStore> comboBoxItems;
    @FXML TextField updatePriceTextField;
    @FXML Button updatePriceButton;
    @FXML Label statusAfterClickedOnButton;

    BusinessLogic businessLogic;
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

        comboBoxItems.setConverter(new StringConverter<AvailableItemInStore>() {
            @Override
            public String toString(AvailableItemInStore object) {
                return "Serial Number:" + object.getSerialNumber() + ", Name:" +object.getName();
            }

            @Override
            public AvailableItemInStore fromString(String string) {
                return comboBoxItems.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);


            }
        });
        updatePriceTextField.disableProperty().bind(isItemSelected.not());
        updatePriceButton.disableProperty().bind(isItemSelected.not());
        comboBoxItems.disableProperty().bind(isStoreSelected.not());
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        final ObservableList<Store> stores = FXCollections.observableList(businessLogic.getStoresList());
        comboBoxStores.setItems(stores);
    }

    public UpdateItemInStoreController()
    {
        isStoreSelected = new SimpleBooleanProperty(false);
        isItemSelected = new SimpleBooleanProperty(false);
    }

    @FXML
    void chooseStore(ActionEvent event){
        GeneralUIMethods.initiateStatusAfterClickedOnButtonLabel(statusAfterClickedOnButton);
        isStoreSelected.setValue(true);
        final ObservableList<AvailableItemInStore> items = FXCollections.observableList(comboBoxStores.getValue().getAvailableItemsList());
        comboBoxItems.setItems(items);
    }

    @FXML
    void chooseItem(ActionEvent event){
        GeneralUIMethods.initiateStatusAfterClickedOnButtonLabel(statusAfterClickedOnButton);
        Store storeFromCombox = comboBoxStores.getValue();
        AvailableItemInStore itemFromCombox = comboBoxItems.getValue();
        if(storeFromCombox != null && itemFromCombox != null)
        {
            isItemSelected.setValue(true);
        }
    }

    @FXML
    void updateItemPrice(ActionEvent event)
    {
        if(GeneralUIMethods.checkIfStringIsValidPriceAndSetError(updatePriceTextField.getText(), statusAfterClickedOnButton)) {
            int priceInt = Integer.parseInt(updatePriceTextField.getText());
            comboBoxStores.getValue().updatePriceOfItem(comboBoxItems.getValue().getSerialNumber(), priceInt);
            statusAfterClickedOnButton.setText("Update price succesfully");
            statusAfterClickedOnButton.setTextFill(Color.BLACK);
        }
    }
}
