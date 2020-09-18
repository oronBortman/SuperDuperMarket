package components.makeAnOrderOption.summeryOfOrder;

import commonUI.SuperDuperMarketConstants;
import components.makeAnOrderOption.showSummeryOfOrderInStore.ShowSummeryOfOrderedInStoreController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import logic.BusinessLogic;
import logic.order.CustomerOrder.OpenedCustomerOrder;
import logic.order.StoreOrder.OpenedStoreOrder;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class SummeryOfOrderController {

    @FXML private FlowPane flowPaneStores;
    @FXML private Label LabelTotalItemsCost;
    @FXML private Label LabelTotalDeliveryPrice;
    @FXML private Label LabelTotalOrderPrice;
    @FXML private Button buttonApproveOrder;
    @FXML private Label LabelDateValue;

    BusinessLogic businessLogic;
    OpenedCustomerOrder openedCustomerOrder;
    Consumer<Boolean> isYesClickedConsumer;
    Consumer<Boolean> isNoClickedConsumer;


    private Map<OpenedStoreOrder, ShowSummeryOfOrderedInStoreController> itemToTileControlleresMap;
    DecimalFormat decimalFormat = new DecimalFormat("#.00");


    public void setProperties(Consumer<Boolean> isYesClickedConsumer, Consumer<Boolean> isNoClickedConsumer)
    {
        this.isYesClickedConsumer = isYesClickedConsumer;
        this.isNoClickedConsumer = isNoClickedConsumer;
    }

    public void setLabelTotalItemsCost()
    {
        LabelTotalItemsCost.setText(decimalFormat.format(openedCustomerOrder.calcTotalItemsCost()));
    }

    public void setLabelTotalDeliveryPrice()
    {
        LabelTotalDeliveryPrice.setText(decimalFormat.format(openedCustomerOrder.calcTotalDeliveryPrice()));
    }

    public void setLabelTotalOrderPrice()
    {
        LabelTotalOrderPrice.setText(decimalFormat.format(openedCustomerOrder.calcTotalOrderPrice()));
    }

    public void setLabelOrderDateValue()
    {
        LabelDateValue.setText(openedCustomerOrder.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yy")));
    }


    public void setFlowPaneStores()
    {

    }

    public SummeryOfOrderController()
    {
        itemToTileControlleresMap = new HashMap<OpenedStoreOrder, ShowSummeryOfOrderedInStoreController>();
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void setOpenedCustomerOrder(OpenedCustomerOrder openedCustomerOrder) throws IOException {
        this.openedCustomerOrder = openedCustomerOrder;
        setLabelTotalDeliveryPrice();
        setLabelTotalItemsCost();
        setLabelTotalOrderPrice();
        setLabelOrderDateValue();
        addOpenedStoreOrderTiles();
    }



    public void addOpenedStoreOrderTiles() throws IOException {
        for(OpenedStoreOrder openedStoreOrder : openedCustomerOrder.getListOfOpenedStoreOrder())
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
            showSummeryOfOrderedInStoreController.setStoreOrder(openedStoreOrder);

            flowPaneStores.getChildren().add(singleStoreTile);
            itemToTileControlleresMap.put(openedStoreOrder, showSummeryOfOrderedInStoreController);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clickedOnApproveButton(ActionEvent event)
    {

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Click Yes to approve the order or Click No to cancel order",
                yes,
                no);

        Boolean clear = false;
        alert.setTitle("Order confirmation");
        alert.setHeaderText("Are you sure you want to approve the order?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == yes)
        {
            isYesClickedConsumer.accept(true);
        }
        else if(result.isPresent() && result.get() == no)
        {
            isNoClickedConsumer.accept(true);
        }
    }

}