package components.QuantityItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import logic.BusinessLogic;
import javafx.scene.control.*;

public class QuantityItemController {

    @FXML Button minusButton;
    @FXML Button plusButton;
    @FXML Label priceVal;
    Stage stage;
    BusinessLogic businessLogic;
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private void initialize() {

    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        //TODO
        //Change xml loading
    }

    public void clickedOnMinusButton(ActionEvent actionEvent) {
    }

    public void clickedOnPlusButton(ActionEvent actionEvent) {
    }
}
