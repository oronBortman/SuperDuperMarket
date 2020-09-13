package components.makeAnOrderOption.Item.WeightItem;

import components.makeAnOrderOption.Item.ItemTileController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.BusinessLogic;

public class WeightItemController implements ItemTileController {

    @FXML TextField WeightTextField;
    @FXML Label priceValueLabel;
    @FXML Label priceLabel;
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

    public Double getAmount() {
        //TODO
        //Check if this is really double
        return Double.parseDouble(WeightTextField.getText());
    }

}
