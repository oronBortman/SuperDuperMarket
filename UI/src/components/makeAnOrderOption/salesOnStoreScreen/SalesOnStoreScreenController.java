package components.makeAnOrderOption.salesOnStoreScreen;

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

public class SalesOnStoreScreenController {

    @FXML private ListView<Discount> listViewSales;
    BusinessLogic businessLogic;

    @FXML
    public void initialize()
    {
        initializeListViewSales();
    }

    public SalesOnStoreScreenController()
    {

    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void setDiscounts(List<Discount> discountList)
    {
        listViewSales.getItems().clear();
        for(Discount discount : discountList)
        {
            listViewSales.getItems().add(discount);
        }
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
}
