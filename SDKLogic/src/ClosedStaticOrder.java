import java.util.Date;
import java.util.Map;

public class ClosedStaticOrder extends StaticOrder {
    private Integer serialNumber;
    private double deliveryPrice;
    private double totalPriceOfOrder;
    private int totalAmountOfItemsByUnit;
    private int totalAmountOfItemTypes;
    private double totalPriceOfItems;
   public ClosedStaticOrder(double deliveryPrice, double totalPriceOfOrder, int totalAmountOfItemsByUnit, int totalAmountOfItemTypes, double totalPriceOfItems, Store storeUsed, Map<Integer, OrderedItem> orderedItems, Date date )
    {
        super(storeUsed, orderedItems, date);
        this.serialNumber = Logic.getCurrentOrderSerialIDInSDK();
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

    public double getTotalAmountOfSoledItem(int itemSerialID)
    {
        return getItemInOrder(itemSerialID).getTotalAmountOfItemOrderedByTypeOfMeasure();
    }
}
