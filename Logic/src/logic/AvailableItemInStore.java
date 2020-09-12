package logic;

import javafx.beans.property.SimpleIntegerProperty;

public class AvailableItemInStore extends Item {
    private SimpleIntegerProperty pricePerUnit;

    public AvailableItemInStore(Integer serialNumber, String name, TypeOfMeasure purchaseCategory, int pricePerUnit) {
        super(serialNumber, name, purchaseCategory);
        this.pricePerUnit = new SimpleIntegerProperty(pricePerUnit);
    }

   public AvailableItemInStore(Integer serialNumber, String name, TypeOfMeasure purchaseCategory) {
        super(serialNumber, name, purchaseCategory);
    }

    public AvailableItemInStore(Item item, int price) {
        super(item);
        this.pricePerUnit = new SimpleIntegerProperty(price);
    }

    public AvailableItemInStore(Item item) {
        super(item);
    }

    public AvailableItemInStore(AvailableItemInStore availableItemInStore) {
        super(availableItemInStore);
        this.pricePerUnit = availableItemInStore.pricePerUnit;
    }

    public int getPricePerUnit()
    {
        return pricePerUnit.getValue();
    }
    public void setPricePerUnit(int pricePerUnit) {this.pricePerUnit = new SimpleIntegerProperty(pricePerUnit);}
}
