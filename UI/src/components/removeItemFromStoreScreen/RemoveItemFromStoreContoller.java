package components.removeItemFromStoreScreen;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
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

        deleteItemButton.setDisable(true);
        comboBoxItems.setDisable(true);
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

    }

    @FXML
    void chooseStore(ActionEvent event){
        comboBoxItems.setDisable(false);
        final ObservableList<AvailableItemInStore> items = FXCollections.observableList(comboBoxStores.getValue().getItemsList());
        comboBoxItems.setItems(items);
    }

    @FXML
    void chooseItem(ActionEvent event){
        Store storeFromCombox = comboBoxStores.getValue();
        AvailableItemInStore itemFromCombox = comboBoxItems.getValue();
        boolean checkIfItemIsTheOnlyOneInStore;
        boolean onlyCertainStoreSellesItem;
        if(storeFromCombox != null && itemFromCombox != null)
        {
            onlyCertainStoreSellesItem = businessLogic.checkIfOnlyCertainStoreSellesItem(itemFromCombox.getSerialNumber(), storeFromCombox.getSerialNumber());
            checkIfItemIsTheOnlyOneInStore = storeFromCombox.checkIfItemIsTheOnlyOneInStore(itemFromCombox.getSerialNumber());
            if(!onlyCertainStoreSellesItem && !checkIfItemIsTheOnlyOneInStore)
            {
                deleteItemButton.setDisable(false);
            }
        }
    }

    @FXML
    void deleteItem(ActionEvent event)
    {
        comboBoxStores.getValue().removeItemFromStore(comboBoxItems.getValue().getSerialNumber());
        final ObservableList<AvailableItemInStore> items = FXCollections.observableList(comboBoxStores.getValue().getItemsList());
        comboBoxItems.setItems(items);
        deleteItemButton.setDisable(true);
    }

}
