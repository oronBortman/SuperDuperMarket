package components.makeAnOrderOption.Item;

import javafx.stage.Stage;
import logic.BusinessLogic;

public interface ItemTileController {
    public void setBusinessLogic(BusinessLogic businessLogic);
    public void setStage(Stage stage);
    public void setPriceLabelsToUnvisible();
    public void setItemNameLabel(String itemName);
    public void setItemPriceLabel(Integer price);
}
