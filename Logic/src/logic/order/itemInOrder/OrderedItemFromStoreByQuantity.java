package logic.order.itemInOrder;

import logic.AvailableItemInStore;

public class OrderedItemFromStoreByQuantity extends OrderedItemFromStore {

    public OrderedItemFromStoreByQuantity(Integer serialNumber, String name, int price, int amountOfItemOrderedByUnits) {
        super(serialNumber, name, TypeOfMeasure.Quantity, price, amountOfItemOrderedByUnits);
    }

    public OrderedItemFromStoreByQuantity(Integer serialNumber, String name, int amountOfItemOrderedByUnits) {
        super(serialNumber, name, TypeOfMeasure.Quantity, amountOfItemOrderedByUnits);
    }

    public OrderedItemFromStoreByQuantity(AvailableItemInStore availableItemInStore, int amountOfItemOrderedByUnits) {
        super(availableItemInStore, amountOfItemOrderedByUnits);
    }



    @Override
    public Double getTotalPriceOfItemOrderedByTypeOfMeasure()
    {
        return new Double(getAmountOfItemOrderedByUnits() * getPricePerUnit());
    }

    @Override
    public Double getTotalAmountOfItemOrderedByTypeOfMeasure()
    {
        return new Double(getAmountOfItemOrderedByUnits());
    }

    public void increaseAmountOfItemOrderedByUnits(int UnitsToAdd)
    {
        setAmountOfItemOrderedByUnits(getAmountOfItemOrderedByUnits() + UnitsToAdd);
    }

}
