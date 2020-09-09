package components.MakeAnOrder;


import commonUI.SuperDuperMarketConstants;
import components.chooseAnItemForDynamicOrder.ChooseItemsForDynamicOrderController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    @FXML Button buttonTest;
    @FXML BorderPane borderPane;
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

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        setComboBoxCustomer();
      /* comboBoxStores.disableProperty().bind(
                Bindings.or(
                        isOrderTypeSelected.not(),
                        comboBoxOrderType.valueProperty().isEqualTo(DYNAMIC_ORDER)));*/
        datePicker.disableProperty().bind(isCustomerSelected.not());
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        final ObservableList<Store> stores = FXCollections.observableList(businessLogic.getStoresList());
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
        isNextClicked = new SimpleBooleanProperty(false);
        isStaticSelected = new SimpleBooleanProperty(false);


    }

    public void chooseDate(ActionEvent actionEvent) {
        isDatePickerSelected.set(true);
    }


    public void chooseCustomer(ActionEvent actionEvent) {
        isCustomerSelected.set(true);

    }

    public void chooseDynamic(ActionEvent actionEvent) {

    }

    public void chooseStatic(ActionEvent actionEvent) {
        isStaticSelected.set(true);
    }

    public void clickOnNextButton(ActionEvent actionEvent) throws IOException {
        isNextClicked.set(true);
        isNextClickedConsumer.accept(true);
        //System.out.println(customer.getName());
        //System.out.println("Order is statc:" + isOrderStatic);
    }

    public Customer getCustomer()
    {
        return comboBoxCustomer.getValue();
    }

    public boolean getStaticBoolean()
    {
        return isStaticSelected.getValue();
    }
}
