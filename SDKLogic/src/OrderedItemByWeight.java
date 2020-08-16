public class OrderedItemByWeight extends OrderedItem{
    private double amountOfItemOrderedByWeight;
    private static final int amountOfItemOrderedByUnitsInItemWithWeight = 1;
    OrderedItemByWeight(Integer serialNumber, String name, int price, double amountOfItemOrderedByWeight) {
        super(serialNumber, name, TypeOfMeasure.Weight, price, amountOfItemOrderedByUnitsInItemWithWeight);
        this.amountOfItemOrderedByWeight = amountOfItemOrderedByWeight;

    }

    @Override
    public double getTotalPriceOfItemOrderedByTypeOfMeasure()
    {
        return amountOfItemOrderedByWeight * getPricePerUnit();
    }

    @Override
    public double getTotalAmountOfItemOrderedByTypeOfMeasure()
    {
        return amountOfItemOrderedByWeight;
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
