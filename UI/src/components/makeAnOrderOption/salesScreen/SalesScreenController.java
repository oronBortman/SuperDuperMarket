package components.makeAnOrderOption.salesScreen;

import components.makeAnOrderOption.Item.ItemTileController;
import components.makeAnOrderOption.salesOnStoreScreen.SalesOnStoreScreenController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import logic.BusinessLogic;
import logic.common.SuperDuperMarketConstants;
import logic.discount.Discount;
import logic.discount.IfYouBuySDM;
import logic.discount.Offer;
import logic.discount.ThenYouGetSDM;
import logic.order.CustomerOrder.OpenedCustomerOrder;
import logic.order.itemInOrder.OrderedItemFromSale;
import logic.order.itemInOrder.OrderedItemFromStore;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Consumer;

import static commonUI.SuperDuperMarketConstants.SALES_LIST_VIEW;

public class SalesScreenController {

    @FXML private ComboBox<Discount> comboBoxChooseSale;
    @FXML private Button buttonAdd;
    @FXML private ComboBox<Offer> comboBoxChooseItem;
    @FXML private Button buttonNext;
    @FXML private HBox HboxOfListView;

    @FXML private TableView tableViewSales;
    @FXML private TableColumn<OrderedItemFromSale, String> salesTableNameCol;
    @FXML private TableColumn<OrderedItemFromSale, String>salesTableSerialIDCol;
    @FXML private TableColumn<OrderedItemFromSale, String> salesTableAmountCol;
    @FXML private TableColumn<OrderedItemFromSale, String> salesTablePriceCol;
    @FXML private TableColumn<OrderedItemFromSale, String> salesTableTotalPriceCol;
    @FXML private TableColumn<OrderedItemFromSale, String> salesTableSaleNameCol;

    @FXML private TableView tableViewCart;
    @FXML private TableColumn<OrderedItemFromStore, String> cartTableNameCol;
    @FXML private TableColumn<OrderedItemFromStore, String>  cartTablePriceCol;
    @FXML private TableColumn<OrderedItemFromStore, String> cartTableSerialIDCol;
    @FXML private TableColumn<OrderedItemFromStore, String> cartTableAmountCol;
    @FXML private TableColumn<OrderedItemFromStore, String> cartTableTotalPriceCol;

    BusinessLogic businessLogic;
    OpenedCustomerOrder openedCustomerOrder;
    Consumer<Boolean> isNextClickedConsumer;
    ListView<Discount> listView;
    SalesOnStoreScreenController salesOnStoreScreenController;
    SimpleBooleanProperty saleChosen;
    SimpleBooleanProperty saleChosenIsOneOf;
    SimpleBooleanProperty itemChosen;
    SimpleBooleanProperty itemComboBoxIsVisible;
    SimpleBooleanProperty isNextClicked;
    DecimalFormat decimalFormat = new DecimalFormat("#.00");


    public void setProperties(Consumer<Boolean> isNextClicked)
    {
        this.isNextClickedConsumer = isNextClicked;
    }

    public SalesScreenController()
    {
         saleChosen = new SimpleBooleanProperty(false);
         itemChosen = new SimpleBooleanProperty(false);
         itemComboBoxIsVisible = new SimpleBooleanProperty(false);
         saleChosenIsOneOf = new SimpleBooleanProperty(false);
         isNextClicked = new SimpleBooleanProperty(false);;
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void setOpenedCustomerOrder(OpenedCustomerOrder openedCustomerOrder) {
        this.openedCustomerOrder = openedCustomerOrder;
        openedCustomerOrder.initializeAvailableDiscountMapInOpenedStoreOrders();
        openedCustomerOrder.initializeItemsAmountLeftToUseInSalesMapInOpenedStoreOrders();
        try {
            setListViewSales();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setCartTableData();
        setSalesTableData();
        setComboBoxData();
    }

    public void setListViewSales() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL itemFXML = getClass().getResource(SALES_LIST_VIEW);
        loader.setLocation(itemFXML);
        this.listView = loader.load();
        this.salesOnStoreScreenController = loader.getController();
        this.salesOnStoreScreenController.setBusinessLogic(this.businessLogic);
        this.salesOnStoreScreenController.setDiscounts(openedCustomerOrder.generateListOfDiscounts());
        HboxOfListView.getChildren().setAll(listView);
    }

    public void initializeComboBoxSales()
    {
        comboBoxChooseSale.setConverter(new StringConverter<Discount>() {
            @Override
            public String toString(Discount object) {
                return object.getName();
            }

            @Override
            public Discount fromString(String string) {
                return comboBoxChooseSale.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });

    }

    public void initializeComboBoxItems()
    {
        comboBoxChooseItem.setConverter(new StringConverter<Offer>() {
            @Override
            public String toString(Offer object) {
                return object.getQuantity() + " of " + businessLogic.getItemBySerialID(object.getItemId()).getName() + " for " + object.getForAdditional() + "$";
            }

            @Override
            public Offer fromString(String string) {
                return null;
            }
        });


        comboBoxChooseItem.visibleProperty().bind(
                Bindings.and(
                        saleChosen,
                        saleChosenIsOneOf
                )
        );

        itemComboBoxIsVisible.bind(comboBoxChooseItem.visibleProperty());
    }

    public void setComboBoxData()
    {
        final ObservableList<Discount> listOfDiscounts = FXCollections.observableList(openedCustomerOrder.generateListOfDiscounts());
        comboBoxChooseSale.setItems(listOfDiscounts);
    }

    public void initialize() {

        initializeComboBoxSales();
        initializeComboBoxItems();
        initializeSalesCols();
        initializetCartCols();

        buttonAdd.disableProperty().bind(
                Bindings.or(
                        saleChosen.not(),
                        comboBoxChooseItem.visibleProperty().and(itemChosen.not())));


    }


    @FXML
    void addAction(ActionEvent event)
    {
        Discount discount = comboBoxChooseSale.getValue();
        System.out.println("Clicked on add action");

        if(discount != null)
        {
            String operator = discount.getThenYouGet().getOperator();
            System.out.println("discount not null");
            if(operator.equals(SuperDuperMarketConstants.ONE_OF))
            {
                Offer offer = comboBoxChooseItem.getValue();
                if(offer != null)
                {
                    System.out.println("ONE-OF!!!!!");
                    openedCustomerOrder.applyDiscountOneOf(discount.getName(), offer);
                }
                saleChosen.set(false);
                saleChosenIsOneOf.set(false);
            }
            else if(operator.equals(SuperDuperMarketConstants.ALL_OR_NOTHING) || operator.equals("IRRELEVANT"))
            {
                System.out.println("ALL-OR-NOTHING!!!!!");
                openedCustomerOrder.applyDiscountAllOrNothing(discount.getName());
            }
            else
            {

            }
        }
        this.salesOnStoreScreenController.setDiscounts(openedCustomerOrder.generateListOfDiscounts());
        setSalesTableData();
        setComboBoxData();
        if(listView.getItems().isEmpty())
        {

            buttonAdd.disableProperty().unbind();
            buttonAdd.setDisable(true);
            comboBoxChooseSale.disableProperty().unbind();
            comboBoxChooseSale.setDisable(true);
            for(Discount discount1 : listView.getItems())
            {
                System.out.println(discount.getName());
            }
            System.out.println("empty!!!");

        }
    }

    void setSalesTableData()
    {
        final ObservableList<OrderedItemFromSale> orderedItemFromSaleList = FXCollections.observableList(openedCustomerOrder.generateListOfOrderedItemFromSaleWithDiscountName());
        tableViewSales.setItems(orderedItemFromSaleList);
    }
    void initializetCartCols()
    {
        cartTableNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItemFromStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItemFromStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getName());
            }
        });

        cartTableSerialIDCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItemFromStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItemFromStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getSerialNumber().toString());
            }
        });

        cartTableAmountCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItemFromStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItemFromStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(decimalFormat.format(param.getValue().getTotalAmountOfItemOrderedByTypeOfMeasure()));
            }
        });

        cartTablePriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItemFromStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItemFromStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(decimalFormat.format(param.getValue().getPricePerUnit()));
            }
        });
        cartTableTotalPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItemFromStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItemFromStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(decimalFormat.format(param.getValue().getTotalPriceOfItemOrderedByTypeOfMeasure()));
            }
        });
    }

    void initializeSalesCols()
    {
        salesTableNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItemFromSale, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItemFromSale, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getOrderedItemFromStore().getName());
            }
        });
        salesTableSerialIDCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItemFromSale, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItemFromSale, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getOrderedItemFromStore().getSerialNumber().toString());
            }
        });
        salesTableAmountCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItemFromSale, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItemFromSale, String> param) {
                return new ReadOnlyObjectWrapper<String>(decimalFormat.format(param.getValue().getOrderedItemFromStore().getTotalAmountOfItemOrderedByTypeOfMeasure()));
            }
        });
        salesTablePriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItemFromSale, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItemFromSale, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getOrderedItemFromStore().getPricePerUnit().toString());
            }
        });
        salesTableTotalPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItemFromSale, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItemFromSale, String> param) {
                return new ReadOnlyObjectWrapper<String>(decimalFormat.format(param.getValue().getOrderedItemFromStore().getTotalPriceOfItemOrderedByTypeOfMeasure()));
            }
        });
        salesTableSaleNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItemFromSale, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItemFromSale, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getSaleName());
            }
        });


    }

    void setCartTableData()
    {
        final ObservableList<OrderedItemFromStore> dataOfItems = FXCollections.observableList(openedCustomerOrder.generateListsOfItemNotFromSale());
        tableViewCart.setItems(dataOfItems);
    }

    @FXML
    void chooseItem(ActionEvent event)
    {
        itemChosen.set(true);
    }

    @FXML
    void chooseSale(ActionEvent event)
    {
        if(comboBoxChooseSale.getValue() != null)
        {
            saleChosen.set(true);
            Discount discount = comboBoxChooseSale.getValue();
            if(discount.getThenYouGet().getOperator().equals(SuperDuperMarketConstants.ONE_OF))
            {
                saleChosenIsOneOf.set(true);
                comboBoxChooseItem.setItems(FXCollections.observableList(discount.getThenYouGet().getOfferList()));

            }
            else
            {
                saleChosenIsOneOf.set(false);
            }
        }
    }
    @FXML
    void clickedOnNextButton(ActionEvent event)
    {
        isNextClicked.set(true);
        isNextClickedConsumer.accept(true);
    }

}
