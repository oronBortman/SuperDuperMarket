public class OrderedItem extends SelledItemInStore{
    private int amountOfItemOrdered;
    private int TotalCostOfItemOrdered;


    OrderedItem(Integer serialNumber, String name, TypeOfMeasure purchaseCategory, int price) {
        super(serialNumber, name, purchaseCategory, price);
    }
}
