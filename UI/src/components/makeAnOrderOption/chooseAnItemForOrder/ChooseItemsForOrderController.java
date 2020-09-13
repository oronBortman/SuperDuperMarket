package components.makeAnOrderOption.chooseAnItemForOrder;


import commonUI.SuperDuperMarketConstants;
import components.makeAnOrderOption.Item.ItemTileController;
import components.makeAnOrderOption.Item.QuantityItem.QuantityItemController;
import components.makeAnOrderOption.Item.WeightItem.WeightItemController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import logic.AvailableItemInStore;
import logic.BusinessLogic;
import logic.Item;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ChooseItemsForOrderController {

    @FXML Button nextButton;
    @FXML BorderPane borderPane;
    @FXML FlowPane flowPaneItems;
    BusinessLogic businessLogic;
    private Map<Item, ItemTileController> itemToTileControlleresMap;
    private List<Item> itemsList;
    private boolean isOrderStatic;
    ObservableList<Item> items;
    Consumer<Boolean> isNextClickedConsumer;

    SimpleBooleanProperty isNextClicked;


    //TODO


    public void chooseItems()
    {
        System.out.println("Inside chooseItems");
        if(itemsList == null)
        {
            System.out.println("Setting items List Nul!!!!!!");

        }
        else
        {
            System.out.println("Setting items List Not Null");
            for(Item item : itemsList)
            {
                System.out.println(item.getName());
            }

        }
        for(Item item : itemsList)
        {
            System.out.println(item.getName() + "!!!!!!");
//item.getClass() == OrderedItemByQuantity.class
            if(item.getTypeOfMeasure() == Item.TypeOfMeasure.Quantity)
            {
                System.out.println("Adding quantity item");

                createItemTile(item, SuperDuperMarketConstants.QUANTITY_ITEM_RESOURCE_IDENTIFEIR);
            }
            else if(item.getTypeOfMeasure() == Item.TypeOfMeasure.Weight)
            {
                System.out.println("Adding Weight item");

                createItemTile(item, SuperDuperMarketConstants.WEIGHT_ITEM_RESOURCE_IDENTIFEIR);
            }
        }
    }

    public void setOrderStatic(boolean orderStatic) {
        isOrderStatic = orderStatic;
    }

    public void setProperties(Consumer<Boolean> isNextClicked)
    {
        this.isNextClickedConsumer = isNextClicked;
    }


    private void createItemTile(Item item, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL itemFXML = getClass().getResource(fxmlPath);
            loader.setLocation(itemFXML);
            Pane singleItemTile = loader.load();
            ItemTileController itemController = loader.getController();

            int itemSerialID = item.getSerialNumber();
            String itemName = item.getName();

            System.out.println("Inside createItemTile");
            if(itemController != null)
            {
                itemController.setItemNameLabel(itemName);
                System.out.println(itemName);
                if(isOrderStatic == true)
                {
                    System.out.println("Order is static");
                    if(item.getClass() == AvailableItemInStore.class)
                    {
                        AvailableItemInStore availableItemInStore = (AvailableItemInStore)item;
                        itemController.setItemPriceLabel(availableItemInStore.getPricePerUnit());
                    }
                }
                else
                {
                    System.out.println("Order is dynamic");

                    itemController.setPriceLabelsToUnvisible();
                }
                itemController.setBusinessLogic(businessLogic);
                flowPaneItems.getChildren().add(singleItemTile);
                itemToTileControlleresMap.put(item, itemController);
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
        chooseItems();
        //TODO
        //Change xml loading
    }

    public ChooseItemsForOrderController()
    {
         itemToTileControlleresMap = new HashMap<Item, ItemTileController>();
         itemsList = new ArrayList<Item>();
         isNextClicked = new SimpleBooleanProperty(false);
    }


    public void clickOnNextButton(ActionEvent actionEvent) throws IOException {

        isNextClicked.set(true);
        isNextClickedConsumer.accept(true);

    }

    public void setItemsList(List<Item> itemsList) {
        if(itemsList == null)
        {
            System.out.println("Setting items List Nul!!!!!!");

        }
        else
        {
            System.out.println("Setting items List Not Null");
            for(Item item : itemsList)
            {
                System.out.println(item.getName());
            }

        }
        this.itemsList = itemsList;
    }

    public Map<Integer, Integer> getOrderedItemsListByItemSerialIDAndQuantity()
    {
        Map<Integer, Integer> orderedItemFromStoreMap = new HashMap<Integer, Integer>();
        for(Map.Entry<Item, ItemTileController> entry : itemToTileControlleresMap.entrySet())
        {
            int itemSerialID = entry.getKey().getSerialNumber();
            ItemTileController itemController = entry.getValue();
            if(itemController != null)
            {
                if(itemController.getClass() == QuantityItemController.class)
                {
                    int amount = ((QuantityItemController) itemController).getAmount();
                    if(amount > 0)
                    {
                        orderedItemFromStoreMap.put(itemSerialID, amount);
                    }

                }
            }
        }
        return orderedItemFromStoreMap;
    }

    public Map<Integer, Double> getOrderedItemsListByItemSerialIDAndWeight()
    {
        Map<Integer, Double> orderedItemFromStoreMap = new HashMap<Integer, Double>();
        for(Map.Entry<Item, ItemTileController> entry : itemToTileControlleresMap.entrySet())
        {
            int itemSerialID = entry.getKey().getSerialNumber();
            ItemTileController itemController = entry.getValue();
            if(itemController != null) {
                if (itemController.getClass() == WeightItemController.class) {
                    Double amount = ((WeightItemController) itemController).getAmount();
                    if(amount > 0)
                    {
                        orderedItemFromStoreMap.put(itemSerialID, amount);
                    }
                }
            }
        }
        return orderedItemFromStoreMap;
    }
}
