package commonUI;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class GeneralUIMethods {


    public static boolean  checkIfStringIsValidPriceAndSetError(String priceStr, Label label)
    {
        if (priceStr == null) {
            return false;
        }
        try {
            Integer priceInt = Integer.parseInt(priceStr);
            if(priceInt < 0)
            {
                setAnErrorToStatusAfterClickedOnButtonLabel("Price can't be negative", label);
                return false;
            }
        } catch (NumberFormatException nfe) {
            setAnErrorToStatusAfterClickedOnButtonLabel("Price need to be a whole number", label);
            return false;
        }
        return true;
    }

    public static void setAnErrorToStatusAfterClickedOnButtonLabel(String errorMessage, Label label)
    {
        label.setText(errorMessage);
        label.setTextFill(Color.RED);
    }

    public static void initiateStatusAfterClickedOnButtonLabel(Label label)
    {
        label.setText("");
        label.setTextFill(Color.BLACK);
    }

}
