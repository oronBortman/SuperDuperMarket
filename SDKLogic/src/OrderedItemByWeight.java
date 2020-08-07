public class OrderedItemByWeight extends OrderedItem{
    private double amountOfItemOrderedByWeight;
    private static final int amountOfItemOrderedByUnitsInItemWithWeight = 1;
    OrderedItemByWeight(Integer serialNumber, String name, int price, double amountOfItemOrderedByWeight) {
        super(serialNumber, name, TypeOfMeasure.Weight, price, amountOfItemOrderedByUnitsInItemWithWeight);

    }

    @Override
    public double getTotalPriceOfItemOrderedByTypeOfMeasure()
    {
        return amountOfItemOrderedByWeight * getPricePerUnit();
    }

    public void increaseAmountOfItemOrderedByWeight(double weightToAdd)
    {
        amountOfItemOrderedByWeight += weightToAdd;
    }

    public double getAmountOfItemOrderedByWeight()
    {
        return amountOfItemOrderedByWeight;
    }
}
