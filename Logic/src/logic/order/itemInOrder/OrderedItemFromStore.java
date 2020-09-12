package logic.order.itemInOrder;

import logic.Item;
import logic.AvailableItemInStore;

import java.util.Objects;

public abstract class OrderedItemFromStore extends AvailableItemInStore {
    private int amountOfItemOrderedByUnits;

    /*logic.Orders.orderItems.OrderedItem(Integer serialNumber, String name, TypeOfMeasure purchaseCategory, int price) {
        super(serialNumber, name, purchaseCategory, price);
    }*/

    public OrderedItemFromStore(Integer serialNumber, String name, TypeOfMeasure purchaseCategory, int price, int amountOfItemOrderedByUnits) {
        super(serialNumber, name, purchaseCategory, price);
        this.amountOfItemOrderedByUnits = amountOfItemOrderedByUnits;
    }

    public OrderedItemFromStore(Integer serialNumber, String name, TypeOfMeasure purchaseCategory, int amountOfItemOrderedByUnits) {
        super(serialNumber, name, purchaseCategory);
        this.amountOfItemOrderedByUnits = amountOfItemOrderedByUnits;
    }

    public OrderedItemFromStore(Item item, int amountOfItemOrderedByUnits) {
        super(item);
        this.amountOfItemOrderedByUnits = amountOfItemOrderedByUnits;
    }

    public OrderedItemFromStore(AvailableItemInStore availableItemInStore, int amountOfItemOrderedByUnits) {
        super(availableItemInStore);
        this.amountOfItemOrderedByUnits = amountOfItemOrderedByUnits;
    }

    public abstract Double getTotalPriceOfItemOrderedByTypeOfMeasure();
    public abstract Double getTotalAmountOfItemOrderedByTypeOfMeasure();

    public Integer getAmountOfItemOrderedByUnits()
    {
        return amountOfItemOrderedByUnits;
    }

    public double getTotalPriceOfItemOrderedByUnits()
    {
        return amountOfItemOrderedByUnits * getPricePerUnit();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderedItemFromStore)) return false;
        OrderedItemFromStore that = (OrderedItemFromStore) o;
        return amountOfItemOrderedByUnits == that.amountOfItemOrderedByUnits;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountOfItemOrderedByUnits);
    }

    public void setAmountOfItemOrderedByUnits(int amountOfItemOrderedByUnits)
    {
        this.amountOfItemOrderedByUnits = amountOfItemOrderedByUnits;
    }
}
