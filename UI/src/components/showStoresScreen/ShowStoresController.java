package components.showStoresScreen;


import exceptions.DuplicateSerialIDException;
import exceptions.SerialIDNotExistException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import logic.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javax.xml.bind.JAXBException;

public class ShowStoresController {

    @FXML GridPane showStoresGridPane;
    @FXML ComboBox<Store> comboBoxStores;
    @FXML Label serialNumberValue;
    @FXML Label nameOfStoreValue;
    @FXML Label PPKValue;
    @FXML TableView listOfItemsTable;
    @FXML TableColumn<AvailableItemInStore, Integer> ItemSerialNumberCol;
    @FXML TableColumn<AvailableItemInStore, String> NameOfItemCol;
    @FXML TableColumn<AvailableItemInStore, String> TypeOfMeasureCol;
    @FXML TableColumn<AvailableItemInStore, Integer> PricePerUnitCol;
    @FXML TableColumn<AvailableItemInStore, String> TotalItemsSoledInStoreCol;

    BusinessLogic businessLogic;

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

        comboBoxStores.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
            {
                serialNumberValue.setText(newval.getSerialNumber().toString());
                nameOfStoreValue.setText(newval.getName());
                PPKValue.setText(newval.getPPK().toString());
                // totalPaymentForDeliveriesVal.setText(newval.get);
            }
        });
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        final ObservableList<Store> stores = FXCollections.observableList(businessLogic.getStoresList());
        comboBoxStores.setItems(stores);
        //TODO
        //Change xml loading
    }

    public ShowStoresController()
    {

    }

    @FXML
    void chooseStore(ActionEvent event){

        setItemsTable();
        setOrdersTable();

    }

    private void setItemsTable()
    {
        final ObservableList<AvailableItemInStore> dataOfItems = FXCollections.observableList(comboBoxStores.getValue().getAvailableItemsList());

        ItemSerialNumberCol.setCellValueFactory(new PropertyValueFactory<AvailableItemInStore, Integer>("serialNumber"));
        NameOfItemCol.setCellValueFactory(new PropertyValueFactory<AvailableItemInStore, String>("name"));
        PricePerUnitCol.setCellValueFactory(new PropertyValueFactory<AvailableItemInStore, Integer>("pricePerUnit"));
        TotalItemsSoledInStoreCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AvailableItemInStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<AvailableItemInStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(businessLogic.getTotalAmountOfSoledItem(param.getValue().getSerialNumber()).toString());
            }
        });

        TypeOfMeasureCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AvailableItemInStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<AvailableItemInStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getTypeOfMeasureStr());
            }
        });
        listOfItemsTable.setItems(dataOfItems);
    }

    private void setOrdersTable()
    {
        /*final ObservableList<Item> dataOfItems = FXCollections.observableList(comboBoxStores.getValue().getItemsList());

        for(TableColumn tableColumn : (ObservableList<TableColumn>)listOfItemsTable.getColumns())
        {
            if(tableColumn.getId().equals("itemSerialNumberCol"))
            {
                System.out.println(tableColumn.getId());
                tableColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("serialNumber"));
            }
            if(tableColumn.getId().equals("NameOfItemCol"))
            {
                System.out.println(tableColumn.getId());
                tableColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
            }
        }
        listOfItemsTable.setItems(dataOfItems);*/
    }

}
