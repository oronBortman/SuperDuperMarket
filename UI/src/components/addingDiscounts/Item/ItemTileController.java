package components.addingDiscounts.Item;

import javafx.stage.Stage;
import logic.AvailableItemInStore;
import logic.BusinessLogic;

public interface ItemTileController {
    public void setBusinessLogic(BusinessLogic businessLogic);

    public void setStage(Stage stage);

    public void setItemNameLabel(String itemName);

    public void setAvailableItemInStore(AvailableItemInStore availableItemInStore);

    public void setIsItemInThenYouGet(boolean isItemInThenYouGet);

    public void setProperties();

    public Double getAmount();
    public Integer getAdditionalAmount();

    public AvailableItemInStore getAvailableItemInStore();
}
