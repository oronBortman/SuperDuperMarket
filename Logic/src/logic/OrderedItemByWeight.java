package logic;

import java.util.Objects;

public class OrderedItemByWeight extends OrderedItem{
    private double amountOfItemOrderedByWeight;
    private static final int amountOfItemOrderedByUnitsInItemWithWeight = 1;
    public OrderedItemByWeight(Integer serialNumber, String name, int price, double amountOfItemOrderedByWeight) {
        super(serialNumber, name, TypeOfMeasure.Weight, price, amountOfItemOrderedByUnitsInItemWithWeight);
        this.amountOfItemOrderedByWeight = amountOfItemOrderedByWeight;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderedItemByWeight that = (OrderedItemByWeight) o;
        return Double.compare(that.amountOfItemOrderedByWeight, amountOfItemOrderedByWeight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amountOfItemOrderedByWeight);
    }

    OrderedItemByWeight(Integer serialNumber, String name, double amountOfItemOrderedByWeight) {
        super(serialNumber, name, TypeOfMeasure.Weight, amountOfItemOrderedByUnitsInItemWithWeight);
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
