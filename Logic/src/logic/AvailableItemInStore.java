package logic;

import javafx.beans.property.SimpleIntegerProperty;

public class AvailableItemInStore extends Item {
    private SimpleIntegerProperty pricePerUnit;

    AvailableItemInStore(Integer serialNumber, String name, TypeOfMeasure purchaseCategory, int pricePerUnit) {
        super(serialNumber, name, purchaseCategory);
        this.pricePerUnit = new SimpleIntegerProperty(pricePerUnit);
    }

    AvailableItemInStore(Integer serialNumber, String name, TypeOfMeasure purchaseCategory) {
        super(serialNumber, name, purchaseCategory);
    }

    AvailableItemInStore(Item item, int price) {
        super(item);
        this.pricePerUnit = new SimpleIntegerProperty(price);
    }


    public int getPricePerUnit()
    {
        return pricePerUnit.getValue();
    }
    public void setPricePerUnit(int pricePerUnit) {this.pricePerUnit = new SimpleIntegerProperty(pricePerUnit);}
}
