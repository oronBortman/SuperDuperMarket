import java.util.Date;
import java.util.Map;

public abstract class ClosedOrder extends Order{

    private Integer serialNumber;
    private double deliveryPrice;
    private double totalPriceOfOrder;
    private int totalAmountOfItemsByUnit;
    private int totalAmountOfItemTypes;
    private double totalPriceOfItems;

    public ClosedOrder(double deliveryPrice, double totalPriceOfOrder, int totalAmountOfItemsByUnit, int totalAmountOfItemTypes, double totalPriceOfItems, Map<Integer, OrderedItem> orderedItems, Date date )
    {
        super(orderedItems,date);
        this.serialNumber = Base.getCurrentOrderSerialIDInSDK();
        this.deliveryPrice = deliveryPrice;
        this.totalPriceOfOrder = totalPriceOfOrder;
        this.totalAmountOfItemsByUnit = totalAmountOfItemsByUnit;
        this.totalAmountOfItemTypes = totalAmountOfItemTypes;
        this.totalPriceOfItems = totalPriceOfItems;
    }

    public double getDeliveryPriceAfterOrder() {
        return deliveryPrice;
    }

    public double getTotalPriceOfOrder()
    {
        return totalPriceOfOrder;
    }

    public int getTotalAmountOfItemsByUnit() {
        return totalAmountOfItemsByUnit;
    }

    public int getTotalAmountOfItemTypes() {
        return totalAmountOfItemTypes;
    }

    public double getTotalPriceOfItems()
    {
        return totalPriceOfItems;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

   /* public double getTotalAmountOfAnItemByTypeOfMeasure(int itemSerialID)
    {
        return getItemInOrder(itemSerialID).getTotalAmountOfItemOrderedByTypeOfMeasure();
    }*/

}

