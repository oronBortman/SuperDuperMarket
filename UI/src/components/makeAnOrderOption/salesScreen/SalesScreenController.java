package components.makeAnOrderOption.salesScreen;

import exceptions.DuplicateSerialIDException;
import exceptions.SerialIDNotExistException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import logic.BusinessLogic;
import logic.Customer;
import logic.common.SuperDuperMarketConstants;
import logic.discount.Discount;
import logic.discount.IfYouBuySDM;
import logic.discount.Offer;
import logic.discount.ThenYouGetSDM;
import logic.order.CustomerOrder.OpenedCustomerOrder;
import logic.order.StoreOrder.OpenedStoreOrder;

import javax.xml.bind.JAXBException;
import java.util.List;

public class SalesScreenController {

    BusinessLogic businessLogic;
    OpenedCustomerOrder openedCustomerOrder;

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void setOpenedCustomerOrder(OpenedCustomerOrder openedCustomerOrder) {
        this.openedCustomerOrder = openedCustomerOrder;
    }

    public void setDiscounts(List<Discount> discountList)
    {
        for(Discount discount : discountList)
        {
            listViewSales.getItems().add(discount);
        }
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
                    System.out.println("Operator:" + operator);
                    setText(message(discount.getName(), quantity, itemID, offerList, operator));
                }
            }
        });
    }

    public void initialize() {

    }

    private String message(String nameOfSale, Double quantity, Integer itemID, List<Offer> offerList, String operator)
    {
        String message = "Name of sale: " + nameOfSale + "\n" +
                "Buy: " + quantity + " of " + businessLogic.getItemBySerialID(itemID).getName() + "\n" +
                "Get: ";
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
    private ListView<Discount> listViewSales;

    @FXML
    private ComboBox<?> comboBoxChooseSale;

    @FXML
    private Button buttonAdd;

    @FXML
    private ComboBox<?> comboBoxChooseItem;

    @FXML
    private TableColumn<?, ?> salesTableNameCol;

    @FXML
    private TableColumn<?, ?> salesTableSerialIDCol;

    @FXML
    private TableColumn<?, ?> salesTableAmountCol;

    @FXML
    private TableColumn<?, ?> salesTablePriceCol;

    @FXML
    private TableColumn<?, ?> salesTableTotalPriceCol;

    @FXML
    private TableColumn<?, ?> salesTableSaleNameCol;

    @FXML
    private TableColumn<?, ?> cartTableNameCol;

    @FXML
    private TableColumn<?, ?> cartTableSerialIDCol;

    @FXML
    private TableColumn<?, ?> cartTableAmountCol;

    @FXML
    private TableColumn<?, ?> cartTableTotalPriceCol;

    @FXML
    void addAction(ActionEvent event) {

    }

    @FXML
    void chooseItem(ActionEvent event) {

    }

    @FXML
    void chooseSale(ActionEvent event) {

    }

}
