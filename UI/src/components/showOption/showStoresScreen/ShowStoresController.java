package components.showOption.showStoresScreen;


import components.makeAnOrderOption.MakeAnOrder.MakeAnOrderController;
import components.makeAnOrderOption.salesOnStoreScreen.SalesOnStoreScreenController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import logic.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TableColumn.CellDataFeatures;
import logic.order.StoreOrder.ClosedStoreOrder;
import logic.order.StoreOrder.StoreOrder;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import static commonUI.SuperDuperMarketConstants.SALES_LIST_VIEW;

public class ShowStoresController {

    @FXML GridPane showStoresGridPane;
    @FXML ComboBox<Store> comboBoxStores;
    @FXML Label serialNumberValue;
    @FXML Label nameOfStoreValue;
    @FXML Label PPKValue;
    @FXML TableView listOfItemsTable;
    @FXML HBox hboxSales;
    @FXML Label totalPaymentForDeliveriesVal;
    @FXML TableColumn<AvailableItemInStore, String> ItemSerialNumberCol;
    @FXML TableColumn<AvailableItemInStore, String> NameOfItemCol;
    @FXML TableColumn<AvailableItemInStore, String> TypeOfMeasureCol;
    @FXML TableColumn<AvailableItemInStore, String> PricePerUnitCol;
    @FXML TableColumn<AvailableItemInStore, String> TotalItemsSoledInStoreCol;

    @FXML TableView listOfOrdersTable;
    @FXML TableColumn<ClosedStoreOrder, String> dateCol;
    @FXML TableColumn<ClosedStoreOrder, String> totalItemsCol;
    @FXML TableColumn<ClosedStoreOrder, String> totalItemsPriceCol;
    @FXML TableColumn<ClosedStoreOrder, String> totalDeliveryPriceCol;
    @FXML TableColumn<ClosedStoreOrder, String> totalOrderPriceCol;
    ListView listView;
    SalesOnStoreScreenController salesOnStoreScreenController;
    BusinessLogic businessLogic;

    DecimalFormat decimalFormat = new DecimalFormat("#.00");

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
                totalPaymentForDeliveriesVal.setText(decimalFormat.format(newval.getOrdersList().stream().mapToDouble(StoreOrder::calcTotalDeliveryPrice).sum()));
            }
        });

        initializeItemsTable();
        initializeOrdersTable();
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        final ObservableList<Store> stores = FXCollections.observableList(businessLogic.getStoresList());
        comboBoxStores.setItems(stores);
        try {
            setListViewSales();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setListViewSales() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL itemFXML = getClass().getResource(SALES_LIST_VIEW);
        loader.setLocation(itemFXML);
        this.listView = loader.load();
        this.salesOnStoreScreenController = loader.getController();
        this.salesOnStoreScreenController.setBusinessLogic(this.businessLogic);
        hboxSales.getChildren().setAll(listView);
    }

    public ShowStoresController()
    {

    }

    @FXML
    void chooseStore(ActionEvent event)
    {
        setItemsTable();
        setOrdersTable();
        System.out.println("AAAA");
        this.salesOnStoreScreenController.setDiscounts(comboBoxStores.getValue().getDiscountsList());

    }

    private void initializeItemsTable()
    {
        System.out.println("BBBBBBBB");
        ItemSerialNumberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AvailableItemInStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<AvailableItemInStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getSerialNumber().toString());
            }
        });
        NameOfItemCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AvailableItemInStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<AvailableItemInStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getName());
            }
        });
        PricePerUnitCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AvailableItemInStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<AvailableItemInStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getPricePerUnit().toString());
            }
        });
        TotalItemsSoledInStoreCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AvailableItemInStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<AvailableItemInStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(decimalFormat.format(businessLogic.getTotalAmountOfSoledItem(param.getValue().getSerialNumber())));
            }
        });

        TypeOfMeasureCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AvailableItemInStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<AvailableItemInStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getTypeOfMeasureStr());
            }
        });
    }

    private void initializeOrdersTable()
    {
        //TODO
        //SetDate
        dateCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClosedStoreOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ClosedStoreOrder, String> param) {
                MakeAnOrderController closedCustomerOrder;
                return new ReadOnlyObjectWrapper<String>(param.getValue().getDate().format(DateTimeFormatter.ofPattern("dd.MM.yy")));
            }
        });

        //TODO
        //Check if its ok
        totalItemsCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClosedStoreOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ClosedStoreOrder, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().calcTotalAmountOfItemsByUnit().toString());
            }
        });

        totalItemsPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClosedStoreOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ClosedStoreOrder, String> param) {
                return new ReadOnlyObjectWrapper<String>(decimalFormat.format(param.getValue().calcTotalPriceOfItems()));
            }
        });

        totalDeliveryPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClosedStoreOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ClosedStoreOrder, String> param) {
                return new ReadOnlyObjectWrapper<String>(decimalFormat.format(param.getValue().calcTotalDeliveryPrice()));
            }
        });

        totalOrderPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClosedStoreOrder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ClosedStoreOrder, String> param) {
                return new ReadOnlyObjectWrapper<String>(decimalFormat.format(param.getValue().calcTotalPriceOfOrder()));
            }
        });
    }

    private void setItemsTable()
    {
        final ObservableList<AvailableItemInStore> dataOfItems = FXCollections.observableList(comboBoxStores.getValue().getAvailableItemsList());
        for( AvailableItemInStore availableItemInStore : comboBoxStores.getValue().getAvailableItemsList())
        {
            System.out.println(availableItemInStore.getName());
        }
        listOfItemsTable.setItems(dataOfItems);
    }

    private void setOrdersTable()
    {
        final ObservableList<ClosedStoreOrder> dataOfOrders = FXCollections.observableList(comboBoxStores.getValue().getOrdersList());
        listOfOrdersTable.setItems(dataOfOrders);

    }
}
