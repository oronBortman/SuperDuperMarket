package components.mainScreen;

import components.LoadingXMLFileScreen.LoadingXMLFileController;
import components.makeAnOrderOption.MakeAnOrder.MakeAnOrderController;
import components.makeAnOrderOption.ShowSummeryOfOrderInStore.ShowSummeryOfOrderedInStoreController;
import components.makeAnOrderOption.SummeryOfOrder.SummeryOfOrderController;
import components.makeAnOrderOption.salesScreen.SalesScreenController;
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
import exceptions.DuplicateSerialIDException;
import exceptions.SerialIDNotExistException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import logic.*;
import logic.order.CustomerOrder.ClosedCustomerOrder;
import logic.order.CustomerOrder.OpenedCustomerOrder;
import logic.order.StoreOrder.OpenedStoreOrder;
import logic.order.itemInOrder.OrderedItemFromStore;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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


    private SimpleBooleanProperty loadedXMLFileSuccessfully;


    public mainScreenController()
    {
        loadedXMLFileSuccessfully = new SimpleBooleanProperty(false);
        setProperties();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public void setBusinessLogic(BusinessLogic businessLogic) throws SerialIDNotExistException, JAXBException, DuplicateSerialIDException {
        this.businessLogic = businessLogic;
    }


    public void initialize()
    {
        menuOptionMakeAnOrder.disableProperty().bind(loadedXMLFileSuccessfully.not());
        //menuOptionShow.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionShowUsers.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionShowItems.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionShowStores.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionShowOrders.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionShowMap.disableProperty().bind(loadedXMLFileSuccessfully.not());
        //menuOptionUpdateItem.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionDeleteItem.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionAddItem.disableProperty().bind(loadedXMLFileSuccessfully.not());
        menuOptionUpdatePrice.disableProperty().bind(loadedXMLFileSuccessfully.not());
    }
    public void setProperties()
    {

    }

    @FXML
    void loadFromXml(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL LoadXMLFileFXML = getClass().getResource(SuperDuperMarketConstants.LOAD_XML_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(LoadXMLFileFXML);
        ScrollPane borderPane = loader.load();
        LoadingXMLFileController loadingXMLFileController = loader.getController();
        Consumer<Boolean> loadBusinessLogicSuccessfully = new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if (aBoolean == true) {
                    try {
                        setBusinessLogic(loadingXMLFileController.getBusinessLogicFromXML());
                        loadedXMLFileSuccessfully.set(true);
                    } catch (Exception e) {

                    }
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
        FXMLLoader loader = new FXMLLoader();
        URL salesScreenFXML = getClass().getResource(SuperDuperMarketConstants.SALES_SCREEN);
        loader.setLocation(salesScreenFXML);
        ScrollPane gridPane = loader.load();
        SalesScreenController salesScreenController = loader.getController();
        salesScreenController.setBusinessLogic(businessLogic);
        salesScreenController.setOpenedCustomerOrder(openedCustomerOrder);

        Consumer<Boolean> chooseNext = new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if(aBoolean == true)
                {
                    try {
                        showSummeryOfOrder(openedCustomerOrder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

        Consumer<Boolean> isYesClickedConsumer = new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if(aBoolean == true)
                {
                    ClosedCustomerOrder closedCustomerOrder = openedCustomerOrder.closeCustomerOrder();
                    businessLogic.addClosedOrderToHistory(closedCustomerOrder);
                    showMainScreen();
                }
            }
        };

        Consumer<Boolean> isNoClickedConsumer = new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if(aBoolean == true)
                {
                    showMainScreen();
                }
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
        } catch (DuplicateSerialIDException e) {
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

    ImageView generateUserImage()
    {
        ImageView userImage = new ImageView("/components/user.png");
        userImage.setFitHeight(40);
        userImage.setFitWidth(40);
        return userImage;
    }
    ImageView generateStoreImage()
    {
        ImageView storeImage = new ImageView("/components/shop.png");
        storeImage.setFitHeight(40);
        storeImage.setFitWidth(40);
        return storeImage;
    }

    ScrollPane createAndSetScrollPane()
    {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        return scrollPane;
    }

    void settingColumnsScaleMark(GridPane gridPane, int maxCoordinateX)
    {

        for(Integer i=1;i<=maxCoordinateX;i++)
        {
            gridPane.add(new Label(i.toString()),i,0);
        }
    }

    void settingRowsScaleMark(GridPane gridPane, int maxCoordinateY)
    {
        for(Integer i=1;i<=maxCoordinateY;i++)
        {
            gridPane.add(new Label(i.toString()),0,i);
        }

    }

    void settingCustomersOnMap(GridPane gridPane)
    {
        for(Customer customer : businessLogic.getUsersList())
        {
            final Tooltip tooltip = new Tooltip();
            tooltip.setText("Type: customer\n" + "Name: " + customer.getName() +
                    "\nLocation:" + "(" + customer.getLocation().getX() + "," +
                    customer.getLocation().getY() + ")");
            ImageView imageView = generateUserImage();
            createImageAndSetItWithHboxAndToolTip(tooltip, imageView, gridPane, customer.getLocation().getX(), customer.getLocation().getY());
        }
    }

    void settingStoresOnMap(GridPane gridPane)
    {
        for(Store store : businessLogic.getStoresList())
        {
            final Tooltip tooltip = new Tooltip();
            tooltip.setText("Type: store\n" + "Serial ID: " + store.getSerialNumber() + "\nName: " + store.getName() +
                    "\nLocation:" + "(" + store.getLocationOfShop().getX() + "," +
                    store.getLocationOfShop().getY() + ")");
            ImageView imageView = generateStoreImage();
            createImageAndSetItWithHboxAndToolTip(tooltip, imageView, gridPane, store.getLocationOfShop().getX(), store.getLocationOfShop().getY());
        }
    }

    @FXML
    void showMapOfStoresAndOrders(ActionEvent event)
    {
        ScrollPane scrollPane = createAndSetScrollPane();
        int maxCoordinateX = businessLogic.getMaxCoordinateXOfLocationOfUsersAndStores();
        int maxCoordinateY = businessLogic.getMaxCoordinateYOfLocationOfUsersAndStores();
        GridPane gridPane = gridPaneForceColsAndRows(maxCoordinateX,maxCoordinateY);
        settingColumnsScaleMark(gridPane, maxCoordinateX);
        settingRowsScaleMark(gridPane, maxCoordinateY);
        settingCustomersOnMap(gridPane);
        settingStoresOnMap(gridPane);
        scrollPane.setContent(gridPane);
        mainBorderPane.setCenter(scrollPane);
    }

    public void createImageAndSetItWithHboxAndToolTip(Tooltip tooltip, ImageView imageView, GridPane gridPane, int coordinateX, int coordinateY)
    {
        setToolTipOnImage(tooltip, imageView);
        HBox hbox = new HBox(imageView);
        updateHboxWithImage(hbox,imageView);
        gridPane.add(hbox,coordinateX,coordinateY);
    }
    public void setToolTipOnImage(Tooltip toolTipOnImage, ImageView imageView)
    {
        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Node  node =(Node)t.getSource();
                toolTipOnImage.show(node, primaryStage.getX()+t.getSceneX(), primaryStage.getY()+t.getSceneY());
            }
        });

        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                toolTipOnImage.hide();
            }
        });
    }
    public void updateHboxWithImage(HBox hbox, ImageView imageView)
    {
        hbox.setMinHeight(40);
        hbox.setPrefHeight(40);
        hbox.setMinWidth(40);
        hbox.setPrefHeight(40);
        hbox.setFillHeight(true);
        HBox.setHgrow(imageView,Priority.ALWAYS);

        imageView.fitWidthProperty().bind(hbox.widthProperty());
        imageView.fitHeightProperty().bind(hbox.heightProperty());

    }

    public GridPane gridPaneForceColsAndRows(int numCols, int numRows) {
        GridPane gridPane = new GridPane();

        gridPane.setGridLinesVisible(true);
        double widht=100.0;
        double hight=100.0;
            for (int i = 0; i <= numCols+1; i++)
            {

                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(widht / numCols);
                if(i==0 || i==numCols+1) {
                }
                else {  }
                gridPane.getColumnConstraints().add(colConst);
            }
            for (int i = 0; i <= numRows+1; i++) {
                if(i==0 || i==numCols+1) {}
                else {}
                RowConstraints rowConst = new RowConstraints();
                rowConst.setPercentHeight(hight / numRows);
                gridPane.getRowConstraints().add(rowConst);
            }
            return gridPane;
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
