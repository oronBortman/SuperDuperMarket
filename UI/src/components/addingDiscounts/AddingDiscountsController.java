package components.addingDiscounts;

import components.addingDiscounts.Item.ItemTileController;
import components.addingDiscounts.Item.QuantityItem.QuantityItemController;
import components.addingDiscounts.Item.WeightItem.WeightItemController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import logic.AvailableItemInStore;
import logic.BusinessLogic;
import logic.Item;
import logic.Store;
import logic.common.SuperDuperMarketConstants;
import logic.discount.Discount;
import logic.discount.IfYouBuySDM;
import logic.discount.Offer;
import logic.discount.ThenYouGetSDM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static commonUI.SuperDuperMarketConstants.*;
import static java.util.stream.Collectors.toCollection;

public class AddingDiscountsController {

    @FXML private Pane PaneAddItems;
    @FXML private ComboBox<AvailableItemInStore> comboBoxChooseItemIfYouBuy;
    @FXML private Label LabelErrorOnTable;
    @FXML private Button buttonFinish;
    @FXML private TextField textFieldEnterDiscountName;
    @FXML private TableView offersTable;
    @FXML private TableColumn<Offer, String> serialIDCol;
    @FXML private TableColumn<Offer, String> nameCol;
    @FXML private TableColumn<Offer, String> quantityCol;
    @FXML private TableColumn<Offer, String> forAdditionalCol;
    @FXML private Button buttonAddItem;
    @FXML private ComboBox<AvailableItemInStore> comboBoxChooseItemThenYouGet;
    @FXML private ComboBox<Store> comboBoxChooseStore;
    @FXML private RadioButton radioButtonOperatorAllOrNothing;
    @FXML private RadioButton radioButtonOperatorOneOf;
    @FXML private RadioButton radioButtonOperatorIrrelevant;
    @FXML private HBox hboxItemIfYouBuy;
    @FXML private HBox hboxItemThenYouGet;
    @FXML private Label labelErrorInWeightFieldInThenYouGet;
    @FXML private Label labelErrorAdditionalPrice;
    @FXML private Label labelErrorDiscountName;
    @FXML private Label labelErrorChooseStore;
    @FXML private Label labelErrorChooseItemInIfYouGet;
    @FXML private Label labelErrorEnterWeightInIfYouBuy;
    @FXML private Label labelErrorOperator;
    @FXML private Label labelErrorOnTable;

    SimpleBooleanProperty isRadioButtonOperatorAllOrNothingSelected;
    SimpleBooleanProperty isRadioButtonOperatorIrrelavntSelected;
    SimpleBooleanProperty isRadioButtonOperatorOneOfSelected;
    SimpleBooleanProperty isStoreChosen;
    SimpleBooleanProperty isItemInIfYouBuyChosen;
    SimpleBooleanProperty isItemInThanYouGetChosen;

    BusinessLogic businessLogic;
    private ThenYouGetSDM thenYouGetSDM;
    List<Offer> offerList;
    ItemTileController currentItemTileControllerIfYouBuy;
    ItemTileController currentItemTileControllerThenYouGet;
    Map<Integer, AvailableItemInStore> mapAvailableItemsInChooseItemComboboxInThenYouGet;


    public AddingDiscountsController()
    {
        mapAvailableItemsInChooseItemComboboxInThenYouGet = new HashMap<Integer, AvailableItemInStore>();
        isStoreChosen = new SimpleBooleanProperty(false);
        isItemInIfYouBuyChosen = new SimpleBooleanProperty(false);
        isRadioButtonOperatorAllOrNothingSelected = new SimpleBooleanProperty(false);
        isRadioButtonOperatorIrrelavntSelected = new SimpleBooleanProperty(false);
        isRadioButtonOperatorOneOfSelected = new SimpleBooleanProperty(false);
        isItemInThanYouGetChosen = new SimpleBooleanProperty(false);
        offerList = new ArrayList<>();
        thenYouGetSDM = new ThenYouGetSDM();
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        final ObservableList<Store> stores = FXCollections.observableList(businessLogic.getStoresList());
        comboBoxChooseStore.setItems(stores);

    }

    private void setProperties()
    {
        comboBoxChooseItemIfYouBuy.disableProperty().bind(isStoreChosen.not());
        comboBoxChooseItemThenYouGet.disableProperty().bind(isStoreChosen.not());
        buttonAddItem.disableProperty().bind(isItemInThanYouGetChosen.not());
        buttonFinish.disableProperty().bind(isItemInIfYouBuyChosen.not());
    }

    @FXML
    public void initialize()
    {
        initializeComboBoxChooseItemIfYouBuy();
        initializeComboBoxChooseItemThenYouGet();
        initializeComboBoxChooseStore();
        initializeTable();
        setProperties();
    }

    private void initializeTable()
    {
        serialIDCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Offer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Offer, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getItemId().toString());
            }
        });
        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Offer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Offer, String> param) {
                return new ReadOnlyObjectWrapper<String>(businessLogic.getItemBySerialID(param.getValue().getItemId()).getName());
            }
        });
        quantityCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Offer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Offer, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getQuantity().toString());
            }
        });
        forAdditionalCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Offer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Offer, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getForAdditional().toString());
            }
        });
    }

    private void initializeComboBoxChooseStore()
    {
        comboBoxChooseStore.setConverter(new StringConverter<Store>() {
            @Override
            public String toString(Store object) {
                return "Serial Number:" + object.getSerialNumber() + ", Name:" +object.getName();
            }

            @Override
            public Store fromString(String string) {
                return comboBoxChooseStore.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void initializeComboBoxChooseItemIfYouBuy()
    {
        comboBoxChooseItemIfYouBuy.setConverter(new StringConverter<AvailableItemInStore>() {
            @Override
            public String toString(AvailableItemInStore object) {
                return "Serial Number:" + object.getSerialNumber() + ", Name:" +object.getName();
            }

            @Override
            public AvailableItemInStore fromString(String string) {
                return null;
            }
        });
    }

    private void initializeComboBoxChooseItemThenYouGet()
    {
        comboBoxChooseItemThenYouGet.setConverter(new StringConverter<AvailableItemInStore>() {
            @Override
            public String toString(AvailableItemInStore object) {
                return "Serial Number:" + object.getSerialNumber() + ", Name:" +object.getName();
            }

            @Override
            public AvailableItemInStore fromString(String string) {
                return null;
            }
        });
    }

    private void setComboBoxChooseItemIfYouBuy()
    {
        if(comboBoxChooseStore != null)
        {
            Store store;
            store = comboBoxChooseStore.getValue();
            final ObservableList<AvailableItemInStore> availableItemInStores = FXCollections.observableList(store.getAvailableItemsList());
            comboBoxChooseItemIfYouBuy.setItems(availableItemInStores);
        }
    }

    private void setComboBoxChooseItemThenYouGet()
    {
        if(comboBoxChooseStore != null)
        {
            Store store;
            store = comboBoxChooseStore.getValue();
            final ObservableList<AvailableItemInStore> availableItemInStores = FXCollections.observableList(store.getAvailableItemsList());
            comboBoxChooseItemThenYouGet.setItems(availableItemInStores);
            mapAvailableItemsInChooseItemComboboxInThenYouGet=new HashMap<Integer, AvailableItemInStore>(store.getItemsSerialIDMap());
        }
    }

    @FXML void chooseStore(ActionEvent event) {
        if(comboBoxChooseStore.getValue() != null)
        {
            setComboBoxChooseItemIfYouBuy();
            setComboBoxChooseItemThenYouGet();
            isStoreChosen.set(true);
        }
    }

    @FXML
    void clickedOnButtonAddItemToThenYouGet(ActionEvent event)
    {
        System.out.println("AAAA");
        boolean itemIsOK=false;
        if (currentItemTileControllerThenYouGet instanceof WeightItemController)
        {
            System.out.println("BBBB");
            itemIsOK=checkWeightItemFieldsAndAddOfferToThenYouGet();
        }
        else if (currentItemTileControllerThenYouGet instanceof QuantityItemController)
        {
            System.out.println("CCCC");
            itemIsOK=checkQuantityItemFieldsAndAddOfferToThenYouGet();
        }
        if(itemIsOK)
        {
            addOfferToThenYouGet();
            isItemInThanYouGetChosen.set(false);
        }


    }

    private boolean checkWeightItemFieldsAndAddOfferToThenYouGet()
    {
        System.out.println("In weightt");
        boolean isWeightFieldOK = false;
        boolean isForAdditionalOK = false;

        WeightItemController weightItemController = (WeightItemController) currentItemTileControllerThenYouGet;
        if(weightItemController.checkIfWeightFieldIsOK() == false)
        {
            labelErrorInWeightFieldInThenYouGet.setText(weightItemController.getWeightErrorMessage());
        }
        else
        {
            labelErrorInWeightFieldInThenYouGet.setText("");
            isWeightFieldOK = true;
        }
        if(weightItemController.checkIfForAdditionalFieldIsOK() == false)
        {
            labelErrorAdditionalPrice.setText(weightItemController.getForAdditionalErrorMessage());
        }
        else
        {
            labelErrorAdditionalPrice.setText("");
            isForAdditionalOK = true;
        }
        return isForAdditionalOK && isWeightFieldOK;


    }

    private boolean checkQuantityItemFieldsAndAddOfferToThenYouGet()
    {
        System.out.println("In quantity");

        boolean isForAdditionalOK = false;

        QuantityItemController quantityItemController = (QuantityItemController) currentItemTileControllerThenYouGet;

        if(quantityItemController.checkIfForAdditionalFieldIsOK() == false)
        {
            labelErrorAdditionalPrice.setText(quantityItemController.getForAdditionalErrorMessage());
        }
        else
        {
            labelErrorAdditionalPrice.setText("");
            isForAdditionalOK = true;
        }
        return isForAdditionalOK;
    }


    public boolean checkIfItemInIfYouBuyIsValid()
    {
        boolean itemIsValid = false;
        if (currentItemTileControllerIfYouBuy instanceof WeightItemController)
        {
            itemIsValid=checkWeightItemFieldsInIfYouBuy();
        }
        else if(currentItemTileControllerIfYouBuy instanceof QuantityItemController)
        {
            itemIsValid=true;
        }
        return itemIsValid;
    }

    private boolean checkWeightItemFieldsInIfYouBuy()
    {
        boolean isWeightFieldOK = false;

        WeightItemController weightItemController = (WeightItemController) currentItemTileControllerIfYouBuy;
        if(weightItemController.checkIfWeightFieldIsOK() == false)
        {
            labelErrorEnterWeightInIfYouBuy.setText(weightItemController.getWeightErrorMessage());
        }
        else
        {
            labelErrorEnterWeightInIfYouBuy.setText("");
            isWeightFieldOK = true;
        }
        return isWeightFieldOK;
    }

    private void addOfferToThenYouGet()
    {
        System.out.println("A1");

        Integer serialID = currentItemTileControllerThenYouGet.getAvailableItemInStore().getSerialNumber();
        System.out.println("A2");

        Double quantity = currentItemTileControllerThenYouGet.getAmount();
        System.out.println("A3");

        Integer forAdditionalAmount = currentItemTileControllerThenYouGet.getAdditionalAmount();
        System.out.println("A4");

        Offer offer = new Offer(serialID, quantity, forAdditionalAmount);
        System.out.println("A5");

        thenYouGetSDM.addOfferToListFromSDMOffer(offer);
        System.out.println("A6");

        mapAvailableItemsInChooseItemComboboxInThenYouGet.remove(serialID);
        System.out.println("A7");

        setOffersTable();
        System.out.println("A8");

        comboBoxChooseItemThenYouGet.getSelectionModel().clearSelection();
        System.out.println("A9");

        hboxItemThenYouGet.getChildren().clear();
        System.out.println("A10");

        ObservableList<AvailableItemInStore> availableItemsInChoosenItemList = FXCollections.observableList(getAvailableItemsInChoosenItemList());
        System.out.println("A11");

        comboBoxChooseItemThenYouGet.setItems(availableItemsInChoosenItemList);
        System.out.println("A12");

    }

    public List<AvailableItemInStore> getAvailableItemsInChoosenItemList()
    {
        return mapAvailableItemsInChooseItemComboboxInThenYouGet.values().stream().collect(toCollection(ArrayList::new));
    }

    public boolean checkDiscountNameAndUpdateErrorMessage(boolean storeIsValid)
    {
        Store store = comboBoxChooseStore.getValue();
        boolean discountNameIsValid=false;
        if(textFieldEnterDiscountName.getText().equals(""))
        {
            labelErrorDiscountName.setText("TextField is empty");
        }
        else if(storeIsValid)
        {
            String discountName = textFieldEnterDiscountName.getText();
            if(store.checkIfDiscountHasUniqueName(discountName) == false)
            {
                labelErrorDiscountName.setText("Discount name exists in store");
            }
            else
            {
                labelErrorDiscountName.setText("");
                discountNameIsValid = true;
            }
        }
        return discountNameIsValid;
    }

    public boolean checkIfStoreIsValidAndUpdateErrorMessage()
    {
        boolean storeIsValid=false;
        if(comboBoxChooseStore.getValue() == null)
        {
            labelErrorChooseStore.setText("*comboBox is empty");
        }
        else
        {
            labelErrorChooseStore.setText("");
            storeIsValid = true;

        }
        return storeIsValid;
    }

    public boolean checkIfYouBuyAndUpdateErrorMessage()
    {
        boolean isIfYouBuyOK = false;
        if(comboBoxChooseItemIfYouBuy.getValue() == null)
        {
            labelErrorChooseItemInIfYouGet.setText("*comboBox is empty");
        }
        else if(checkIfItemInIfYouBuyIsValid() == true)
        {
            labelErrorChooseItemInIfYouGet.setText("");
            isIfYouBuyOK=true;
        }
        return isIfYouBuyOK;
    }

    @FXML
    void clickedOnButtonFinish(ActionEvent event) {
        boolean storeIsValid = checkIfStoreIsValidAndUpdateErrorMessage();;
        boolean discountNameIsValid = checkDiscountNameAndUpdateErrorMessage(storeIsValid);
        boolean itemInIfYouBuyIsValid = checkIfYouBuyAndUpdateErrorMessage();
        boolean operatorIsValid = checkIfOperatorSelectedAndUpdateErrorMessage();
        boolean tableOfOffersIsValid = checkIfTableNotEmptyAndUpdateErrorMessage();

        System.out.println(storeIsValid + " " + discountNameIsValid + " " + itemInIfYouBuyIsValid + " " + operatorIsValid + " " + tableOfOffersIsValid);
        if(storeIsValid && discountNameIsValid && itemInIfYouBuyIsValid && operatorIsValid && tableOfOffersIsValid)
        {
            System.out.println("Succeed!!!");
            AvailableItemInStore availableItemInStore = comboBoxChooseItemIfYouBuy.getValue();
            IfYouBuySDM ifYouBuySDM = new IfYouBuySDM(availableItemInStore.getSerialNumber(), currentItemTileControllerIfYouBuy.getAmount());
            String discountName = textFieldEnterDiscountName.getText();
            setThenYouGetAfterOperatorIsValid();
            Discount discount = new Discount(discountName, ifYouBuySDM, thenYouGetSDM);
            for(Offer offer :thenYouGetSDM.getOfferList())
            {
                System.out.println("Offer id !!!!!!!" + offer.getItemId() + offer.getQuantity() + offer.getForAdditional());
            }
            Store store = comboBoxChooseStore.getValue();
            store.addDiscountToStore(discount);
        }
        clearAll();
    }

    private void setThenYouGetAfterOperatorIsValid()
    {
        String operator="";
        if(isRadioButtonOperatorAllOrNothingSelected.get())
        {
            operator= SuperDuperMarketConstants.ALL_OR_NOTHING;
        }
        else if(isRadioButtonOperatorIrrelavntSelected.get())
        {
            operator=SuperDuperMarketConstants.IRRELEVANT;
        }
        else if(isRadioButtonOperatorOneOfSelected.get())
        {
            operator=SuperDuperMarketConstants.ONE_OF;
        }

        thenYouGetSDM.setOperator(operator);
        System.out.println("Operator$$$$#" + operator);
    }


    private boolean checkIfTableNotEmptyAndUpdateErrorMessage()
    {
        boolean tableNotEmpty=false;
        if(offersTable.getItems() != null)
        {
            tableNotEmpty=true;
        }
        else
        {
            labelErrorOnTable.setText("*Table is empty");
        }
        return tableNotEmpty;

    }
    private boolean checkIfOperatorSelectedAndUpdateErrorMessage()
    {
        boolean operatorIsValid = false;
        if(isRadioButtonOperatorOneOfSelected.getValue() || isRadioButtonOperatorIrrelavntSelected.getValue() || isRadioButtonOperatorAllOrNothingSelected.getValue())
        {
            operatorIsValid=true;
        }
        else
        {
            labelErrorOperator.setText("Need to choose operator");
        }
        return operatorIsValid;
    }
    private void setOffersTable(){

        final ObservableList<Offer> availableItemInStores = FXCollections.observableList(thenYouGetSDM.getOfferList());
        offersTable.setItems(availableItemInStores);
    }

    @FXML
    void chooseItemIfYouBuy(ActionEvent event) throws IOException {
        if(comboBoxChooseItemIfYouBuy.getValue() != null)
        {
            AvailableItemInStore availableItemInStore = comboBoxChooseItemIfYouBuy.getValue();
            setHboxItemIfYouBuy(availableItemInStore);
            isItemInIfYouBuyChosen.set(true);
        }
    }

    @FXML
    void chooseItemThenYouGet(ActionEvent event) throws IOException {
        System.out.println("In choose item");
        if(comboBoxChooseItemThenYouGet.getValue() != null)
        {
            System.out.println("Combobox not null");
            AvailableItemInStore availableItemInStore = comboBoxChooseItemThenYouGet.getValue();
            setHboxItemThenYouGet(availableItemInStore);
            isItemInThanYouGetChosen.set(true);
        }
        else
        {
            System.out.println("Combobox null");

        }
    }

    void setHboxItemThenYouGet(AvailableItemInStore availableItemInStore) throws IOException {
        if(availableItemInStore.getTypeOfMeasure() == Item.TypeOfMeasure.Quantity)
        {
            setItemTileThenYouGet(availableItemInStore, QUANTITY_ITEM_IN_ADDING_DISCOUNT, true, hboxItemThenYouGet);
        }
        else if(availableItemInStore.getTypeOfMeasure() == Item.TypeOfMeasure.Weight)
        {
            setItemTileThenYouGet(availableItemInStore, WEIGHT_ITEM_IN_ADDING_DISCOUNT, true, hboxItemThenYouGet);
        }
    }

    @FXML
    void setHboxItemIfYouBuy(AvailableItemInStore availableItemInStore) throws IOException {
        if(availableItemInStore.getTypeOfMeasure() == Item.TypeOfMeasure.Quantity)
        {
            setItemTileIfYouBuy(availableItemInStore, QUANTITY_ITEM_IN_ADDING_DISCOUNT, false, hboxItemIfYouBuy);
        }
        else if(availableItemInStore.getTypeOfMeasure() == Item.TypeOfMeasure.Weight)
        {
            setItemTileIfYouBuy(availableItemInStore, WEIGHT_ITEM_IN_ADDING_DISCOUNT, false, hboxItemIfYouBuy);
        }
    }



    private void setItemTileIfYouBuy(AvailableItemInStore availableItemInStore, String fxmlPath, Boolean isItemInThenYouGet, HBox hbox) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL itemFXML = getClass().getResource(fxmlPath);
        loader.setLocation(itemFXML);
        FlowPane flowPane = loader.load();
        currentItemTileControllerIfYouBuy = loader.getController();
        currentItemTileControllerIfYouBuy.setBusinessLogic(this.businessLogic);
        currentItemTileControllerIfYouBuy.setAvailableItemInStore(availableItemInStore);
        currentItemTileControllerIfYouBuy.setIsItemInThenYouGet(isItemInThenYouGet);
        currentItemTileControllerIfYouBuy.setProperties();
        hbox.getChildren().setAll(flowPane);
    }

    private void setItemTileThenYouGet(AvailableItemInStore availableItemInStore, String fxmlPath, Boolean isItemInThenYouGet, HBox hbox) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL itemFXML = getClass().getResource(fxmlPath);
        loader.setLocation(itemFXML);
        FlowPane flowPane = loader.load();
        currentItemTileControllerThenYouGet = loader.getController();
        currentItemTileControllerThenYouGet.setBusinessLogic(this.businessLogic);
        currentItemTileControllerThenYouGet.setAvailableItemInStore(availableItemInStore);
        currentItemTileControllerThenYouGet.setIsItemInThenYouGet(isItemInThenYouGet);
        currentItemTileControllerThenYouGet.setProperties();
        hbox.getChildren().setAll(flowPane);
    }


    @FXML
    void actionOnRadioButtonOperatorAllOrNothing(ActionEvent event) {
        if(radioButtonOperatorAllOrNothing.isSelected())
        {
            isRadioButtonOperatorAllOrNothingSelected.set(true);
            isRadioButtonOperatorIrrelavntSelected.set(false);
            isRadioButtonOperatorOneOfSelected.set(false);
            radioButtonOperatorIrrelevant.setSelected(false);
            radioButtonOperatorOneOf.setSelected(false);

        }
        else
        {
            isRadioButtonOperatorAllOrNothingSelected.set(false);
        }
    }

    @FXML
    void actionOnRadioButtonOperatorIrrelavnt(ActionEvent event) {
        if(radioButtonOperatorIrrelevant.isSelected())
        {
            isRadioButtonOperatorIrrelavntSelected.set(true);
            isRadioButtonOperatorAllOrNothingSelected.set(false);
            isRadioButtonOperatorOneOfSelected.set(false);
            radioButtonOperatorAllOrNothing.setSelected(false);
            radioButtonOperatorOneOf.setSelected(false);

        }
        else
        {
            isRadioButtonOperatorIrrelavntSelected.set(false);
        }
    }

    @FXML
    void actionOnRadioButtonOperatorOneOf(ActionEvent event)
    {
        if(radioButtonOperatorOneOf.isSelected())
        {
            isRadioButtonOperatorOneOfSelected.set(true);
            isRadioButtonOperatorIrrelavntSelected.set(false);
            isRadioButtonOperatorAllOrNothingSelected.set(false);
            radioButtonOperatorAllOrNothing.setSelected(false);
            radioButtonOperatorIrrelevant.setSelected(false);
        }
        else
        {
            isRadioButtonOperatorOneOfSelected.set(false);
        }
    }

    private void clearAll()
    {
        thenYouGetSDM=new ThenYouGetSDM();
        comboBoxChooseStore.getSelectionModel().clearSelection();
        comboBoxChooseItemThenYouGet.getItems().clear();
        comboBoxChooseItemIfYouBuy.getItems().clear();
        offersTable.getItems().clear();
        hboxItemIfYouBuy.getChildren().clear();
        hboxItemThenYouGet.getChildren().clear();
        isItemInIfYouBuyChosen.set(false);
        isItemInThanYouGetChosen.set(false);
    }

}
