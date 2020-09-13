package components.makeAnOrderOption.Item.QuantityItem;

import components.makeAnOrderOption.Item.ItemTileController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import logic.BusinessLogic;
import javafx.scene.control.*;

public class QuantityItemController implements ItemTileController {

    @FXML Button minusButton;
    @FXML Button plusButton;
    @FXML Label priceValueLabel;
    @FXML Label priceLabel;
    @FXML Label amountLabel;
    @FXML Label itemNameLabel;
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
        Integer amount = Integer.parseInt(amountLabel.getText());
        if(amount > 0)
        {
            amount-=1;
            amountLabel.setText(amount.toString());
        }
    }

    public void clickedOnPlusButton(ActionEvent actionEvent) {
        Integer amount = Integer.parseInt(amountLabel.getText());
        amount+=1;
        amountLabel.setText(amount.toString());
    }

    public void setPriceLabelsToUnvisible()
    {
        priceValueLabel.setVisible(false);
        priceLabel.setVisible(false);
    }

    public void setItemNameLabel(String itemName)
    {
        itemNameLabel.setText(itemName);
    }

    public void setItemPriceLabel(Integer price)
    {
        priceValueLabel.setText(price.toString());
    }

    public Integer getAmount() {
        //TODO
        //Check if this is really double
        return Integer.parseInt(amountLabel.getText());
    }
}
