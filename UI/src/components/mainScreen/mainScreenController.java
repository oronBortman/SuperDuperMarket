package components.mainScreen;

import components.LoadingXMLFileScreen.LoadingXMLFileController;
import components.makeAnOrderOption.MakeAnOrder.MakeAnOrderController;
import components.makeAnOrderOption.ShowSummeryOfOrderInStore.ShowSummeryOfOrderedInStoreController;
import components.makeAnOrderOption.SummeryOfOrder.SummeryOfOrderController;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import logic.*;
import logic.order.CustomerOrder.OpenedCustomerOrder;
import logic.order.StoreOrder.OpenedStoreOrder;
import logic.order.itemInOrder.OrderedItemFromStore;
import javafx.application.Application;
import javafx.scene.Scene;
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
        FXMLLoader loader = new FXMLLoader();
        URL salesScreenFXML = getClass().getResource(SuperDuperMarketConstants.SALES_SCREEN);
        loader.setLocation(salesScreenFXML);
        ScrollPane gridPane = loader.load();
        SalesScreenController salesScreenController = loader.getController();
        salesScreenController.setOpenedCustomerOrder(openedCustomerOrder);
        salesScreenController.setBusinessLogic(businessLogic);

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
        summeryOfOrderController.setOpenedCustomerOrder(openedCustomerOrder);
        summeryOfOrderController.setBusinessLogic(businessLogic);

        Consumer<Boolean> isYesClickedConsumer = new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if(aBoolean == true)
                {
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
        BusinessLogic businessLogic = new BusinessLogic();
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

        // set stage
        primaryStage.setTitle("Super Duper Market");
        Scene scene = new Scene(scrollPane, 1050, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
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
    @FXML
    void showMapOfStoresAndOrders(ActionEvent event) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        int maxCoordinateX = businessLogic.getMaxCoordinateXOfLocationOfUsersAndStores();
        System.out.println("Max x:" + maxCoordinateX);
        int maxCoordinateY = businessLogic.getMaxCoordinateYOfLocationOfUsersAndStores();
        System.out.println("Max y:" + maxCoordinateY);
        GridPane gridPane = gridPaneForceColsAndRows(maxCoordinateX,maxCoordinateY);
        System.out.println("A");

        for(Integer i=1;i<=maxCoordinateX;i++)
        {
            gridPane.add(new Label(i.toString()),i,0);
        }

        for(Integer i=1;i<=maxCoordinateY;i++)
        {
            gridPane.add(new Label(i.toString()),0,i);
        }

        for(Customer customer : businessLogic.getUsersList())
        {
            final Tooltip tooltip = new Tooltip();
            tooltip.setText("Name: " + customer.getName() +
                    "\nLocation:" + "(" + customer.getLocation().getX() + "," +
                    customer.getLocation().getY() + ")");

            int coordinateX = customer.getLocation().getX();
            int coordinateY = customer.getLocation().getY();
            System.out.println("Corrdinate x:"+coordinateX);
            System.out.println("Corrdinate y:"+coordinateY);
            ImageView imageView = generateUserImage();
            //Tooltip.install(imageView, new Tooltip("AAA"));
            HBox hbox = new HBox(imageView);
            hbox.setMinHeight(40);
            hbox.setPrefHeight(40);
            hbox.setMinWidth(40);
            hbox.setPrefHeight(40);
            hbox.setFillHeight(true);
            HBox.setHgrow(imageView,Priority.ALWAYS);

            imageView.fitWidthProperty().bind(hbox.widthProperty());
            imageView.fitHeightProperty().bind(hbox.heightProperty());

            imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    Node  node =(Node)t.getSource();
                    tooltip.show(node, primaryStage.getX()+t.getSceneX(), primaryStage.getY()+t.getSceneY());
                }
            });

            imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    tooltip.hide();
                }
            });

            gridPane.add(hbox,coordinateX,coordinateY);
        }

        System.out.println("Finished with users");

        for(Store store : businessLogic.getStoresList())
        {
            final Tooltip tooltip = new Tooltip();
            tooltip.setText("Serial ID: " + store.getSerialNumber() + "\nName: " + store.getName() +
                    "\nLocation:" + "(" + store.getLocationOfShop().getX() + "," +
                    store.getLocationOfShop().getY() + ")");

            int coordinateX = store.getLocationOfShop().getX();
            int coordinateY = store.getLocationOfShop().getY();
            System.out.println("Corrdinate x:"+coordinateX);
            System.out.println("Corrdinate y:"+coordinateY);

            ImageView imageView = generateStoreImage();
            imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    Node  node =(Node)t.getSource();
                    tooltip.show(node, primaryStage.getX()+t.getSceneX(), primaryStage.getY()+t.getSceneY());
                }
            });

            imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    tooltip.hide();
                }
            });

            HBox hbox = new HBox(imageView);
            hbox.setMinHeight(40);
            hbox.setPrefHeight(40);
            hbox.setMinWidth(40);
            hbox.setPrefHeight(40);
            hbox.setFillHeight(true);
            HBox.setHgrow(imageView,Priority.ALWAYS);

            imageView.fitWidthProperty().bind(hbox.widthProperty());
            imageView.fitHeightProperty().bind(hbox.heightProperty());

            gridPane.add(hbox,coordinateX,coordinateY);
        }

        scrollPane.setContent(gridPane);

        mainBorderPane.setCenter(scrollPane);


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
