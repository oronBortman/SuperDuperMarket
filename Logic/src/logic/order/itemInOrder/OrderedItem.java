package logic.order.itemInOrder;

import logic.AvailableItemInStore;

import java.util.Objects;

public abstract class OrderedItem extends AvailableItemInStore {
    private int amountOfItemOrderedByUnits;

    /*logic.Orders.orderItems.OrderedItem(Integer serialNumber, String name, TypeOfMeasure purchaseCategory, int price) {
        super(serialNumber, name, purchaseCategory, price);
    }*/

    public OrderedItem(Integer serialNumber, String name, TypeOfMeasure purchaseCategory, int price, int amountOfItemOrderedByUnits) {
        super(serialNumber, name, purchaseCategory, price);
        this.amountOfItemOrderedByUnits = amountOfItemOrderedByUnits;
    }

    public OrderedItem(Integer serialNumber, String name, TypeOfMeasure purchaseCategory, int amountOfItemOrderedByUnits) {
        super(serialNumber, name, purchaseCategory);
        this.amountOfItemOrderedByUnits = amountOfItemOrderedByUnits;
    }

    public abstract double getTotalPriceOfItemOrderedByTypeOfMeasure();
    public abstract double getTotalAmountOfItemOrderedByTypeOfMeasure();

    public int getAmountOfItemOrderedByUnits()
    {
        return amountOfItemOrderedByUnits;
    }

    public int getTotalPriceOfItemOrderedByUnits()
    {
        return amountOfItemOrderedByUnits * getPricePerUnit();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderedItem)) return false;
        OrderedItem that = (OrderedItem) o;
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
