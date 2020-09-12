package components.MakeAnOrder;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import logic.AvailableItemInStore;
import logic.BusinessLogic;
import logic.Customer;
import logic.Store;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class MakeAnOrderController {

    @FXML DatePicker datePicker;
    @FXML RadioButton RadioButtonStatic;
    @FXML RadioButton RadioButtonDynamic;
    @FXML ComboBox<Customer> comboBoxCustomer;
    @FXML Button nextButton;
    @FXML BorderPane borderPane;
    @FXML ComboBox<Store> comboBoxStore;
    BusinessLogic businessLogic;
    SimpleBooleanProperty isCustomerSelected;
    SimpleBooleanProperty isDatePickerSelected;
    SimpleBooleanProperty isStoreSelected;
    SimpleBooleanProperty isNextClicked;

    Consumer<Boolean> isNextClickedConsumer;

    SimpleBooleanProperty isDynamicSelected;
    SimpleBooleanProperty isStaticSelected;
    Stage stage;


    //TODO
    public void setProperties(Consumer<Boolean> isNextClicked)
    {
        this.isNextClickedConsumer = isNextClicked;
    }

    @FXML
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
    public void setComboBoxStore()
    {
        comboBoxStore.setConverter(new StringConverter<Store>() {
            @Override
            public String toString(Store object) {
                return "Serial Number:" + object.getSerialNumber() + ", Name:" +object.getName();
            }

            @Override
            public Store fromString(String string) {
                return comboBoxStore.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });

        comboBoxCustomer.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
            {

            }
        });
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        setComboBoxCustomer();
        setComboBoxStore();

                // array of thing to observe to recompute binding - this gives the array

       /*comboBoxStores.disableProperty().bind(
                Bindings.or(
                        isOrderTypeSelected.not(),
                        comboBoxOrderType.valueProperty().isEqualTo(DYNAMIC_ORDER)));*/
        datePicker.disableProperty().bind(isCustomerSelected.not());
        RadioButtonDynamic.disableProperty().bind(isDatePickerSelected.not());
        RadioButtonStatic.disableProperty().bind(isDatePickerSelected.not());
        comboBoxStore.visibleProperty().bind(
                Bindings.and(
                        isStaticSelected,
                        isDynamicSelected.not()));
        nextButton.disableProperty().bind(
                Bindings.and(
                        isStoreSelected.not(),
                        isDynamicSelected.not()
                ));
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        final ObservableList<Store> stores = FXCollections.observableList(businessLogic.getStoresList());
        final ObservableList<Customer> customer = FXCollections.observableList(businessLogic.getUsersList());
        comboBoxCustomer.setItems(customer);
        comboBoxStore.setItems(stores);
        //TODO
        //Change xml loading
    }

    public MakeAnOrderController()
    {
        isStoreSelected = new SimpleBooleanProperty(false);
        isDatePickerSelected = new SimpleBooleanProperty(false);
        isCustomerSelected = new SimpleBooleanProperty(false);
        isNextClicked = new SimpleBooleanProperty(false);
        isStaticSelected = new SimpleBooleanProperty(false);
        isDynamicSelected = new SimpleBooleanProperty(false);
        isStaticSelected = new SimpleBooleanProperty(false);


    }

    public void chooseDate(ActionEvent actionEvent) {
        if(datePicker.getValue() == null)
        {
            isDynamicSelected.set(false);
        }
        else
        {
            isDatePickerSelected.set(true);
        }
    }


    public void chooseCustomer(ActionEvent actionEvent) {
        isCustomerSelected.set(true);

    }

    public void chooseDynamic(ActionEvent actionEvent) {
        if(RadioButtonDynamic.isSelected())
        {
            isDatePickerSelected.set(true);
            isDynamicSelected.set(true);
        }
        else
        {
            isDynamicSelected.set(false);
        }
    }

    public void chooseStatic(ActionEvent actionEvent) {
        if(RadioButtonStatic.isSelected())
        {
            isStaticSelected.set(true);
        }
        else
        {
            isStaticSelected.set(false);
        }
    }

    public void clickOnNextButton(ActionEvent actionEvent) throws IOException {
        isNextClicked.set(true);
        isNextClickedConsumer.accept(true);
    }

    public Customer getCustomer()
    {
        return comboBoxCustomer.getValue();
    }

    public boolean getStaticBoolean()
    {
        return isStaticSelected.getValue();
    }

    public Store getStore()
    {
        System.out.println("Inside getStore");
        System.out.println(comboBoxStore.getValue().getName());
        return comboBoxStore.getValue();
    }

    public void chooseStore(ActionEvent actionEvent) {
        isStoreSelected.setValue(true);
    }
}
