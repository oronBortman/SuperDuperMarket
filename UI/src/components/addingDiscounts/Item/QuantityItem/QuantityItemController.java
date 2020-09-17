package components.addingDiscounts.Item.QuantityItem;

import components.addingDiscounts.Item.ItemTileController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.AvailableItemInStore;
import logic.BusinessLogic;

public class QuantityItemController implements ItemTileController {

    @FXML Button minusButton;
    @FXML Button plusButton;
    @FXML Label labelForAdditional;
    @FXML TextField textFieldForAdditional;
    @FXML Label amountLabel;
    @FXML Label itemNameLabel;
    Stage stage;
    BusinessLogic businessLogic;
    SimpleBooleanProperty isItemInThenYouGet;
    AvailableItemInStore availableItemInStore;

    public QuantityItemController()
    {
        isItemInThenYouGet = new SimpleBooleanProperty(false);
    }
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private void initialize()
    {

    }

    @Override
    public void setProperties()
    {
        setForAdditionalOptionProperties();
    }

    @Override
    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        //TODO
        //Change xml loading
    }

    public void clickedOnMinusButton(ActionEvent actionEvent) {
        Integer amount = Integer.parseInt(amountLabel.getText());
        if(amount > 1)
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

    private void setForAdditionalOptionProperties()
    {
        labelForAdditional.visibleProperty().bind(isItemInThenYouGet);
        textFieldForAdditional.visibleProperty().bind(isItemInThenYouGet);

    }

    @Override
    public void setAvailableItemInStore(AvailableItemInStore availableItemInStore) {
        this.availableItemInStore = availableItemInStore;
        setItemFields();
    }

    @Override
    public void setIsItemInThenYouGet(boolean isItemInThenYouGet)
    {
        this.isItemInThenYouGet.set(isItemInThenYouGet);
    }

    public void setItemFields()
    {
        setItemNameLabel(availableItemInStore.getName());
    }

    public void setItemNameLabel(String itemName)
    {
        itemNameLabel.setText(itemName);
    }

    @Override
    public Double getAmount() {
        //TODO
        //Check if this is really double
        return Double.parseDouble(amountLabel.getText());
    }

    @Override
    public Integer getAdditionalAmount() {
        return Integer.parseInt(textFieldForAdditional.getText());
    }

    @Override
    public AvailableItemInStore getAvailableItemInStore()
    {
        return availableItemInStore;
    }


    public boolean forAdditionalTextFieldIsEmpty()
    {
        return textFieldForAdditional.getText().equals("");
    }

    public boolean checkIfForAdditionalFieldIsOK()
    {
        Boolean forAdditionalFieldIsOK=false;
        if(forAdditionalTextFieldIsEmpty() == false)
        {
            try {
                Integer amount = getAdditionalAmount();
                if (amount >= 0)
                {
                    forAdditionalFieldIsOK=true;
                }
            }
            catch ( NumberFormatException exception)
            {
            }
        }
        return forAdditionalFieldIsOK;
    }


    public String getForAdditionalErrorMessage()
    {
        String errorMessage="";
        if(forAdditionalTextFieldIsEmpty())
        {
            errorMessage="*for additional field is empty";
        }
        else
        {
            try
            {
                Integer amount = getAdditionalAmount();
                if(amount < 0 )
                {
                    errorMessage="*for additional field must contain a non-negative number";
                }
            }
            catch ( NumberFormatException exception)
            {
                errorMessage="*for additional field must contain a non-negative number";
            }
        }
        return errorMessage;
    }
}
