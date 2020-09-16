package components.addItem;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import logic.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toCollection;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddItemController {

    @FXML private TextField TextFieldSerialID;
    @FXML private TextField TextFieldItemName;
    @FXML private Label LabelErrorSerialID;
    @FXML private Label LabelErrorItemName;
    @FXML private RadioButton RadioButtonQuantity;
    @FXML private RadioButton RadioButtonWeight;
    @FXML private Button ButtonAddStore;
    @FXML private Button buttonAddItem;
    @FXML private Label LabelErrorTypeOfMeasure;


    BusinessLogic businessLogic;
    SimpleBooleanProperty textFieldSerialIDEmpty;
    SimpleBooleanProperty textFieldNameEmpty;
    SimpleBooleanProperty isRadioButtonQuantitySelected;
    SimpleBooleanProperty isRadioButtonWeightSelected;

    Consumer<Boolean> isItemAdded;

    Item addedItem=null;


    public AddItemController() {
        isRadioButtonQuantitySelected = new SimpleBooleanProperty(false);
        isRadioButtonWeightSelected = new SimpleBooleanProperty(false);
        textFieldSerialIDEmpty = new SimpleBooleanProperty(true);
        textFieldNameEmpty = new SimpleBooleanProperty(true);
    }
    // final ObservableList<SimpleBooleanProperty> componentsNeedToBeFieldForAddingStore =
    //  FXCollections.observableArrayList(tableIsEmpty,textFieldPPKEmpty,textFieldNameEmpty,textFieldSerialIDEmpty);

    public void initialize() {
    }

    public void setProperties(Consumer<Boolean> isItemAdded)
    {
        this.isItemAdded = isItemAdded;
    }

    public void setBusinessLogic(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }


    public boolean checkSerialNumberAndAddErrorMessage() {
        boolean serialIdIsOK = false;
        if (isTextFieldSerialIDEmpty()) {
            LabelErrorSerialID.setText("* must enter serial ID");
        } else {
            try {
                int serialID = getEnteredSerialID();
                //TODO
                //NEED to check there is no more store with there serial id
                if (serialID > 0) {
                    if (businessLogic.checkIfItemIdExists(serialID)) {
                        LabelErrorSerialID.setText("* There is already an item with this serial ID");
                    } else {
                        serialIdIsOK = true;
                        LabelErrorSerialID.setText("");
                    }
                } else {
                    LabelErrorSerialID.setText("* must enter a whole positive number");
                }
            } catch (NumberFormatException exception) {
                LabelErrorSerialID.setText("* must enter a whole positive number");
            }
        }
        return serialIdIsOK;
    }

    public boolean checkNameOfItemAndAddErrorMessage() {
        boolean nameIsOK = false;

        if (isTextFieldItemNameEmpty()) {
            LabelErrorItemName.setText("* item name can't be empty");
        } else {
            nameIsOK = true;
            LabelErrorItemName.setText("");
        }
        return nameIsOK;
    }


    public void clickedOnButtonAddItem(ActionEvent actionEvent) {

        SDMLocation location = null;
        boolean serialIdIsOK = checkSerialNumberAndAddErrorMessage();
        boolean isNameOK = checkNameOfItemAndAddErrorMessage();
        boolean isTypeOfMeasureOK =false;
        //TODO
        Item newItemToAdd=null;

        if(isRadioButtonQuantitySelected.getValue() || isRadioButtonWeightSelected.getValue() )
        {
            LabelErrorTypeOfMeasure.setText("");
            isTypeOfMeasureOK = true;
        }
        else
        {
            LabelErrorTypeOfMeasure.setText("* item must have a measure type");
        }

        if ( serialIdIsOK && isNameOK && isTypeOfMeasureOK) {
            System.out.println("GREAT!!!");
            String nameOfItem = TextFieldItemName.getText();
            if(isRadioButtonQuantitySelected.getValue())
            {
                newItemToAdd = new Item(getEnteredSerialID(), nameOfItem, Item.TypeOfMeasure.Quantity);
                System.out.println("Added quantity!!!");

            }
            else if(isRadioButtonWeightSelected.getValue())
            {
                newItemToAdd = new Item(getEnteredSerialID(), nameOfItem, Item.TypeOfMeasure.Weight);
                System.out.println("Added Weight!!!");

            }
            businessLogic.addItem(newItemToAdd);
            addedItem=newItemToAdd;
            isItemAdded.accept(true);
        }
        clearAll();
    }

    public void clearAll() {
        TextFieldSerialID.setText("");
        TextFieldItemName.setText("");

    }

    public Integer getEnteredSerialID() {
        return Integer.parseInt(TextFieldSerialID.getText());
    }

    public boolean isTextFieldSerialIDEmpty() {
        return TextFieldSerialID.getText().equals("");
    }

    public boolean isTextFieldItemNameEmpty() {
        return TextFieldItemName.getText().equals("");
    }



    public Item getItem()
    {
        return addedItem;
    }


    @FXML
    public void chooseQuantityRadioButton(ActionEvent actionEvent)
    {
        if(RadioButtonQuantity.isSelected())
        {
            isRadioButtonQuantitySelected.set(true);
            isRadioButtonWeightSelected.set(false);
            RadioButtonWeight.setSelected(false);

        }
        else
        {
            isRadioButtonQuantitySelected.set(false);
        }
    }

    @FXML
    public void chooseWeightRadioButton(ActionEvent actionEvent)
    {
        if(RadioButtonWeight.isSelected())
        {
            isRadioButtonWeightSelected.set(true);
            isRadioButtonQuantitySelected.set(false);
            RadioButtonQuantity.setSelected(false);

        }
        else
        {
            isRadioButtonWeightSelected.set(false);
        }
    }

}
