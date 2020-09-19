package components.showOption.showUsersScreen;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import logic.BusinessLogic;
import logic.Customer;

import java.text.DecimalFormat;

public class ShowUsersController {


    @FXML GridPane showUsersGridPane;
    @FXML TableView listOfUsersTable;
    BusinessLogic businessLogic;
    @FXML TableColumn<Customer, Integer> UserSerialNumberCol;
    @FXML TableColumn<Customer, String> NameOfUserCol;
    @FXML TableColumn<Customer, String> LocationCol;
    @FXML TableColumn<Customer, String> AmountOfOrdersCol;
    @FXML TableColumn<Customer, String> AverageOrderPriceCol;
    @FXML TableColumn<Customer, String> AverageDeliveryPrice;
    @FXML

    DecimalFormat decimalFormat = new DecimalFormat("#.00");

    private void initialize() {
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        setUsersTable();
    }

    public ShowUsersController()
    {

    }

    private void setUsersTable()
    {
        final ObservableList<Customer> dataOfUsers = FXCollections.observableList(businessLogic.getUsersList());
        UserSerialNumberCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("serialNumber"));
        NameOfUserCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));

        LocationCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Customer, String> param) {
                return new ReadOnlyObjectWrapper<String>("(" + param.getValue().getLocation().getX() + ","
                        + param.getValue().getLocation().getY() + ")");
            }
        });

        AmountOfOrdersCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Customer, String> param) {
                ReadOnlyObjectWrapper<String> readOnlyObjectWrapper;
                Customer customer = param.getValue();
                Integer amountOfOrders = customer.getAmountOfOrders();
                if(amountOfOrders > 0)
                {
                    System.out.println(amountOfOrders);
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>(amountOfOrders.toString());
                }
                else
                {
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>("0");
                }
                return readOnlyObjectWrapper;
            }
        });

        AverageOrderPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Customer, String> param) {
                ReadOnlyObjectWrapper<String> readOnlyObjectWrapper;
                Customer customer = param.getValue();
                if(customer.getMapOfClosedCustomerOrders().size() > 0)
                {
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>(decimalFormat.format(param.getValue().getAverageOrderPrice()));
                }
                else
                {
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>("0");
                }
                return readOnlyObjectWrapper;
            }
        });

        AverageDeliveryPrice.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Customer, String> param) {
                ReadOnlyObjectWrapper<String> readOnlyObjectWrapper;
                Customer customer = param.getValue();
                if(customer.getMapOfClosedCustomerOrders().size() > 0)
                {
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>(decimalFormat.format(param.getValue().getAverageDeliveryPrice()));
                }
                else
                {
                    readOnlyObjectWrapper = new ReadOnlyObjectWrapper<String>("0");
                }
                return readOnlyObjectWrapper;
            }
        });

        listOfUsersTable.setItems(dataOfUsers);
    }

}
