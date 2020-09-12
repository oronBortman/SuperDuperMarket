package components.showStoreStatusInDynamicOrder;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import logic.BusinessLogic;
import logic.SDMLocation;
import logic.Store;
import logic.order.CustomerOrder.OpenedCustomerOrder;
import logic.order.StoreOrder.OpenedStoreOrder;

public class ShowStoresStatusInDynamicOrderController {


    @FXML
    GridPane showItemsGridPane;
    @FXML
    TableView listOfItemsTable;
    BusinessLogic businessLogic;
    @FXML TableColumn<OpenedStoreOrder, String> StoreSerialNumberCol;
    @FXML TableColumn<OpenedStoreOrder, String> NameOfStoreCol;
    @FXML TableColumn<OpenedStoreOrder, String> locationCol;
    @FXML TableColumn<OpenedStoreOrder, String> DistanceFromCustomerCol;
    @FXML TableColumn<OpenedStoreOrder, String>  PPKCol;
    @FXML TableColumn<OpenedStoreOrder, String> deliveryCostCol;
    @FXML TableColumn<OpenedStoreOrder, String>  amountOfItemsPurchasedCol;
    @FXML TableColumn<OpenedStoreOrder, String>  totalPriceOfItemsCol;

    OpenedCustomerOrder openedCustomerOrder;

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

    public ShowStoresStatusInDynamicOrderController()
    {

    }

    public void setOpenedCustomerOrder(OpenedCustomerOrder openedCustomerOrder) {
        this.openedCustomerOrder = openedCustomerOrder;
    }

    private void setItemsTable()
    {
        if(openedCustomerOrder.getOpenedStoresOrderMap() == null)
        {
            System.out.println("nullll");
        }
        else
        {
            System.out.println("not nullll");

        }
        for(OpenedStoreOrder openedStoreOrder : openedCustomerOrder.getOpenedStoresOrderMap().values())
        {
            System.out.println("Bbbbb");

            System.out.println(openedStoreOrder.getStoreUsed().getName());
        }
        final ObservableList<OpenedStoreOrder> dataOfItems = FXCollections.observableList(openedCustomerOrder.getListOfOpenedStoreOrder());

        StoreSerialNumberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OpenedStoreOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OpenedStoreOrder, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getStoreUsed().getSerialNumber().toString());
            }
        });
        NameOfStoreCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OpenedStoreOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OpenedStoreOrder, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getStoreUsed().getName());
            }
        });


        locationCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OpenedStoreOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OpenedStoreOrder, String> param) {
                SDMLocation locationOfShop = param.getValue().getStoreUsed().getLocationOfShop();
                return new ReadOnlyObjectWrapper<String>("(" + locationOfShop.getX() + "," + locationOfShop.getY() + ")");
            }
        });
        DistanceFromCustomerCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OpenedStoreOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OpenedStoreOrder, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getStoreUsed().getLocationOfShop().getAirDistanceToOtherLocation(openedCustomerOrder.getCustomerLocation()).toString());
            }
        });

        PPKCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OpenedStoreOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OpenedStoreOrder, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getStoreUsed().getPPK().toString());
            }
        });

        deliveryCostCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OpenedStoreOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OpenedStoreOrder, String> param) {
                Store store = param.getValue().getStoreUsed();
                SDMLocation storeLocation = store.getLocationOfShop();
                SDMLocation customerLocation = openedCustomerOrder.getCustomerLocation();
                return new ReadOnlyObjectWrapper<String>(param.getValue().calcTotalDeliveryPrice(customerLocation).toString());
            }
        });

        amountOfItemsPurchasedCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OpenedStoreOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OpenedStoreOrder, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().calcTotalAmountOfItemsType().toString());
            }
        });


        totalPriceOfItemsCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OpenedStoreOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OpenedStoreOrder, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().calcTotalPriceOfItems().toString());
            }
        });

        listOfItemsTable.setItems(dataOfItems);

    }
}
