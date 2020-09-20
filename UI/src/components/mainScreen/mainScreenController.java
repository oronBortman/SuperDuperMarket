package components.mainScreen;

import components.loadingXMLFileScreen.LoadingXMLFileController;
import components.addItem.AddItemController;
import components.addItem.chooseStoresForItem.ChooseStoresForItemController;
import components.addStore.AddStoreController;
import components.addingDiscounts.AddingDiscountsController;
import components.makeAnOrderOption.makeAnOrder.MakeAnOrderController;
import components.makeAnOrderOption.summeryOfOrder.SummeryOfOrderController;
import components.makeAnOrderOption.salesScreen.SalesScreenController;
import components.showOption.shopMap.MapGridPane;
import components.showOption.shopMap.ShowMapMainBorderPane;
import components.showOption.shopMap.TopBorderPane;
import components.showOption.showOrderesHistory.showOrdersHistoryController;
import components.updateItemInStoreOption.addItemToStoreScreen.AddItemToStoreContoller;
import components.makeAnOrderOption.chooseAnItemForOrder.ChooseItemsForOrderController;
import components.updateItemInStoreOption.removeItemFromStoreScreen.RemoveItemFromStoreContoller;
import components.makeAnOrderOption.showStoreStatusInDynamicOrder.ShowStoresStatusInDynamicOrderController;
import components.showOption.showStoresScreen.*;
import components.showOption.showItemsScreen.*;
import commonUI.*;
import components.showOption.showUsersScreen.ShowUsersController;
import components.updateItemInStoreOption.updatePriceOfItemInStoreScreen.UpdateItemInStoreController;
import exceptions.duplicateSerialID.DuplicateSerialIDExceptionInSDM;
import exceptions.notExistException.SerialIDNotExistException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import logic.*;
import logic.order.CustomerOrder.ClosedCustomerOrder;
import logic.order.CustomerOrder.OpenedCustomerOrder;
import javafx.scene.layout.GridPane;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class mainScreenController {

    //private BusinessLogic businessLogic;
    private Stage primaryStage;
    private BusinessLogic businessLogic;
    @FXML BorderPane mainBorderPane;
    @FXML MenuItem menuOptionMakeAnOrder;
    @FXML Menu menuOptionShow;
    @FXML MenuItem menuOptionShowUsers;
    @FXML MenuItem menuOptionShowItems;
    @FXML MenuItem menuOptionShowStores;
    @FXML MenuItem menuOptionShowOrders;
    @FXML MenuItem menuOptionShowMap;
    @FXML Menu menuOptionUpdateItem;
    @FXML MenuItem menuOptionDeleteItem;
    @FXML MenuItem menuOptionAddItem;
    @FXML MenuItem menuOptionUpdatePrice;
    @FXML MenuItem menuOptionAddStore;
    @FXML MenuItem menuOptionAddItemToSDM;
    @FXML MenuItem menuOptionLoadFromXML;
    @FXML MenuItem menuOptionAddDiscount;


    private SimpleBooleanProperty loadedXMLFileSuccessfully;


    public mainScreenController()
    {
        loadedXMLFileSuccessfully = new SimpleBooleanProperty(false);
        setProperties();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public void setBusinessLogic(BusinessLogic businessLogic) throws SerialIDNotExistException, JAXBException, DuplicateSerialIDExceptionInSDM {
        this.businessLogic = businessLogic;
    }


    public void initialize()
    {
        bindMenuOptionsToLoadedXMLFile();
    }

    public void unbindMenuOptionFromLoadedXMLFile()
    {
        menuOptionAddDiscount.disableProperty().unbind();
        menuOptionMakeAnOrder.disableProperty().unbind();
        menuOptionShowUsers.disableProperty().unbind();
        menuOptionShowItems.disableProperty().unbind();
        menuOptionShowStores.disableProperty().unbind();
        menuOptionShowOrders.disableProperty().unbind();
        menuOptionShowMap.disableProperty().unbind();
        menuOptionDeleteItem.disableProperty().unbind();
        menuOptionAddItem.disableProperty().unbind();
        menuOptionUpdatePrice.disableProperty().unbind();
        menuOptionAddStore.disableProperty().unbind();
        menuOptionAddItemToSDM.disableProperty().unbind();
    }

    public void bindMenuOptionsToLoadedXMLFile()
    {
        menuOptionAddDiscount.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionMakeAnOrder.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionShowUsers.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionShowItems.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionShowStores.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionShowOrders.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionShowMap.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionDeleteItem.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionAddItem.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionUpdatePrice.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionAddStore.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionAddItemToSDM.disableProperty().bind(loadedXMLFileSuccessfully.not());
    }

    public void disableOrEnableAllMenuOptions(boolean disable)
    {
        menuOptionAddDiscount.setDisable(disable);
        menuOptionAddDiscount.setDisable(disable);
        menuOptionMakeAnOrder.setDisable(disable);
        menuOptionShowUsers.setDisable(disable);
        menuOptionShowItems.setDisable(disable);
        menuOptionShowStores.setDisable(disable);
        menuOptionShowOrders.setDisable(disable);
        menuOptionShowMap.setDisable(disable);
        menuOptionDeleteItem.setDisable(disable);
        menuOptionAddItem.setDisable(disable);
        menuOptionUpdatePrice.setDisable(disable);
        menuOptionAddStore.setDisable(disable);
        menuOptionAddItemToSDM.setDisable(disable);
        menuOptionLoadFromXML.setDisable(disable);
    }

    public void setProperties()
    {

    }

    @FXML
    void clickedOnOptionAdDiscount(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL LoadXMLFileFXML = getClass().getResource(SuperDuperMarketConstants.ADDING_DISCOUNT);
        loader.setLocation(LoadXMLFileFXML);
        ScrollPane scrollPane = loader.load();
        AddingDiscountsController addingDiscountsController = loader.getController();
        addingDiscountsController.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(scrollPane);
    }
    @FXML
    void loadFromXml(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL LoadXMLFileFXML = getClass().getResource(SuperDuperMarketConstants.LOAD_XML_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(LoadXMLFileFXML);
        ScrollPane borderPane = loader.load();
        LoadingXMLFileController loadingXMLFileController = loader.getController();
        Consumer<Boolean> loadBusinessLogicSuccessfully = aBoolean -> {
            if (aBoolean == true) {
                try {
                    setBusinessLogic(loadingXMLFileController.getBusinessLogicFromXML());
                    loadedXMLFileSuccessfully.set(true);
                } catch (Exception e) {

                }
            }
        };
        loadingXMLFileController.setProperties(loadBusinessLogicSuccessfully);
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

        Consumer<Boolean> chooseNext = aBoolean -> {
            if(aBoolean == true)
            {
                try {
                    if(makeAnOrderController != null)
                    {
                        Customer customer = makeAnOrderController.getCustomer();
                        boolean isOrderStatic = makeAnOrderController.getStaticBoolean();
                        List<Item> itemsList;
                        if(isOrderStatic)
                        {
                            Store store = makeAnOrderController.getStore();
                            LocalDate date = makeAnOrderController.getDate();
                            itemsList = store.getItemsList();
                            chooseItemsForStaticOrder(customer, store, date,isOrderStatic, itemsList);
                        }
                        else
                        {
                            itemsList = businessLogic.getItemsList();
                            LocalDate date = makeAnOrderController.getDate();
                            chooseItemsForDynamicOrder(customer, date,isOrderStatic, itemsList);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        makeAnOrderController.setProperties(chooseNext);
        mainBorderPane.setCenter(pane);

    }

    public void ClickedOnOptionAddStore(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL loaderFXML = getClass().getResource(SuperDuperMarketConstants.ADD_STORE);
        loader.setLocation(loaderFXML);
        ScrollPane pane = loader.load();
        AddStoreController addStoreController = loader.getController();
        addStoreController.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(pane);
    }

    public void ClickedOnOptionAddItem(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL loaderFXML = getClass().getResource(SuperDuperMarketConstants.ADD_ITEM);
        loader.setLocation(loaderFXML);
        ScrollPane pane = loader.load();
        AddItemController addItemController = loader.getController();
        addItemController.setBusinessLogic(businessLogic);
        Consumer<Boolean> addedItem = aBoolean -> {
            if(aBoolean == true)
            {
                Item item=addItemController.getItem();
                //unbindMenuOptionFromLoadedXMLFile();
                //disableOrEnableAllMenuOptions(true);
                try {
                    chooseStoresForItem(item);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        addItemController.setProperties(addedItem);
        mainBorderPane.setCenter(pane);
    }

    public void chooseStoresForItem(Item item) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        URL loaderFXML = getClass().getResource(SuperDuperMarketConstants.CHOOSE_STORE_FOR_ITEM);
        loader.setLocation(loaderFXML);
        ScrollPane pane = loader.load();
        ChooseStoresForItemController chooseStoresForItemController = loader.getController();
        chooseStoresForItemController.setBusinessLogic(businessLogic);
        chooseStoresForItemController.setAddedItem(item);

        Consumer<Boolean> addedItemToStoreSuccessfully = aBoolean -> {
            if(aBoolean == true)
            {
//                bindMenuOptionsToLoadedXMLFile();
                showMainScreen();
                try {
                    chooseStoresForItem(item);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        chooseStoresForItemController.setProperties(addedItemToStoreSuccessfully);
        mainBorderPane.setCenter(pane);
    }


    public void chooseItemsForStaticOrder(Customer customer, Store store, LocalDate date, Boolean isOrderStatic, List<Item> itemsList) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        URL loaderFXML = getClass().getResource(SuperDuperMarketConstants.CHOOSE_ITEM_FOR_ORDER_RESOURCE_IDENTIFEIR);
        loader.setLocation(loaderFXML);
        ScrollPane pane = loader.load();
        ChooseItemsForOrderController chooseItemsForOrderController = loader.getController();
        chooseItemsForOrderController.setOrderStatic(isOrderStatic);
        chooseItemsForOrderController.setItemsList(itemsList);
        chooseItemsForOrderController.setBusinessLogic(businessLogic);

        Consumer<Boolean> chooseNext = aBoolean -> {
            if(aBoolean == true)
            {
                if(chooseItemsForOrderController != null)
                {
                    Map<Integer, Double> orderedItemsListByItemSerialIDAndWeight = chooseItemsForOrderController.getOrderedItemsListByItemSerialIDAndWeight();
                    Map<Integer, Integer> orderedItemsListByItemSerialIDAndQuantity = chooseItemsForOrderController.getOrderedItemsListByItemSerialIDAndQuantity();
                    OpenedCustomerOrder openedCustomerOrder = businessLogic.updateItemsWithAmountAndCreateOpenedStaticCustomerOrder(customer, date, store, orderedItemsListByItemSerialIDAndWeight, orderedItemsListByItemSerialIDAndQuantity);
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
        };
        chooseItemsForOrderController.setProperties(chooseNext);

        mainBorderPane.setCenter(pane);

    }

    public void chooseItemsForDynamicOrder(Customer customer, LocalDate date, Boolean isOrderStatic, List<Item> itemsList) throws IOException {
        //ChooseItemsForOrderController chooseItemsForOrderController = getChooseItemsForOrderController(customer,itemsList);

        FXMLLoader loader = new FXMLLoader();
        URL loaderFXML = getClass().getResource(SuperDuperMarketConstants.CHOOSE_ITEM_FOR_ORDER_RESOURCE_IDENTIFEIR);
        loader.setLocation(loaderFXML);
        ScrollPane pane = loader.load();
        ChooseItemsForOrderController chooseItemsForOrderController = loader.getController();
        chooseItemsForOrderController.setItemsList(itemsList);
        chooseItemsForOrderController.setBusinessLogic(businessLogic);

        chooseItemsForOrderController.setOrderStatic(isOrderStatic);
        Consumer<Boolean> chooseNext = aBoolean -> {
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
        };
        chooseItemsForOrderController.setProperties(chooseNext);

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

        Consumer<Boolean> chooseNext = aBoolean -> {
            if(aBoolean == true)
            {
                try {
                    salesScreen(openedCustomerOrder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        showStoresStatusInDynamicOrderController.setProperties(chooseNext);

        mainBorderPane.setCenter(gridPane);
    }

    @FXML
    void salesScreen(OpenedCustomerOrder openedCustomerOrder) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL salesScreenFXML = getClass().getResource(SuperDuperMarketConstants.SALES_SCREEN);
        loader.setLocation(salesScreenFXML);
        ScrollPane gridPane = loader.load();
        SalesScreenController salesScreenController = loader.getController();
        salesScreenController.setBusinessLogic(businessLogic);
        salesScreenController.setOpenedCustomerOrder(openedCustomerOrder);

        Consumer<Boolean> chooseNext = aBoolean -> {
            if(aBoolean == true)
            {
                try {
                    showSummeryOfOrder(openedCustomerOrder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        salesScreenController.setProperties(chooseNext);
        mainBorderPane.setCenter(gridPane);
    }

    void showSummeryOfOrder(OpenedCustomerOrder openedCustomerOrder) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL showSummeryOfOrderInStoreFXML = getClass().getResource(SuperDuperMarketConstants.SUMMERY_OF_ORDER);
        loader.setLocation(showSummeryOfOrderInStoreFXML);
        ScrollPane gridPane = loader.load();
        SummeryOfOrderController summeryOfOrderController = loader.getController();
        summeryOfOrderController.setBusinessLogic(businessLogic);
        summeryOfOrderController.setOpenedCustomerOrder(openedCustomerOrder);

        Consumer<Boolean> isYesClickedConsumer = aBoolean -> {
            if(aBoolean == true)
            {
                ClosedCustomerOrder closedCustomerOrder = openedCustomerOrder.closeCustomerOrder();
                businessLogic.addClosedOrderToHistory(closedCustomerOrder);
                showMainScreen();
            }
        };

        Consumer<Boolean> isNoClickedConsumer = aBoolean -> {
            if(aBoolean == true)
            {
                showMainScreen();
            }
        };

        summeryOfOrderController.setProperties(isYesClickedConsumer, isNoClickedConsumer);
        mainBorderPane.setCenter(gridPane);
    }

    public void showMainScreen()
    {
        FXMLLoader loader = new FXMLLoader();

        // load main fxml
        URL mainFXML = getClass().getResource(SuperDuperMarketConstants.MAIN_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(mainFXML);
        BorderPane scrollPane = null;
        try {
            scrollPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // wire up controller
        components.mainScreen.mainScreenController superDuperMarketController = loader.getController();
        superDuperMarketController.setPrimaryStage(primaryStage);
        try {
            superDuperMarketController.setBusinessLogic(businessLogic);
        } catch (SerialIDNotExistException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (DuplicateSerialIDExceptionInSDM e) {
            e.printStackTrace();
        }
        superDuperMarketController.setLoadedXMLFileSuccessfully(this.loadedXMLFileSuccessfully.get());
        // set stage
        primaryStage.setTitle("Super Duper Market");
        Scene scene = new Scene(scrollPane, 1050, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setLoadedXMLFileSuccessfully(boolean loadedXMLFileSuccessfully) {
        this.loadedXMLFileSuccessfully.set(loadedXMLFileSuccessfully);
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
    void showMapOfStoresAndCustomers(ActionEvent event)
    {
        TopBorderPane topBorderPane = new TopBorderPane();
        ShowMapMainBorderPane showMapMainBorderPane = new ShowMapMainBorderPane();
        MapGridPane mapGridPane = new MapGridPane(businessLogic, topBorderPane.getHboxDetailsOnSDM());
        showMapMainBorderPane.setMainBorderPaneWithComponents(topBorderPane.getTopBorderPane(), mapGridPane.getGridPane());
        mainBorderPane.setCenter(showMapMainBorderPane.getScrollPane());
    }


    @FXML
    void showOrders(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        URL showOrdersFXML = getClass().getResource(SuperDuperMarketConstants.SHOW_ORDERS_HISTORY_CONTROLLER);
        loader.setLocation(showOrdersFXML);
        ScrollPane gridPane = null;
        try {
            gridPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showOrdersHistoryController showOrdersHistoryController = loader.getController();
        showOrdersHistoryController.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(gridPane);
    }


    @FXML
    void showStores(ActionEvent event) throws IOException, SerialIDNotExistException, JAXBException, DuplicateSerialIDExceptionInSDM {

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
