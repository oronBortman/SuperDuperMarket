package components.addingDiscounts.Item.WeightItem;

import components.addingDiscounts.Item.ItemTileController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.AvailableItemInStore;
import logic.BusinessLogic;

public class WeightItemController implements ItemTileController {

    @FXML TextField textFieldWeight;
    @FXML Label labelForAdditional;
    @FXML TextField textFieldForAdditional;
    @FXML Label itemNameLabel;
    Stage stage;
    String errorMessage=null;
    BusinessLogic businessLogic;
    AvailableItemInStore availableItemInStore;
    SimpleBooleanProperty isItemInThenYouGet;

    public WeightItemController()
    {
        isItemInThenYouGet = new SimpleBooleanProperty(false);
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private void initialize() {

    }

    @Override
    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
        //TODO
        //Change xml loading
    }

    @Override
    public void setProperties()
    {
        setForAdditionalOptionProperties();
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
        return Double.parseDouble(textFieldWeight.getText());
    }

    @Override
    public Integer getAdditionalAmount() {
        return Integer.parseInt(textFieldForAdditional.getText());
    }
    public boolean weightTextFieldIsEmpty()
    {
        return textFieldWeight.getText().equals("");
    }

    public boolean forAdditionalTextFieldIsEmpty()
    {
        return textFieldForAdditional.getText().equals("");
    }

    @Override
    public AvailableItemInStore getAvailableItemInStore()
    {
        return availableItemInStore;
    }

    public boolean checkIfWeightFieldIsOK()
    {
        Boolean weightFieldIsOK=false;
        if(weightTextFieldIsEmpty() == false)
        {
            try {
                Double amount = getAmount();
                if (amount > 0)
                {
                    weightFieldIsOK=true;
                }
            }
            catch ( NumberFormatException exception)
            {
            }
        }
        return weightFieldIsOK;
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

    public String getWeightErrorMessage()
    {
        String errorMessage="";
        if(weightTextFieldIsEmpty())
        {
            errorMessage="*Weight field is empty";
        }
        else
        {
            try
            {
                Double amount = getAmount();
                if(amount <=0 )
                {
                    errorMessage="*Weight field must contain a positive number";

                }
            }
            catch ( NumberFormatException exception)
            {
                errorMessage="*Weight field must contain a positive number";
            }
        }
        return errorMessage;
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
