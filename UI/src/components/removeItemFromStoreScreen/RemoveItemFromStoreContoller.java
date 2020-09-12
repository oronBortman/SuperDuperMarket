package components.removeItemFromStoreScreen;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import logic.AvailableItemInStore;
import logic.BusinessLogic;
import logic.Item;
import logic.Store;

public class RemoveItemFromStoreContoller {

    @FXML GridPane RemoveItemFromStoreGridPane;
    @FXML ComboBox<Store> comboBoxStores;
    @FXML ComboBox<AvailableItemInStore> comboBoxItems;
    @FXML Button deleteItemButton;
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

        deleteItemButton.disableProperty().bind(isItemSelected.not());
        comboBoxItems.disableProperty().bind(isStoreSelected.not());
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        final ObservableList<Store> stores = FXCollections.observableList(businessLogic.getStoresList());
        comboBoxStores.setItems(stores);

        //TODO
        //Change xml loading
    }

    public RemoveItemFromStoreContoller()
    {
        isStoreSelected = new SimpleBooleanProperty(false);
        isItemSelected = new SimpleBooleanProperty(false);
    }

    @FXML
    void chooseStore(ActionEvent event)
    {
        commonUI.GeneralUIMethods.initiateStatusAfterClickedOnButtonLabel(statusAfterClickedOnButton);
        if(comboBoxStores.getValue() != null)
        {
            final ObservableList<AvailableItemInStore> items = FXCollections.observableList(comboBoxStores.getValue().getAvailableItemsList());
            comboBoxItems.setItems(items);
            isStoreSelected.setValue(true);
        }
    }

    @FXML
    void chooseItem(ActionEvent event)
    {
        commonUI.GeneralUIMethods.initiateStatusAfterClickedOnButtonLabel(statusAfterClickedOnButton);

        Store storeFromCombox = comboBoxStores.getValue();
        AvailableItemInStore itemFromCombox = comboBoxItems.getValue();
        if(storeFromCombox != null && itemFromCombox != null)
        {
            isItemSelected.setValue(true);
        }
    }

    @FXML
    void deleteItem(ActionEvent event)
    {
        Store storeFromCombox = comboBoxStores.getValue();
        AvailableItemInStore itemFromCombox = comboBoxItems.getValue();
        boolean checkIfItemIsTheOnlyOneInStore;
        boolean onlyCertainStoreSellesItem;
        if(storeFromCombox != null && itemFromCombox != null)
        {
            onlyCertainStoreSellesItem = businessLogic.checkIfOnlyCertainStoreSellesItem(itemFromCombox.getSerialNumber(), storeFromCombox.getSerialNumber());
            checkIfItemIsTheOnlyOneInStore = storeFromCombox.checkIfItemIsTheOnlyOneInStore(itemFromCombox.getSerialNumber());
            if(onlyCertainStoreSellesItem)
            {
                commonUI.GeneralUIMethods.setAnErrorToStatusAfterClickedOnButtonLabel("Only this store selles The item.\nTherefore, this item can't be deleted.", statusAfterClickedOnButton);

            }
            else if(checkIfItemIsTheOnlyOneInStore)
            {
                commonUI.GeneralUIMethods.setAnErrorToStatusAfterClickedOnButtonLabel("This is the only item left in store.\nTherefore, this item can't be deleted.", statusAfterClickedOnButton);
            }
            else
            {
                storeFromCombox.removeItemFromStore(comboBoxItems.getValue().getSerialNumber());
                final ObservableList<AvailableItemInStore> items = FXCollections.observableList(comboBoxStores.getValue().getAvailableItemsList());
                comboBoxItems.setItems(items);
                isItemSelected.setValue(false);
                statusAfterClickedOnButton.setText("Item deleted succesfully");
                statusAfterClickedOnButton.setTextFill(Color.BLACK);
            }
        }

    }

}
