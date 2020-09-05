package components.updatePriceOfItemInStoreScreen;

import commonUI.GeneralUIMethods;
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

    @FXML
    private void initialize() {

        comboBoxStores.setConverter(new StringConverter<Store>() {
            @Override
            public String toString(Store object) {
                return object.getName();
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
                return object.getName();
            }

            @Override
            public AvailableItemInStore fromString(String string) {
                return comboBoxItems.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });


       /* comboBoxStores.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
            {
                serialNumberValue.setText(newval.getSerialNumber().toString());
                nameOfStoreValue.setText(newval.getName());
                PPKValue.setText(newval.getPPK().toString());
                // totalPaymentForDeliveriesVal.setText(newval.get);
            }
        });*/
        updatePriceTextField.setDisable(true);
        updatePriceButton.setDisable(true);
        comboBoxItems.setDisable(true);
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        final ObservableList<Store> stores = FXCollections.observableList(businessLogic.getStoresList());
        comboBoxStores.setItems(stores);

        //TODO
        //Change xml loading
    }

    public UpdateItemInStoreController()
    {

    }

    @FXML
    void chooseStore(ActionEvent event){
        GeneralUIMethods.initiateStatusAfterClickedOnButtonLabel(statusAfterClickedOnButton);

        comboBoxItems.setDisable(false);
        final ObservableList<AvailableItemInStore> items = FXCollections.observableList(comboBoxStores.getValue().getItemsList());
        comboBoxItems.setItems(items);
    }

    @FXML
    void chooseItem(ActionEvent event){
        GeneralUIMethods.initiateStatusAfterClickedOnButtonLabel(statusAfterClickedOnButton);

        Store storeFromCombox = comboBoxStores.getValue();
        AvailableItemInStore itemFromCombox = comboBoxItems.getValue();
        if(storeFromCombox != null && itemFromCombox != null)
        {
            updatePriceTextField.setDisable(false);
            updatePriceButton.setDisable(false);
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

    /*boolean checkIfStringIsValidPriceAndSetError(String priceStr)
    {
        if (priceStr == null) {
            return false;
        }
        try {
            Integer priceInt = Integer.parseInt(priceStr);
            if(priceInt < 0)
            {
                setAnErrorToStatusAfterClickedOnButtonLabel("Price can't be negative");
                return false;
            }
        } catch (NumberFormatException nfe) {
            setAnErrorToStatusAfterClickedOnButtonLabel("Price need to be a whole number");
            return false;
        }
        return true;
    }

    void setAnErrorToStatusAfterClickedOnButtonLabel(String errorMessage)
    {
        statusAfterClickedOnButton.setText(errorMessage);
        statusAfterClickedOnButton.setTextFill(Color.RED);
    }

    void initiateStatusAfterClickedOnButtonLabel()
    {
        statusAfterClickedOnButton.setText("");
        statusAfterClickedOnButton.setTextFill(Color.BLACK);
    }
*/
}
