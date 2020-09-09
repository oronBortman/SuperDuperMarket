package components.chooseAnItemForDynamicOrder;


import commonUI.SuperDuperMarketConstants;
import components.QuantityItem.QuantityItemController;
import components.showItemsScreen.ShowItemsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import logic.BusinessLogic;
import logic.Customer;
import logic.Item;
import logic.Store;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ChooseItemsForDynamicOrderController {

    @FXML Button nextButton;
    @FXML BorderPane borderPane;
    @FXML FlowPane flowPaneItems;
    BusinessLogic businessLogic;
    private Map<Integer, QuantityItemController> itemToTileController;
    private Consumer<Boolean> clickedNext;
    ObservableList<Item> items;



    //TODO


    private void createQuantityTile(Integer itemID) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL quantityItemFXML = getClass().getResource(SuperDuperMarketConstants.QUANTITY_ITEM_RESOURCE_IDENTIFEIR);
            loader.setLocation(quantityItemFXML);
            Pane singleWordTile = loader.load();
            QuantityItemController quantityItemController = loader.getController();
            if(quantityItemController != null)
            {
                System.out.println("A");
                quantityItemController.setBusinessLogic(businessLogic);
                System.out.println("B");

                flowPaneItems.getChildren().add(singleWordTile);
                System.out.println("C");

                itemToTileController.put(itemID, quantityItemController);
                System.out.println("Adding item");
            }
            else
            {
                System.out.println("Item controller is null");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        items = FXCollections.observableList(businessLogic.getItemsList());
        System.out.println("In buisnessLogic!!!!");
        for(Item item : items)
        {
            System.out.println(item.getName());
            createQuantityTile(item.getSerialNumber());
        }
        //TODO
        //Change xml loading
    }

    public ChooseItemsForDynamicOrderController()
    {
         itemToTileController = new HashMap<Integer, QuantityItemController>();
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
