public class OrderedItem extends SelledItemInStore{
    private int amountOfItemOrdered;
    private int TotalCostOfItemOrdered;


    OrderedItem(Integer serialNumber, String name, TypeOfMeasure purchaseCategory) {
        super(serialNumber, name, purchaseCategory);
    }
}
