package components.showOption.showOrderesHistory;

import commonUI.SuperDuperMarketConstants;
import components.makeAnOrderOption.ShowSummeryOfOrderInStore.ShowSummeryOfOrderedInStoreController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import logic.BusinessLogic;
import logic.order.CustomerOrder.ClosedCustomerOrder;
import logic.order.CustomerOrder.OpenedCustomerOrder;
import logic.order.StoreOrder.ClosedStoreOrder;
import logic.order.StoreOrder.OpenedStoreOrder;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;


package components.makeAnOrderOption.SummeryOfOrder;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class showOrdersHistoryController {
    @FXML private FlowPane flowPaneStores;
    @FXML private ComboBox<?> comboBoxChooseOrder;
    @FXML private Label LabelTotalItemsCost;
    @FXML private Label LabelTotalDeliveryPrice;
    @FXML private Label LabelTotalOrderPrice;
    @FXML private Button buttonApproveOrder;

    private Map<ClosedStoreOrder, ShowSummeryOfOrderedInStoreController> itemToTileControlleresMap;
    private BusinessLogic businessLogic;
    private ClosedCustomerOrder closedCustomerOrder;

    @FXML
    void chooseOrder(ActionEvent event) {

    }

    public void setLabelTotalItemsCost()
    {
        LabelTotalItemsCost.setText(closedCustomerOrder.getTotalItemCostInOrder().toString());
    }

    public void setLabelTotalDeliveryPrice()
    {
        LabelTotalDeliveryPrice.setText(closedCustomerOrder.getTotalDeliveryPriceInOrder().toString());
    }

    public void setLabelTotalOrderPrice()
    {
        LabelTotalOrderPrice.setText(closedCustomerOrder.getTotalOrderPrice().toString());
    }

    public void setFlowPaneStores()
    {

    }

    public showOrdersHistoryController()
    {
        itemToTileControlleresMap = new HashMap<OpenedStoreOrder, ShowSummeryOfOrderedInStoreController>();
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void setClosedCustomerOrder(ClosedCustomerOrder closedCustomerOrder) throws IOException {
        this.closedCustomerOrder = closedCustomerOrder;
        setLabelTotalDeliveryPrice();
        setLabelTotalItemsCost();
        setLabelTotalOrderPrice();
        addOpenedStoreOrderTiles();
    }

    public void addOpenedStoreOrderTiles() throws IOException {
        for(OpenedStoreOrder openedStoreOrder : closedCustomerOrder.getListOfOpenedStoreOrder())
        {
            createShowSummeryOfOrderInStoreTile(openedStoreOrder);
        }
    }

    private void createShowSummeryOfOrderInStoreTile(OpenedStoreOrder openedStoreOrder) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL tileFXML = getClass().getResource(SuperDuperMarketConstants.SHOW_SUMMERY_OF_ORDER_IN_STORE);
            loader.setLocation(tileFXML);
            ScrollPane singleStoreTile = loader.load();
            ShowSummeryOfOrderedInStoreController showSummeryOfOrderedInStoreController = loader.getController();
            showSummeryOfOrderedInStoreController.setBusinessLogic(businessLogic);
            showSummeryOfOrderedInStoreController.setOpenedStoreOrder(openedStoreOrder);

            System.out.println(openedStoreOrder.getStoreUsed().getName());
            flowPaneStores.getChildren().add(singleStoreTile);
            itemToTileControlleresMap.put(openedStoreOrder, showSummeryOfOrderedInStoreController);
            System.out.println("Adding item");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}












