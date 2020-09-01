package components.showStoresScreen;


import exceptions.DuplicateSerialIDException;
import exceptions.SerialIDNotExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import logic.*;
import InterfaceConsole.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javax.xml.bind.JAXBException;

public class ShowStoresController {

    @FXML GridPane showStoresGridPane;
    @FXML ComboBox<Store> comboBoxStores;
    @FXML Label serialNumberValue;
    @FXML Label nameOfStoreValue;
    @FXML Label PPKValue;
    @FXML TableView listOfItemsTable;
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
        final ObservableList<Item> dataOfItems = FXCollections.observableList(comboBoxStores.getValue().getItemsList());

        for(TableColumn tableColumn : (ObservableList<TableColumn>)listOfItemsTable.getColumns())
        {
            if(tableColumn.getId().equals("itemSerialNumberCol"))
            {
                tableColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("serialNumber"));
            }
            if(tableColumn.getId().equals("NameOfItemCol"))
            {
                tableColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
            }
        }
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
