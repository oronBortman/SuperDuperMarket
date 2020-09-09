package components.chooseAnItemForDynamicOrder;


import commonUI.SuperDuperMarketConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import logic.BusinessLogic;
import logic.Customer;
import logic.Store;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class ChooseItemsForDynamicOrderController {

    @FXML Button nextButton;
    @FXML BorderPane borderPane;
    BusinessLogic businessLogic;
    private Consumer<Boolean> clickedNext;



    //TODO


    @FXML
    public void setComboBoxCustomer(Consumer<Boolean> clickedNext)
    {
        this.clickedNext = clickedNext;

    }


    @FXML
    private void initialize() {
       // setComboBoxCustomer();
      /* comboBoxStores.disableProperty().bind(
                Bindings.or(
                        isOrderTypeSelected.not(),
                        comboBoxOrderType.valueProperty().isEqualTo(DYNAMIC_ORDER)));*/
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        final ObservableList<Store> stores = FXCollections.observableList(businessLogic.getStoresList());
        final ObservableList<Customer> customer = FXCollections.observableList(businessLogic.getUsersList());
        //TODO
        //Change xml loading
    }

    public ChooseItemsForDynamicOrderController()
    {

    }


    public void clickOnNextButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL makeAnOrderFXML = getClass().getResource(SuperDuperMarketConstants.MAKE_AN_ORDER_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(makeAnOrderFXML);
        BorderPane pane = loader.load();
        ChooseItemsForDynamicOrderController makeAnOrderController = loader.getController();
        makeAnOrderController.setBusinessLogic(businessLogic);
        borderPane.setCenter(pane);
    }
}
