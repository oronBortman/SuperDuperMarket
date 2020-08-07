public class ClosedOrder extends Order {
    private String serialNumber;
    private double deliveryPrice;
    private double totalPriceOfOrder;
    private int totalAmountOfItems;
    private double totalPriceOfItems;
    public int calcTotalAmountOfItemsByUnit()
    {
        return getOrderedItems().values().stream().mapToInt(OrderedItem::getTotalPriceOfItemOrderedByUnits).sum();
    }

    //TODO
    public double calcTotalPriceOfCertainItem(int serialIdOfItem)
    {
        return getOrderedItems().get(serialIdOfItem).getTotalPriceOfItemOrderedByTypeOfMeasure();
    }    public ClosedOrder(double deliveryPrice, double totalPriceOfOrder, int totalAmountOfItems, int totalPriceOfItems, Store storeUsed)
    {
        super(storeUsed);
        this.deliveryPrice = deliveryPrice;
        this.totalPriceOfOrder = totalPriceOfOrder;
        this.totalAmountOfItems = totalAmountOfItems;
        this.totalPriceOfItems = totalPriceOfItems;
    }
    public double getDeliveryPriceAfterOrder() {
        return deliveryPrice;
    }

    public double getTotalPriceOfOrder()
    {
        return totalPriceOfOrder;
    }

    public int getTotalAmountOfItems() {
        return totalAmountOfItems;
    }

    public double getTotalPriceOfItems()
    {
        return totalPriceOfItems;
    }
}
