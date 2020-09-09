package components.mainScreen;

import components.LoadingXMLFileScreen.LoadingXMLFileController;
import components.MakeAnOrder.MakeAnOrderController;
import components.addItemToStoreScreen.AddItemToStoreContoller;
import components.chooseAnItemForDynamicOrder.ChooseItemsForDynamicOrderController;
import components.removeItemFromStoreScreen.RemoveItemFromStoreContoller;
import components.showStoresScreen.*;
import components.showItemsScreen.*;
import commonUI.*;
import components.showUsersScreen.ShowUsersController;
import components.updatePriceOfItemInStoreScreen.UpdateItemInStoreController;
import exceptions.DuplicateSerialIDException;
import exceptions.SerialIDNotExistException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
        //TODO
        //Change xml loading
        //new MenuOptionForReadingXMLFile().readFromXMLFile(businessLogic, SuperDuperMarketConstants.XML_PATH);
    }

    @FXML
    void loadFromXml(ActionEvent event) throws IOException {
        //GridPane gridPane = FXMLLoader.load(getClass().getResource(SuperDuperMarketConstants.SHOW_ITEMS_FXML_RESOURCE_IDENTIFIER));
        FXMLLoader loader = new FXMLLoader();
        URL LoadXMLFileFXML = getClass().getResource(SuperDuperMarketConstants.LOAD_XML_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(LoadXMLFileFXML);
        BorderPane borderPane = loader.load();
        LoadingXMLFileController loadingXMLFileController = loader.getController();
        loadingXMLFileController.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(borderPane);
    }

    @FXML
    void makeAnOrder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL makeAnOrderFXML = getClass().getResource(SuperDuperMarketConstants.MAKE_AN_ORDER_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(makeAnOrderFXML);
        BorderPane pane = loader.load();
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
                            chooseItemsForDynamicOrder(customer, isOrderStatic);
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

    public void chooseItemsForDynamicOrder(Customer customer, Boolean isOrderStatic) throws IOException {
        System.out.println(customer.getName());
        System.out.println("Order is static:" + isOrderStatic);
        FXMLLoader loader = new FXMLLoader();
        URL chooseItemsForDynamicOrderFXML = getClass().getResource(SuperDuperMarketConstants.CHOOSE_ITEM_FOR_DYNAMIC_ORDER_RESOURCE_IDENTIFEIR);
        loader.setLocation(chooseItemsForDynamicOrderFXML);
        BorderPane pane = loader.load();
        ChooseItemsForDynamicOrderController chooseItemsForDynamicOrderController = loader.getController();
        chooseItemsForDynamicOrderController.setBusinessLogic(businessLogic);
        System.out.println("Clicked on next");
        mainBorderPane.setCenter(pane);
    }

    @FXML
    void showItems(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL showItemsFXML = getClass().getResource(SuperDuperMarketConstants.SHOW_ITEMS_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(showItemsFXML);
        GridPane gridPane = loader.load();
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
        GridPane gridPane = loader.load();
        ShowStoresController showStoresController = loader.getController();
        showStoresController.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(gridPane);
    }

    @FXML
    void showUsers(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL showUsersFXML = getClass().getResource(SuperDuperMarketConstants.SHOW_USERS_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(showUsersFXML);
        GridPane gridPane = loader.load();
        ShowUsersController showUsersController = loader.getController();
        showUsersController.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(gridPane);
    }
    @FXML
    void addItemToStore(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL removeItemsFXML = getClass().getResource(SuperDuperMarketConstants.ADD_ITEM_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(removeItemsFXML);
        GridPane gridPane = loader.load();
        AddItemToStoreContoller addItemToStoreContoller = loader.getController();
        addItemToStoreContoller.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(gridPane);

    }

    @FXML
    void removeItemFromStore(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL removeItemsFXML = getClass().getResource(SuperDuperMarketConstants.REMOVE_ITEM_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(removeItemsFXML);
        GridPane gridPane = loader.load();
        RemoveItemFromStoreContoller removeItemFromStoreContoller = loader.getController();
        removeItemFromStoreContoller.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(gridPane);

    }

    @FXML
    void updatePriceOfItemInStore(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL removeItemsFXML = getClass().getResource(SuperDuperMarketConstants.UPDATE_ITEM_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(removeItemsFXML);
        GridPane gridPane = loader.load();
        UpdateItemInStoreController updateItemFromStoreContoller = loader.getController();
        updateItemFromStoreContoller.setBusinessLogic(businessLogic);
        mainBorderPane.setCenter(gridPane);

    }



}
