public abstract class OrderedItem extends SelledItemInStore{
    private int amountOfItemOrderedByUnits;

    /*OrderedItem(Integer serialNumber, String name, TypeOfMeasure purchaseCategory, int price) {
        super(serialNumber, name, purchaseCategory, price);
    }*/

    OrderedItem(Integer serialNumber, String name, TypeOfMeasure purchaseCategory, int price, int amountOfItemOrderedByUnits) {
        super(serialNumber, name, purchaseCategory, price);
        this.amountOfItemOrderedByUnits = amountOfItemOrderedByUnits;
    }

    public abstract double getTotalPriceOfItemOrderedByTypeOfMeasure();

    public int getAmountOfItemOrderedByUnits()
    {
        return amountOfItemOrderedByUnits;
    }

    public int getTotalPriceOfItemOrderedByUnits()
    {
        return amountOfItemOrderedByUnits * getPricePerUnit();
    }

    public void setAmountOfItemOrderedByUnits(int amountOfItemOrderedByUnits)
    {
        this.amountOfItemOrderedByUnits = amountOfItemOrderedByUnits;
    }



}
