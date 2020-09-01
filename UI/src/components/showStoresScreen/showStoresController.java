package components.showStoresScreen;

import exceptions.DuplicateSerialIDException;
import exceptions.SerialIDNotExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.*;
import InterfaceConsole.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class showStoresController {

    @FXML
    GridPane showStoresGridPane;
    ComboBox<Store> comboBoxStores;
    Label serialNumberValue;
    Label nameOfStoreValue;

    @FXML
    void chooseStore(ActionEvent event) throws SerialIDNotExistException, JAXBException, DuplicateSerialIDException {

        Base base=new Base();
        new MenuOptionForReadingXMLFile().readFromXMLFile(base, common.SuperDuperMarketConstants.XML_PATH);
        comboBoxStores = (ComboBox<Store>) showStoresGridPane.lookup("#comboBoxStores");
        final ObservableList<Item> dataOfItems = FXCollections.observableList(comboBoxStores.getValue().getItemsList());
        TableView listOfItemsTable = (TableView) showStoresGridPane.lookup("#listOfItemsTable");

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
        listOfItemsTable.setItems(dataOfItems);

    }

}
