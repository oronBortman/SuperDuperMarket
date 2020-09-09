package components.MakeAnOrder;


import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import logic.AvailableItemInStore;
import logic.BusinessLogic;
import logic.Customer;
import logic.Store;

public class MakeAnOrderController {

    @FXML GridPane showStoresGridPane;
    @FXML ComboBox<Store> comboBoxStores;
    @FXML ComboBox<Customer> comboBoxCustomer;
    @FXML ComboBox<String> comboBoxOrderType;

    @FXML DatePicker chooseDatePicker;
    @FXML Label serialNumberValue;
    @FXML Label nameOfStoreValue;
    @FXML Label PPKValue;
    @FXML TableView listOfItemsTable;
    @FXML TableColumn<AvailableItemInStore, Integer> ItemSerialNumberCol;
    @FXML TableColumn<AvailableItemInStore, String> NameOfItemCol;
    @FXML TableColumn<AvailableItemInStore, String> TypeOfMeasureCol;
    @FXML TableColumn<AvailableItemInStore, Integer> PricePerUnitCol;
    @FXML TableColumn<AvailableItemInStore, String> TotalItemsSoledInStoreCol;

    BusinessLogic businessLogic;
    SimpleBooleanProperty isCustomerSelected;
    SimpleBooleanProperty isDatePickerSelected;

    SimpleBooleanProperty isStoreSelected;
    SimpleBooleanProperty isOrderTypeSelected;

    final String STATIC_ORDER = "Static Order";
    final String DYNAMIC_ORDER = "Dynamic Order";


    //TODO

    public void setCombBoxOrderType()
    {
        comboBoxOrderType.getItems().setAll(STATIC_ORDER, DYNAMIC_ORDER);
    }

    public void setComboBoxStores()
    {
        comboBoxStores.setConverter(new StringConverter<Store>() {
            @Override
            public String toString(Store object) {
                return "Serial Number:" + object.getSerialNumber() + ", Name:" +object.getName();
            }

            @Override
            public Store fromString(String string) {
                return comboBoxStores.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });

        comboBoxStores.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
            {
                serialNumberValue.setText(newval.getSerialNumber().toString());
                nameOfStoreValue.setText(newval.getName());
                PPKValue.setText(newval.getPPK().toString());
                // totalPaymentForDeliveriesVal.setText(newval.get);
            }
        });
    }

    public void setComboBoxCustomer()
    {


        comboBoxCustomer.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer object) {
                return "Serial Number:" + object.getSerialNumber() + ", Name:" +object.getName();
            }

            @Override
            public Customer fromString(String string) {
                return comboBoxCustomer.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });

        comboBoxCustomer.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
            {

            }
        });
    }


    @FXML
    private void initialize() {
        setComboBoxStores();
        setComboBoxCustomer();
        setCombBoxOrderType();
       comboBoxStores.disableProperty().bind(
                Bindings.or(
                        isOrderTypeSelected.not(),
                        comboBoxOrderType.valueProperty().isEqualTo(DYNAMIC_ORDER)));
       comboBoxOrderType.disableProperty().bind(
               Bindings.or(
                       isCustomerSelected.not(),
                       isDatePickerSelected.not()));
        //comboBoxStores.disableProperty().bind();
        //listOfItemsTable.disableProperty().bind(isItemSelected.not());
        chooseDatePicker.disableProperty().bind(isCustomerSelected.not());
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        final ObservableList<Store> stores = FXCollections.observableList(businessLogic.getStoresList());
        comboBoxStores.setItems(stores);
        final ObservableList<Customer> customer = FXCollections.observableList(businessLogic.getUsersList());
        comboBoxCustomer.setItems(customer);
        //TODO
        //Change xml loading
    }

    public MakeAnOrderController()
    {
        isStoreSelected = new SimpleBooleanProperty(false);
        //isItemSelected = new SimpleBooleanProperty(false);
        isDatePickerSelected = new SimpleBooleanProperty(false);
        isCustomerSelected = new SimpleBooleanProperty(false);
        isOrderTypeSelected = new SimpleBooleanProperty(false);;

    }

    @FXML
    void chooseStore(ActionEvent event){

        setItemsTable();
        setOrdersTable();

    }

    private void setItemsTable()
    {
        final ObservableList<AvailableItemInStore> dataOfItems = FXCollections.observableList(comboBoxStores.getValue().getItemsList());

        ItemSerialNumberCol.setCellValueFactory(new PropertyValueFactory<AvailableItemInStore, Integer>("serialNumber"));
        NameOfItemCol.setCellValueFactory(new PropertyValueFactory<AvailableItemInStore, String>("name"));
        PricePerUnitCol.setCellValueFactory(new PropertyValueFactory<AvailableItemInStore, Integer>("pricePerUnit"));
        TotalItemsSoledInStoreCol.setCellValueFactory(new Callback<CellDataFeatures<AvailableItemInStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<AvailableItemInStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(businessLogic.getTotalAmountOfSoledItem(param.getValue().getSerialNumber()).toString());
            }
        });

        TypeOfMeasureCol.setCellValueFactory(new Callback<CellDataFeatures<AvailableItemInStore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<AvailableItemInStore, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getTypeOfMeasureStr());
            }
        });
        listOfItemsTable.setItems(dataOfItems);
    }

    private void setOrdersTable()
    {
        /*final ObservableList<Item> dataOfItems = FXCollections.observableList(comboBoxStores.getValue().getItemsList());

        for(TableColumn tableColumn : (ObservableList<TableColumn>)listOfItemsTable.getColumns())
        {
            if(tableColumn.getId().equals("itemSerialNumberCol"))
            {
                System.out.println(tableColumn.getId());
                tableColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("serialNumber"));
            }
            if(tableColumn.getId().equals("NameOfItemCol"))
            {
                System.out.println(tableColumn.getId());
                tableColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
            }
        }
        listOfItemsTable.setItems(dataOfItems);*/
    }

    public void comboBoxCustomer(ActionEvent actionEvent) {
        isCustomerSelected.set(true);

    }

    public void chooseDate(ActionEvent actionEvent) {
        isDatePickerSelected.set(true);
    }

    public void chooseOrderType(ActionEvent actionEvent) {
        isOrderTypeSelected.set(true);
    }
}
