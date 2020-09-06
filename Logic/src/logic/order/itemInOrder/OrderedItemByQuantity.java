package logic.order.itemInOrder;

public class OrderedItemByQuantity extends OrderedItem{

    public OrderedItemByQuantity(Integer serialNumber, String name, int price, int amountOfItemOrderedByUnits) {
        super(serialNumber, name, TypeOfMeasure.Quantity, price, amountOfItemOrderedByUnits);
    }

    public OrderedItemByQuantity(Integer serialNumber, String name, int amountOfItemOrderedByUnits) {
        super(serialNumber, name, TypeOfMeasure.Quantity, amountOfItemOrderedByUnits);
    }

    @Override
    public double getTotalPriceOfItemOrderedByTypeOfMeasure()
    {
        return getAmountOfItemOrderedByUnits() * getPricePerUnit();
    }

    @Override
    public double getTotalAmountOfItemOrderedByTypeOfMeasure()
    {
        return getAmountOfItemOrderedByUnits();
    }

    public void increaseAmountOfItemOrderedByUnits(int UnitsToAdd)
    {
        setAmountOfItemOrderedByUnits(getAmountOfItemOrderedByUnits() + UnitsToAdd);
    }

}
