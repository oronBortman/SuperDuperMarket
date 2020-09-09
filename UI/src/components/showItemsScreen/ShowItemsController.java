package components.showItemsScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import logic.BusinessLogic;
import logic.Item;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.beans.property.ReadOnlyObjectWrapper;


public class ShowItemsController {


    @FXML GridPane showItemsGridPane;
    @FXML TableView listOfItemsTable;
    BusinessLogic businessLogic;
    @FXML TableColumn<Item, Integer> ItemSerialNumberCol;
    @FXML TableColumn<Item, String> NameOfItemCol;
    @FXML TableColumn<Item, String> AveragePriceCol;
    @FXML TableColumn<Item, String> AmountOfStoresSellesItemCol;
    @FXML TableColumn<Item, String>  AmountSoledFromItemCol;
    @FXML TableColumn<Item, String> TypeOfMeasureCol;

    @FXML
    private void initialize() {
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        setItemsTable();
        //final ObservableList<Store> stores = FXCollections.observableList(businessLogic.getStoresList());
        //TODO
        //Change xml loading
    }

    public ShowItemsController()
    {

    }

    private void setItemsTable()
    {
        final ObservableList<Item> dataOfItems = FXCollections.observableList(businessLogic.getItemsList());
        ItemSerialNumberCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("serialNumber"));
        NameOfItemCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        AveragePriceCol.setCellValueFactory(new Callback<CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Item, String> param) {
                return new ReadOnlyObjectWrapper<String>(businessLogic.getAvgPriceOfItemInSDK(param.getValue().getSerialNumber()).toString());
            }
        });

        AmountOfStoresSellesItemCol.setCellValueFactory(new Callback<CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Item, String> param) {
                return new ReadOnlyObjectWrapper<String>(businessLogic.getHowManyShopsSellesAnItem(param.getValue().getSerialNumber()).toString());
            }
        });

        AmountSoledFromItemCol.setCellValueFactory(new Callback<CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Item, String> param) {
                return new ReadOnlyObjectWrapper<String>(businessLogic.getTotalAmountOfSoledItem(param.getValue().getSerialNumber()).toString());
            }
        });

        TypeOfMeasureCol.setCellValueFactory(new Callback<CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Item, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getTypeOfMeasureStr());
            }
        });

        listOfItemsTable.setItems(dataOfItems);

    }


}
