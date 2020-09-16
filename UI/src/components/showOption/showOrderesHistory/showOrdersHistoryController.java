package components.showOption.showOrderesHistory;

import commonUI.SuperDuperMarketConstants;
import components.makeAnOrderOption.ShowSummeryOfOrderInStore.ShowSummeryOfOrderedInStoreController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.util.StringConverter;
import logic.BusinessLogic;
import logic.order.CustomerOrder.ClosedCustomerOrder;
import logic.order.StoreOrder.ClosedStoreOrder;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import logic.order.itemInOrder.OrderedItem;

public class showOrdersHistoryController {
    @FXML private FlowPane flowPaneStores;
    @FXML private ComboBox<ClosedCustomerOrder> comboBoxChooseOrder;
    @FXML private Label LabelTotalItemsCost;
    @FXML private Label LabelTotalDeliveryPrice;
    @FXML private Label LabelTotalOrderPrice;

    private Map<ClosedStoreOrder, ShowSummeryOfOrderedInStoreController> itemToTileControlleresMap;
    private BusinessLogic businessLogic;
    private ClosedCustomerOrder closedCustomerOrder;
    DecimalFormat decimalFormat = new DecimalFormat("#.00");

    @FXML
    void chooseOrder(ActionEvent event) {
        try {
            setClosedCustomerOrder(comboBoxChooseOrder.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize()
    {
        initializeComboBoxOrders();
    }

    public void setLabelTotalItemsCost()
    {
        LabelTotalItemsCost.setText(decimalFormat.format(closedCustomerOrder.getTotalItemCostInOrder()));
    }

    public void setLabelTotalDeliveryPrice()
    {
        LabelTotalDeliveryPrice.setText(decimalFormat.format(closedCustomerOrder.getTotalDeliveryPriceInOrder()));
    }

    public void setLabelTotalOrderPrice()
    {
        LabelTotalOrderPrice.setText(decimalFormat.format(closedCustomerOrder.getTotalOrderPrice()));
    }

    public void setFlowPaneStores()
    {

    }

    public void initializeComboBoxOrders()
    {
        comboBoxChooseOrder.setConverter(new StringConverter<ClosedCustomerOrder>() {
            @Override
            public String toString(ClosedCustomerOrder object) {
                return "Order number: " + object.getSerialNumber().toString();
            }

            @Override
            public ClosedCustomerOrder fromString(String string) {
                return null;
            }
        });

    }

    public showOrdersHistoryController()
    {
        itemToTileControlleresMap = new HashMap<ClosedStoreOrder, ShowSummeryOfOrderedInStoreController>();
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        final ObservableList<ClosedCustomerOrder> dataOfOrders = FXCollections.observableList(businessLogic.getClosedCustomerOrderList());
        for(ClosedCustomerOrder closedCustomerOrder: businessLogic.getClosedCustomerOrderList())
        {
            System.out.println(closedCustomerOrder.getSerialNumber());
        }
        comboBoxChooseOrder.setItems(dataOfOrders);
    }

    public void setClosedCustomerOrder(ClosedCustomerOrder closedCustomerOrder) throws IOException {
        this.closedCustomerOrder = closedCustomerOrder;
        setLabelTotalDeliveryPrice();
        setLabelTotalItemsCost();
        setLabelTotalOrderPrice();
        addClosedStoreOrderTiles();
    }

    public void addClosedStoreOrderTiles() throws IOException {
        for(ClosedStoreOrder closedStoreOrder : closedCustomerOrder.generateListOfClosedStoreOrders())
        {
            createShowSummeryOfOrderInStoreTile(closedStoreOrder);
        }
    }

    private void createShowSummeryOfOrderInStoreTile(ClosedStoreOrder closedStoreOrder) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL tileFXML = getClass().getResource(SuperDuperMarketConstants.SHOW_SUMMERY_OF_ORDER_IN_STORE);
            loader.setLocation(tileFXML);
            ScrollPane singleStoreTile = loader.load();
            ShowSummeryOfOrderedInStoreController showSummeryOfOrderedInStoreController = loader.getController();
            showSummeryOfOrderedInStoreController.setBusinessLogic(businessLogic);
            showSummeryOfOrderedInStoreController.setStoreOrder(closedStoreOrder);

            System.out.println(closedStoreOrder.getStoreUsed().getName());
            flowPaneStores.getChildren().add(singleStoreTile);
            itemToTileControlleresMap.put(closedStoreOrder, showSummeryOfOrderedInStoreController);
            System.out.println("Adding item");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}












