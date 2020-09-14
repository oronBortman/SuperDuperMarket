package components.mainScreen;

import components.LoadingXMLFileScreen.LoadingXMLFileController;
import components.makeAnOrderOption.MakeAnOrder.MakeAnOrderController;
import components.makeAnOrderOption.salesScreen.SalesScreenController;
import components.updateItemInStoreOption.addItemToStoreScreen.AddItemToStoreContoller;
import components.makeAnOrderOption.chooseAnItemForOrder.ChooseItemsForOrderController;
import components.updateItemInStoreOption.removeItemFromStoreScreen.RemoveItemFromStoreContoller;
import components.makeAnOrderOption.showStoreStatusInDynamicOrder.ShowStoresStatusInDynamicOrderController;
import components.showOption.showStoresScreen.*;
import components.showOption.showItemsScreen.*;
import commonUI.*;
import components.showOption.showUsersScreen.ShowUsersController;
import components.updateItemInStoreOption.updatePriceOfItemInStoreScreen.UpdateItemInStoreController;
import exceptions.DuplicateSerialIDException;
import exceptions.SerialIDNotExistException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logic.*;
import logic.order.CustomerOrder.OpenedCustomerOrder;
import logic.order.StoreOrder.OpenedStoreOrder;
import logic.order.itemInOrder.OrderedItemFromStore;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class mainScreenController {

    //private BusinessLogic businessLogic;
    private Stage primaryStage;
    private BusinessLogic businessLogic;
    @FXML BorderPane mainBorderPane;



    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public void setBusinessLogic(BusinessLogic businessLogic) throws SerialIDNotExistException, JAXBException, DuplicateSerialIDException {
        this.businessLogic = businessLogic;
    }

    @FXML
    void loadFromXml(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL LoadXMLFileFXML = getClass().getResource(SuperDuperMarketConstants.LOAD_XML_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(LoadXMLFileFXML);
        ScrollPane borderPane = loader.load();
        LoadingXMLFileController loadingXMLFileController = loader.getController();
        loadingXMLFileController.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(borderPane);
    }

    @FXML
    void makeAnOrder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL makeAnOrderFXML = getClass().getResource(SuperDuperMarketConstants.MAKE_AN_ORDER_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(makeAnOrderFXML);
        ScrollPane pane = loader.load();
        MakeAnOrderController makeAnOrderController = loader.getController();
        makeAnOrderController.setBusinessLogic(businessLogic);

        Consumer<Boolean> chooseNext = new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if(aBoolean == true)
                {
                    try {
                        if(makeAnOrderController == null)
                        {
                            System.out.println("NULL!!!!");
                        }
                        else
                        {
                            Customer customer = makeAnOrderController.getCustomer();
                            boolean isOrderStatic = makeAnOrderController.getStaticBoolean();
                            List<Item> itemsList;
                            if(isOrderStatic)
                            {
                                Store store = makeAnOrderController.getStore();
                                itemsList = store.getItemsList();
                                chooseItemsForStaticOrder(customer, store, isOrderStatic, itemsList);
                            }
                            else
                            {
                                itemsList = businessLogic.getItemsList();
                                chooseItemsForDynamicOrder(customer, isOrderStatic, itemsList);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        makeAnOrderController.setProperties(chooseNext);
        mainBorderPane.setCenter(pane);

    }

    public void chooseItemsForStaticOrder(Customer customer, Store store, Boolean isOrderStatic, List<Item> itemsList) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        URL loaderFXML = getClass().getResource(SuperDuperMarketConstants.CHOOSE_ITEM_FOR_ORDER_RESOURCE_IDENTIFEIR);
        loader.setLocation(loaderFXML);
        ScrollPane pane = loader.load();
        ChooseItemsForOrderController chooseItemsForOrderController = loader.getController();
        chooseItemsForOrderController.setOrderStatic(isOrderStatic);
        chooseItemsForOrderController.setItemsList(itemsList);
        chooseItemsForOrderController.setBusinessLogic(businessLogic);
        Date date = new Date();

        Consumer<Boolean> chooseNext = new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if(aBoolean == true)
                {
                    if(chooseItemsForOrderController != null)
                    {
                        Map<Integer, Double> orderedItemsListByItemSerialIDAndWeight = chooseItemsForOrderController.getOrderedItemsListByItemSerialIDAndWeight();
                        Map<Integer, Integer> orderedItemsListByItemSerialIDAndQuantity = chooseItemsForOrderController.getOrderedItemsListByItemSerialIDAndQuantity();
                        OpenedCustomerOrder openedCustomerOrder = businessLogic.updateItemsWithAmountAndCreateOpenedStaticCustomerOrder(customer, date, store, orderedItemsListByItemSerialIDAndWeight, orderedItemsListByItemSerialIDAndQuantity);
                        for( OpenedStoreOrder openedStoreOrder : openedCustomerOrder.getListOfOpenedStoreOrder())
                        {
                            for(Map.Entry<Integer, OrderedItemFromStore> entry : openedStoreOrder.getOrderedItemsNotFromSale().entrySet())
                            {
                                System.out.println(entry.getValue().getName() + " " + entry.getValue().getPricePerUnit() + " " +  entry.getValue().getTotalAmountOfItemOrderedByTypeOfMeasure().toString());
                            }
                        }
                    }

                }
            }
        };
        chooseItemsForOrderController.setProperties(chooseNext);
        System.out.println("Clicked on next");

        mainBorderPane.setCenter(pane);

    }

    public void chooseItemsForDynamicOrder(Customer customer, Boolean isOrderStatic, List<Item> itemsList) throws IOException {
        //ChooseItemsForOrderController chooseItemsForOrderController = getChooseItemsForOrderController(customer,itemsList);

        FXMLLoader loader = new FXMLLoader();
        URL loaderFXML = getClass().getResource(SuperDuperMarketConstants.CHOOSE_ITEM_FOR_ORDER_RESOURCE_IDENTIFEIR);
        loader.setLocation(loaderFXML);
        ScrollPane pane = loader.load();
        ChooseItemsForOrderController chooseItemsForOrderController = loader.getController();
        chooseItemsForOrderController.setItemsList(itemsList);
        chooseItemsForOrderController.setBusinessLogic(businessLogic);


        Date date = new Date();
        chooseItemsForOrderController.setOrderStatic(isOrderStatic);
        Consumer<Boolean> chooseNext = new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if(aBoolean == true)
                {
                    if(chooseItemsForOrderController != null)
                    {
                        Map<Integer, Double> orderedItemsListByItemSerialIDAndWeight = chooseItemsForOrderController.getOrderedItemsListByItemSerialIDAndWeight();
                        Map<Integer, Integer> orderedItemsListByItemSerialIDAndQuantity = chooseItemsForOrderController.getOrderedItemsListByItemSerialIDAndQuantity();

                        OpenedCustomerOrder openedCustomerOrder = businessLogic.updateItemsWithAmountAndCreateOpenedDynamicCustomerOrder(customer, date, orderedItemsListByItemSerialIDAndWeight, orderedItemsListByItemSerialIDAndQuantity);
                        if(openedCustomerOrder != null)
                        {
                            try {
                                showStoresStatusInDynamicOrder(openedCustomerOrder);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        chooseItemsForOrderController.setProperties(chooseNext);
        System.out.println("Clicked on next");

        mainBorderPane.setCenter(pane);
    }

    @FXML
    void showStoresStatusInDynamicOrder(OpenedCustomerOrder openedCustomerOrder) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL showStoresStatusInDynamicOrderFXML = getClass().getResource(SuperDuperMarketConstants.SHOW_STORES_STATUS_IN_DYNAMIC_ORDER);
        loader.setLocation(showStoresStatusInDynamicOrderFXML);
        ScrollPane gridPane = loader.load();
        ShowStoresStatusInDynamicOrderController showStoresStatusInDynamicOrderController = loader.getController();
        showStoresStatusInDynamicOrderController.setOpenedCustomerOrder(openedCustomerOrder);
        showStoresStatusInDynamicOrderController.setBusinessLogic(businessLogic);

        Consumer<Boolean> chooseNext = new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if(aBoolean == true)
                {
                    try {
                        salesScreen(openedCustomerOrder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        showStoresStatusInDynamicOrderController.setProperties(chooseNext);
        System.out.println("Clicked on next");


        mainBorderPane.setCenter(gridPane);
    }

    @FXML
    void salesScreen(OpenedCustomerOrder openedCustomerOrder) throws IOException {
        System.out.println("A");
        FXMLLoader loader = new FXMLLoader();
        System.out.println("B");

        URL salesScreenFXML = getClass().getResource(SuperDuperMarketConstants.SALES_SCREEN);
        System.out.println("C");

        loader.setLocation(salesScreenFXML);
        System.out.println("D");

        ScrollPane gridPane = loader.load();
        System.out.println("E");

        SalesScreenController salesScreenController = loader.getController();
        System.out.println("F");

        salesScreenController.setOpenedCustomerOrder(openedCustomerOrder);
        System.out.println("G");

        salesScreenController.setBusinessLogic(businessLogic);
        System.out.println("H");

        mainBorderPane.setCenter(gridPane);
    }

    @FXML
    void showItems(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL showItemsFXML = getClass().getResource(SuperDuperMarketConstants.SHOW_ITEMS_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(showItemsFXML);
        ScrollPane gridPane = loader.load();
        ShowItemsController showItemsController = loader.getController();
        showItemsController.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(gridPane);
    }

    @FXML
    void showMapOfStoresAndOrders(ActionEvent event) {

    }

    @FXML
    void showOrders(ActionEvent event) {

    }

    void initialize(){

    }

    @FXML
    void showStores(ActionEvent event) throws IOException, SerialIDNotExistException, JAXBException, DuplicateSerialIDException {

        FXMLLoader loader = new FXMLLoader();
        URL showStoresFXML = getClass().getResource(SuperDuperMarketConstants.SHOW_STORES_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(showStoresFXML);
        ScrollPane gridPane = loader.load();
        ShowStoresController showStoresController = loader.getController();
        showStoresController.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(gridPane);
    }

    @FXML
    void showUsers(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL showUsersFXML = getClass().getResource(SuperDuperMarketConstants.SHOW_USERS_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(showUsersFXML);
        ScrollPane gridPane = loader.load();
        ShowUsersController showUsersController = loader.getController();
        showUsersController.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(gridPane);
    }
    @FXML
    void addItemToStore(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL removeItemsFXML = getClass().getResource(SuperDuperMarketConstants.ADD_ITEM_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(removeItemsFXML);
        ScrollPane gridPane = loader.load();
        AddItemToStoreContoller addItemToStoreContoller = loader.getController();
        addItemToStoreContoller.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(gridPane);

    }

    @FXML
    void removeItemFromStore(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL removeItemsFXML = getClass().getResource(SuperDuperMarketConstants.REMOVE_ITEM_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(removeItemsFXML);
        ScrollPane gridPane = loader.load();
        RemoveItemFromStoreContoller removeItemFromStoreContoller = loader.getController();
        removeItemFromStoreContoller.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(gridPane);

    }

    @FXML
    void updatePriceOfItemInStore(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL removeItemsFXML = getClass().getResource(SuperDuperMarketConstants.UPDATE_ITEM_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(removeItemsFXML);
        ScrollPane gridPane = loader.load();
        UpdateItemInStoreController updateItemFromStoreContoller = loader.getController();
        updateItemFromStoreContoller.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(gridPane);

    }



}
