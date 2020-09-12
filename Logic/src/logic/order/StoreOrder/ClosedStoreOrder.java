package logic.order.StoreOrder;

import logic.BusinessLogic;
import logic.order.ClosedOrder;
import logic.order.itemInOrder.OrderedItemFromStore;
import logic.Store;

import java.util.Date;
import java.util.Map;

public class ClosedStoreOrder extends StoreOrder implements ClosedOrder {

    private Integer serialNumber;
    private double deliveryPrice;
    private double totalPriceOfOrder;
    private int totalAmountOfItemsByUnit;
    private int totalAmountOfItemTypes;
    private double totalPriceOfItems;


    public ClosedStoreOrder(double deliveryPrice, double totalPriceOfOrder, int totalAmountOfItemsByUnit, int totalAmountOfItemTypes, double totalPriceOfItems, Store storeUsed, Map<Integer, OrderedItemFromStore> orderedItems, Date date, boolean isOrderStatic) {
        super(date, isOrderStatic);
        this.serialNumber = BusinessLogic.getCurrentOrderSerialIDInSDK();
        this.deliveryPrice = deliveryPrice;
        this.totalPriceOfOrder = totalPriceOfOrder;
        this.totalAmountOfItemsByUnit = totalAmountOfItemsByUnit;
        this.totalAmountOfItemTypes = totalAmountOfItemTypes;
        this.totalPriceOfItems = totalPriceOfItems;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Store getStoreUsed() {
        return storeUsed;
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

}


