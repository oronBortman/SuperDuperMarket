package components.makeAnOrderOption.salesScreen;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

import java.util.List;
import java.util.function.Consumer;

public class SalesScreenControllerBackupNew {

    @FXML private ListView<Discount> listViewSales;
    @FXML private ComboBox<Discount> comboBoxChooseSale;
    @FXML private Button buttonAdd;
    @FXML private ComboBox<Offer> comboBoxChooseItem;
    @FXML private Button buttonNext;

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

    SimpleBooleanProperty saleChosen;
    SimpleBooleanProperty saleChosenIsOneOf;
    SimpleBooleanProperty itemChosen;
    SimpleBooleanProperty itemComboBoxIsVisible;
    SimpleBooleanProperty isNextClicked;


    public void setProperties(Consumer<Boolean> isNextClicked)
    {
        this.isNextClickedConsumer = isNextClicked;
    }

    public SalesScreenControllerBackupNew()
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
        setDiscounts(openedCustomerOrder.generateListOfDiscounts());
        setCartTableData();
        setSalesTableData();
    }

    public void setDiscounts(List<Discount> discountList)
    {
        listViewSales.getItems().clear();
        comboBoxChooseSale.getItems().clear();
        for(Discount discount : discountList)
        {
            listViewSales.getItems().add(discount);
            comboBoxChooseSale.getItems().add(discount);
        }
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

    public void initializeListViewSales()
    {
        listViewSales.setCellFactory(param -> new ListCell<Discount>() {
            @Override
            protected void updateItem(Discount discount, boolean empty) {
                if (empty || discount == null || discount.getName() == null) {
                    setText(null);
                } else {
                    super.updateItem(discount, empty);
                    IfYouBuySDM ifYouBuySDM = discount.getIfYouBuySDM();
                    Double quantity = ifYouBuySDM.getQuantity();
                    int itemID = ifYouBuySDM.getItemId();
                    ThenYouGetSDM thenYouGetSDM = discount.getThenYouGet();
                    List<Offer> offerList= thenYouGetSDM.getOfferList();
                    String operator = thenYouGetSDM.getOperator();
                    setText(message(discount.getName(), quantity, itemID, offerList, operator));
                }
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

    public void initialize() {

        initializeListViewSales();
        initializeComboBoxSales();
        initializeComboBoxItems();
        initializeSalesCols();
        initializetCartCols();

        buttonAdd.disableProperty().bind(
                Bindings.or(
                        saleChosen.not(),
                        comboBoxChooseItem.visibleProperty().and(itemChosen.not())));


    }

    private String message(String nameOfSale, Double quantity, Integer itemID, List<Offer> offerList, String operator)
    {
        String message = "Name of sale: " + nameOfSale + "\n" +
                "Buy: " + quantity + " of " + businessLogic.getItemBySerialID(itemID).getName() + "\n" +
                "Get:\n";
        message = message.concat(getStringByOperator(offerList, operator));
        return message;
    }

    private String getStringByOperator(List<Offer> offerList, String operator)
    {
        String allOrNothingOffers = "";
        for(Offer offer : offerList)
        {
            if(offer.equals(offerList.get(0)) == false)
            {
                allOrNothingOffers = allOrNothingOffers.concat("         ");
                if(operator.equals(SuperDuperMarketConstants.ALL_OR_NOTHING)) {
                    allOrNothingOffers = allOrNothingOffers.concat("AND\n");
                }
                else if(operator.equals(SuperDuperMarketConstants.ONE_OF)) {
                    allOrNothingOffers = allOrNothingOffers.concat("OR\n");
                }

            }
            allOrNothingOffers = allOrNothingOffers.concat(offer.getQuantity() + " amount of " + businessLogic.getItemBySerialID(offer.getItemId()).getName() + " for " + offer.getForAdditional() + "\n");
        }
        return  allOrNothingOffers;
    }

    @FXML
    void addAction(ActionEvent event)
    {
        Discount discount = comboBoxChooseSale.getValue();
        if(discount != null)
        {
            String operator = discount.getThenYouGet().getOperator();
            if(operator.equals(SuperDuperMarketConstants.ONE_OF))
            {
                Offer offer = comboBoxChooseItem.getValue();
                if(offer != null)
                {
                    openedCustomerOrder.applyDiscountOneOf(discount.getName(), offer);
                }
                saleChosen.set(false);
                saleChosenIsOneOf.set(false);
            }
            else if(operator.equals(SuperDuperMarketConstants.ALL_OR_NOTHING) || operator.equals("IRRELEVANT"))
            {
                openedCustomerOrder.applyDiscountAllOrNothing(discount.getName());
            }
        }
        setDiscounts(openedCustomerOrder.generateListOfDiscounts());
        setSalesTableData();
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
                return new ReadOnlyObjectWrapper<String>(param.getValue().getTotalAmountOfItemOrderedByTypeOfMeasure().toString());
            }
        });

        cartTablePriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItemFromStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItemFromStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getPricePerUnit().toString());
            }
        });
        cartTableTotalPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderedItemFromStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderedItemFromStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getTotalPriceOfItemOrderedByTypeOfMeasure().toString());
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
                return new ReadOnlyObjectWrapper<String>(param.getValue().getOrderedItemFromStore().getTotalAmountOfItemOrderedByTypeOfMeasure().toString());
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
                return new ReadOnlyObjectWrapper<String>(param.getValue().getOrderedItemFromStore().getTotalPriceOfItemOrderedByTypeOfMeasure().toString());
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
