public class OrderedItemByQuantity extends OrderedItem{

    OrderedItemByQuantity(Integer serialNumber, String name, int price, int amountOfItemOrderedByUnits) {
        super(serialNumber, name, TypeOfMeasure.Quantity, price, amountOfItemOrderedByUnits);
    }

    @Override
    public double getTotalPriceOfItemOrderedByTypeOfMeasure()
    {
        return getAmountOfItemOrderedByUnits() * getPricePerUnit();
    }

    public void increaseAmountOfItemOrderedByUnits(int UnitsToAdd)
    {
        setAmountOfItemOrderedByUnits(getAmountOfItemOrderedByUnits() + UnitsToAdd);
    }

}
