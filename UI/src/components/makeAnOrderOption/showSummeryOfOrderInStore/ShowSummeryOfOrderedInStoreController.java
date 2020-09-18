package components.makeAnOrderOption.showSummeryOfOrderInStore;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import logic.BusinessLogic;
import logic.order.StoreOrder.StoreOrder;
import logic.order.itemInOrder.OrderedItem;
import logic.order.itemInOrder.OrderedItemFromSale;
import logic.order.itemInOrder.OrderedItemFromStore;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class ShowSummeryOfOrderedInStoreController {

    @FXML TableView tableViewOfItems;
    @FXML private TableColumn<OrderedItem, String> SerialIDCol;
    @FXML private TableColumn<OrderedItem, String> nameCol;
    @FXML private TableColumn<OrderedItem, String> measureTypeCol;
    @FXML private TableColumn<OrderedItem, String> amountCol;
    @FXML private TableColumn<OrderedItem, String> pricePerUnitCol;
    @FXML private TableColumn<OrderedItem, String> totalPriceCol;
    @FXML private TableColumn<OrderedItem, String> boughtOnSaleCol;
    @FXML private Label LabelNameOfStore;
    @FXML private Label LabelSerialID;
    @FXML private Label LabelPPK;
    @FXML private Label LabelDistanceToCustomer;
    @FXML private Label LabelDeliveryCost;
    @FXML private Label LabelDate;
    BusinessLogic businessLogic;
    StoreOrder storeOrder;

    DecimalFormat decimalFormat = new DecimalFormat("#.00");

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void setStoreOrder(StoreOrder storeOrder) {
        this.storeOrder = storeOrder;
        setLabelSerialID();
        setLabelNameOfStore();
        setLabelPPK();
        setLabelDistanceToCustomer();
        setLabelDeliveryCost();
        setLabelDate();
        setDataOnItemsTable();
    }

    private void setLabelDeliveryCost() {
        LabelDeliveryCost.setText(decimalFormat.format(storeOrder.calcTotalDeliveryPrice()));
    }

    private void setLabelDistanceToCustomer() {
        LabelDistanceToCustomer.setText(decimalFormat.format(storeOrder.calcDistanceToCustomer()));
    }

    private void setLabelPPK() {
        LabelPPK.setText(storeOrder.getStoreUsed().getPPK().toString());
    }

    private void setLabelSerialID() {
        LabelSerialID.setText(storeOrder.getStoreUsed().getSerialNumber().toString());
    }

    private void setLabelNameOfStore() {
        LabelNameOfStore.setText(storeOrder.getStoreUsed().getName());
    }

    private void setLabelDate() {LabelDate.setText(storeOrder.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yy")));}

    @FXML
    void initialize()
    {
        initializeItemsTable();
    }

    void initializeItemsTable()
    {
        SerialIDCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItem, String> param) {
                ReadOnlyObjectWrapper<String> readOnlyObjectWrapper=null;
                if(param.getValue() instanceof OrderedItemFromSale)
                {
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>(((OrderedItemFromSale)param.getValue()).getOrderedItemFromStore().getSerialNumber().toString());
                }
                else if (param.getValue() instanceof  OrderedItemFromStore) {
                    readOnlyObjectWrapper = new  ReadOnlyObjectWrapper<String>(((OrderedItemFromStore)param.getValue()).getSerialNumber().toString());
                }
                return readOnlyObjectWrapper;
            }
        });
        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItem, String> param) {
                ReadOnlyObjectWrapper<String> readOnlyObjectWrapper=null;
                if(param.getValue() instanceof OrderedItemFromSale)
                {
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>(((OrderedItemFromSale)param.getValue()).getOrderedItemFromStore().getName());
                }
                else if (param.getValue() instanceof OrderedItemFromStore) {
                    readOnlyObjectWrapper = new  ReadOnlyObjectWrapper<String>(((OrderedItemFromStore)param.getValue()).getName());
                }
                return readOnlyObjectWrapper;
            }
        });
        measureTypeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItem, String> param) {
                ReadOnlyObjectWrapper<String> readOnlyObjectWrapper=null;
                if(param.getValue() instanceof OrderedItemFromSale)
                {
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>(((OrderedItemFromSale)param.getValue()).getOrderedItemFromStore().getTypeOfMeasureStr());
                }
                else if (param.getValue() instanceof  OrderedItemFromStore) {
                    readOnlyObjectWrapper = new  ReadOnlyObjectWrapper<String>(((OrderedItemFromStore)param.getValue()).getTypeOfMeasureStr());
                }
                return readOnlyObjectWrapper;
            }
        });
        amountCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItem, String> param) {
                ReadOnlyObjectWrapper<String> readOnlyObjectWrapper=null;
                if(param.getValue() instanceof OrderedItemFromSale)
                {
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>(decimalFormat.format(((OrderedItemFromSale)param.getValue()).getOrderedItemFromStore().getTotalAmountOfItemOrderedByTypeOfMeasure()));
                }
                else if (param.getValue() instanceof OrderedItemFromStore) {
                    readOnlyObjectWrapper = new  ReadOnlyObjectWrapper<String>(decimalFormat.format(((OrderedItemFromStore)param.getValue()).getTotalAmountOfItemOrderedByTypeOfMeasure()));
                }
                return readOnlyObjectWrapper;
            }
        });
        pricePerUnitCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItem, String> param) {
                ReadOnlyObjectWrapper<String> readOnlyObjectWrapper=null;
                if(param.getValue() instanceof OrderedItemFromSale)
                {
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>(decimalFormat.format(((OrderedItemFromSale)param.getValue()).getOrderedItemFromStore().getPricePerUnit()));
                }
                else if (param.getValue() instanceof OrderedItemFromStore) {
                    readOnlyObjectWrapper = new  ReadOnlyObjectWrapper<String>(decimalFormat.format(((OrderedItemFromStore)param.getValue()).getPricePerUnit()));
                }
                return readOnlyObjectWrapper;
            }
        });
        totalPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItem, String> param) {
                ReadOnlyObjectWrapper<String> readOnlyObjectWrapper=null;
                if(param.getValue() instanceof OrderedItemFromSale)
                {
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>(decimalFormat.format(((OrderedItemFromSale)param.getValue()).getOrderedItemFromStore().getTotalPriceOfItemOrderedByTypeOfMeasure()));
                }
                else if (param.getValue() instanceof  OrderedItemFromStore) {
                    readOnlyObjectWrapper = new  ReadOnlyObjectWrapper<String>(decimalFormat.format(((OrderedItemFromStore)param.getValue()).getTotalPriceOfItemOrderedByTypeOfMeasure()));
                }
                return readOnlyObjectWrapper;
            }
        });
        boughtOnSaleCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItem, String> param) {
                ReadOnlyObjectWrapper<String> readOnlyObjectWrapper=null;
                if(param.getValue() instanceof  OrderedItemFromSale)
                {
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>("YES");
                }
                else if (param.getValue() instanceof  OrderedItemFromStore) {
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>("NO");
                }
                return readOnlyObjectWrapper;
            }
        });
    }

    public void setDataOnItemsTable()
    {
        final ObservableList<OrderedItem> dataOfItems = FXCollections.observableList(storeOrder.generateListOfGeneralOrderedItems());
        tableViewOfItems.setItems(dataOfItems);
    }


}